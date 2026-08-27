package com.example.TurboUserManagament.service;

import com.example.TurboUserManagament.appenum.*;
import com.example.TurboUserManagament.entity.*;
import com.example.TurboUserManagament.exception.DriverAlreadyExistException;
import com.example.TurboUserManagament.exception.UserAlreadyExistException;
import com.example.TurboUserManagament.exception.UserNotFoundException;
import com.example.TurboUserManagament.record.DriverRegistration;
import com.example.TurboUserManagament.record.VehicleRegistration;
import com.example.TurboUserManagament.repository.DriverRepository;
import com.example.TurboUserManagament.repository.UserRepository;
import org.springframework.boot.availability.AvailabilityState;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DriverService {

    private final DriverRepository driverRepository;
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final VehicleService vehicleService;
    private final VehicleVerificationService vehicleVerificationService;

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
            throw new UserNotFoundException("Driver is not found");
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
        return driver.getDriverStatus()== DriverStatus.APPROVED;
    }

    public void setAvailability(Long id, DriverAvailability status){
        Driver driver= getDriver(id);
        driver.setDriverAvailability(status);
    }

    public Driver updateDriver(Long id, Driver updatedDriver){
        Driver existingDriver = getDriver(id);

        User existingUser = existingDriver.getUser();
        User updatedUser= updatedDriver.getUser();

        User userWithPhoneNumebr = userRepository.findByPhoneNumber(existingUser.getPhoneNumber());

        if(userWithPhoneNumebr.getAuthenticationAccount().getStatus()== AccountStatus.DELETED){
            throw new UserNotFoundException("This driver has been deleted");
        }
        if (userWithPhoneNumebr!=null && !userWithPhoneNumebr.getId().equals(id)) {
            throw new UserAlreadyExistException(
                    "Update Driver - driver with this phone number already exists"
            );
        }

        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());

        existingDriver.setIdentityNumber(updatedDriver.getIdentityNumber());
        existingDriver.setLicenseExpiryDate(updatedDriver.getLicenseExpiryDate());
        existingDriver.setLicenseNumber(updatedDriver.getLicenseNumber());

        userRepository.save(existingUser);

        return driverRepository.save(existingDriver);
    }

}
