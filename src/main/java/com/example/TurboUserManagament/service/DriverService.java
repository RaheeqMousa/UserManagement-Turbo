package com.example.TurboUserManagament.service;

import com.example.TurboUserManagament.appenum.UserRole;
import com.example.TurboUserManagament.appenum.VehicleVerificationStatus;
import com.example.TurboUserManagament.entity.*;
import com.example.TurboUserManagament.exception.DriverAlreadyExistException;
import com.example.TurboUserManagament.record.DriverRegistration;
import com.example.TurboUserManagament.repository.DriverRepository;
import com.example.TurboUserManagament.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DriverService {

    private DriverRepository driverRepository;
    private AuthenticationService authenticationService;
    private UserRepository userRepository;

    public DriverService(AuthenticationService  authService,
                         DriverRepository driverRepository,
                         UserRepository userRepository){
        this.authenticationService= authService;
        this.driverRepository= driverRepository;
        this.userRepository=userRepository;
    }

    public Driver registerDriver(DriverRegistration registration){
        User user= userRepository.findByPhoneNumber(registration.phoneNumber());

        if(user==null) {
            //create the user
            user = User.builder()
                    .firstName(registration.firstName())
                    .lastName(registration.lastName())
                    .phoneNumber(registration.phoneNumber())
                    .role(UserRole.DRIVER)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            //create the authentication accoutn
            AuthenticationAccount authenticationAccount= authenticationService.register(user, registration.password());
            user.setAuthenticationAccount(authenticationAccount);
        }

        if (driverRepository.findByID(user.getId())!=null) {
            throw new DriverAlreadyExistException(
                    "A driver profile already exists for this phone number"
            );
        }

        //create the driver
        Driver driver = Driver.builder()
                .user(user)
                .identityNumber(registration.identityNumber())
                .licenseNumber(registration.licenseNumber())
                .licenseExpiryDate(registration.licenseExpiryDate())
                .build();

        //create vehicle
        Vehicle vehicle=Vehicle.builder()
                .driver(driver)
                .model(registration.vehicleModel())
                .type(registration.vehicleType())
                .color(registration.vehicleColor())
                .plateNumber(registration.vehiclePlateNumber())
                .build();
        driver.setVehicles(new ArrayList<>(List.of(vehicle)));

        //enter verification for the vehicle
        VehicleVerification verification=new VehicleVerification();
        verification.setVehicle(vehicle);
        verification.setFileURL(
                registration.verificationFileURL()
        );
        verification.setUploadedAt(LocalDate.now());
        verification.setVehicleVerificationStatus(
                VehicleVerificationStatus.PENDING
        );
        return driverRepository.save(driver);
    }

}
