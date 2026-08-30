package com.example.TurboUserManagament.service;

import com.example.TurboUserManagament.appenum.DriverStatus;
import com.example.TurboUserManagament.appenum.VehicleStatus;
import com.example.TurboUserManagament.appenum.VehicleVerificationStatus;
import com.example.TurboUserManagament.entity.Driver;
import com.example.TurboUserManagament.entity.Vehicle;
import com.example.TurboUserManagament.entity.VehicleVerification;
import com.example.TurboUserManagament.repository.DriverRepository;
import com.example.TurboUserManagament.repository.VehicleRepository;
import com.example.TurboUserManagament.repository.VehicleVerificationRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class VehicleVerificationService {

    private final VehicleVerificationRepository vehicleVerificationRepository;
    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;

    public VehicleVerificationService(VehicleVerificationRepository vehicleVerificationRepository,
                                      DriverRepository driverRepository,
                                      VehicleRepository vehicleRepository){
        this.vehicleVerificationRepository=vehicleVerificationRepository;
        this.driverRepository=driverRepository;
        this.vehicleRepository=vehicleRepository;
    }

    public VehicleVerification getVehicleVerification(Long verificationId){
        Optional<VehicleVerification> vehicleVerification= vehicleVerificationRepository.findById(verificationId);
        if (vehicleVerification.isEmpty()) {
            throw new IllegalArgumentException(
                    "Vehicle verification not found"
            );
        }
        return vehicleVerification.get();
    }

    public VehicleVerification submitVerification(Vehicle vehicle,
                                                  String fileURL){
        VehicleVerification vehicleVerification= VehicleVerification.builder()
                .fileURL(fileURL)
                .uploadedAt(LocalDateTime.now())
                .vehicleVerificationStatus(VehicleVerificationStatus.PENDING)
                .vehicle(vehicle)
                .build();
        if(vehicle.getVerifications()==null){
            vehicle.setVerifications(new ArrayList<>());
        }
        vehicle.getVerifications().add(vehicleVerification);

        return vehicleVerificationRepository.save(vehicleVerification);
    }

    @Transactional
    public VehicleVerification approveVerification(Long verificationId){
        VehicleVerification vehicleVerification= getVehicleVerification(verificationId);

        if(vehicleVerification.getVehicleVerificationStatus()!=VehicleVerificationStatus.PENDING){
            throw new IllegalArgumentException("only pending vehicle verification can be verified");
        }

        vehicleVerification.setVehicleVerificationStatus(VehicleVerificationStatus.APPROVED);

        Vehicle vehicle= vehicleVerification
                .getVehicle();
        vehicle.setStatus(VehicleStatus.ACTIVE);
        vehicleRepository.save(vehicle);

        Driver driver = vehicle
                .getDriver();
        driver.setDriverStatus(DriverStatus.APPROVED);
        driverRepository.save(driver);

        return vehicleVerificationRepository.save(vehicleVerification);
    }

    public VehicleVerification rejectVerification(Long verificationId, String rejectionReason){
        VehicleVerification vehicleVerification= getVehicleVerification(verificationId);

        if(vehicleVerification.getVehicleVerificationStatus()!=VehicleVerificationStatus.PENDING){
            throw new IllegalArgumentException("only pending vehicle verification can be verified");
        }

        vehicleVerification.setVehicleVerificationStatus(VehicleVerificationStatus.REJECTED);
        vehicleVerification.setRejectionReason(rejectionReason);

        return vehicleVerificationRepository.save(vehicleVerification);
    }
}
