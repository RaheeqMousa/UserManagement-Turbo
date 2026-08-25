package com.example.TurboUserManagament.service;

import com.example.TurboUserManagament.appenum.UserRole;
import com.example.TurboUserManagament.appenum.VehicleVerificationStatus;
import com.example.TurboUserManagament.entity.*;
import com.example.TurboUserManagament.exception.DriverAlreadyExistException;
import com.example.TurboUserManagament.record.DriverRegistration;
import com.example.TurboUserManagament.record.VehicleRegistration;
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
    private VehicleService vehicleService;
    private VehicleVerificationService vehicleVerificationService;

    public DriverService(AuthenticationService  authService,
                         DriverRepository driverRepository,
                         UserRepository userRepository,
                         VehicleService vehicleService,
                         VehicleVerificationService vehicleVerificationService){
        this.authenticationService= authService;
        this.driverRepository= driverRepository;
        this.userRepository=userRepository;
        this.vehicleService=vehicleService;
        this.vehicleVerificationService=vehicleVerificationService;
    }

    public Driver getDriver(Long driverId){
        Driver driver= driverRepository.findByID(driverId);
        if (driver == null) {
            throw new IllegalArgumentException("Vehicle verification not found");
        }
        return driver;
    }

    public Driver registerDriver(DriverRegistration driverRegistration){
        User user= userRepository.findByPhoneNumber(driverRegistration.phoneNumber());

        if(user==null) {
            //create the user
            user = User.builder()
                    .firstName(driverRegistration.firstName())
                    .lastName(driverRegistration.lastName())
                    .phoneNumber(driverRegistration.phoneNumber())
                    .role(UserRole.DRIVER)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            //create the authentication accoutn
            AuthenticationAccount authenticationAccount= authenticationService.register(user, driverRegistration.password());
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
                .identityNumber(driverRegistration.identityNumber())
                .licenseNumber(driverRegistration.licenseNumber())
                .licenseExpiryDate(driverRegistration.licenseExpiryDate())
                .build();

        //create vehicle
        VehicleRegistration vehicleRegistration=new VehicleRegistration(
                driverRegistration.vehicleModel(),
                driverRegistration.vehicleType(),
                driverRegistration.vehicleColor(),
                driverRegistration.vehiclePlateNumber()
        );
        Vehicle vehicle= vehicleService.addVehicle(driver, vehicleRegistration);

        //enter verification for the vehicle
        vehicleVerificationService.submitVerification(vehicle, driverRegistration.verificationFileURL());

        return driverRepository.save(driver);
    }

    public boolean isDriverApproved(Long driverId){
        Driver driver=getDriver(driverId);
        List<Vehicle> driverVehicles=driver.getVehicles();

        if(driverVehicles==null || driverVehicles.isEmpty()){
            return false;
        }

        for(short i=0;i<driverVehicles.size();i++){
            Vehicle vehicle=driverVehicles.get(i);
            if(vehicleService.isVehicleActive(vehicle.getId())){
                return true;
            }
        }

        return false;
    }

}
