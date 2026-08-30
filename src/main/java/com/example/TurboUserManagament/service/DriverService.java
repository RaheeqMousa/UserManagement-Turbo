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
    private final UserService userService;
    private final VehicleService vehicleService;
    private final VehicleVerificationService vehicleVerificationService;

    public DriverService(AuthenticationService  authService,
                         DriverRepository driverRepository,
                         UserService userService,
                         VehicleService vehicleService,
                         VehicleVerificationService vehicleVerificationService){
        this.authenticationService= authService;
        this.driverRepository= driverRepository;
        this.userService=userService;
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

    public Driver registerDriver(User user,
                                 Driver driver,
                                 Vehicle vehicle,
                                 String rawPassword,
                                 String verificationURL){
        // create user
        user.setRole(UserRole.DRIVER);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        User savedUser = userService.createUser(user);

        // create authentication account
        AuthenticationAccount account = authenticationService.register(savedUser, rawPassword);
        savedUser.setAuthenticationAccount(account);

        // create driver
        driver.setUser(savedUser);
        Driver savedDriver = driverRepository.save(driver);

        // create vehicle
        Vehicle savedVehicle = vehicleService.addVehicle(savedDriver, vehicle);

        // submit vehicle verification
        vehicleVerificationService.submitVerification(
                savedVehicle,
                verificationURL
        );

        return savedDriver;
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

        User userWithPhoneNumebr = userService.getUserByPhoneNumber(existingUser.getPhoneNumber());

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

        userService.createUser(existingUser);

        return driverRepository.save(existingDriver);
    }

}
