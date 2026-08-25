package com.example.TurboUserManagament.service;

import com.example.TurboUserManagament.appenum.DriverStatus;
import com.example.TurboUserManagament.appenum.VehicleVerificationStatus;
import com.example.TurboUserManagament.entity.Driver;
import com.example.TurboUserManagament.entity.Vehicle;
import com.example.TurboUserManagament.entity.VehicleVerification;
import com.example.TurboUserManagament.repository.DriverRepository;
import com.example.TurboUserManagament.repository.VehicleRepository;
import com.example.TurboUserManagament.repository.VehicleVerificationRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VehicleVerificationService {

    private VehicleVerificationRepository vehicleVerificationRepository;
    private DriverRepository driverRepository;

    public VehicleVerificationService(VehicleVerificationRepository vehicleVerificationRepository,
                                      DriverRepository driverRepository){
        this.vehicleVerificationRepository=vehicleVerificationRepository;
        this.driverRepository=driverRepository;
    }

    public VehicleVerification getVehicleVerification(Long verificationId){
        VehicleVerification vehicleVerification= vehicleVerificationRepository.findById(verificationId);
        if (vehicleVerification == null) {
            throw new IllegalArgumentException(
                    "Vehicle verification not found"
            );
        }
        return vehicleVerification;
    }

    public void submitVerification(Vehicle vehicle,
                                                  String fileURL){
        VehicleVerification vehicleVerification= VehicleVerification.builder()
                .fileURL(fileURL)
                .uploadedAt(LocalDate.now())
                .vehicleVerificationStatus(VehicleVerificationStatus.PENDING)
                .vehicle(vehicle)
                .build();
        if(vehicle.getVerifications()==null){
            vehicle.setVerifications(new ArrayList<>());
        }
        vehicle.getVerifications().add(vehicleVerification);

        vehicleVerificationRepository.save(vehicleVerification);
    }

    public VehicleVerification approveVehicle(Long verificationId){
        VehicleVerification vehicleVerification= getVehicleVerification(verificationId);

        if(vehicleVerification.getVehicleVerificationStatus()!=VehicleVerificationStatus.PENDING){
            throw new IllegalArgumentException("only pending vehicle verification can be verified");
        }

        vehicleVerification.setVehicleVerificationStatus(VehicleVerificationStatus.APPROVED);

        Driver driver = vehicleVerification
                .getVehicle()
                .getDriver();

        driver.setDriverStatus(DriverStatus.APPROVED);

        driverRepository.save(driver);

        return vehicleVerificationRepository.save(vehicleVerification);
    }

    public VehicleVerification rejectVehicle(Long verificationId, String rejectionReason){
        VehicleVerification vehicleVerification= getVehicleVerification(verificationId);

        if(vehicleVerification.getVehicleVerificationStatus()!=VehicleVerificationStatus.PENDING){
            throw new IllegalArgumentException("only pending vehicle verification can be verified");
        }

        vehicleVerification.setVehicleVerificationStatus(VehicleVerificationStatus.REJECTED);
        vehicleVerification.setRejectionReason(rejectionReason);

        return vehicleVerificationRepository.save(vehicleVerification);
    }


}
