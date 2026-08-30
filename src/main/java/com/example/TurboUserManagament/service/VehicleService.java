package com.example.TurboUserManagament.service;

import com.example.TurboUserManagament.appenum.VehicleStatus;
import com.example.TurboUserManagament.appenum.VehicleVerificationStatus;
import com.example.TurboUserManagament.entity.Driver;
import com.example.TurboUserManagament.entity.Vehicle;
import com.example.TurboUserManagament.entity.VehicleVerification;
import com.example.TurboUserManagament.repository.VehicleRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository){
        this.vehicleRepository= vehicleRepository;
    }

    public Vehicle getVehicleById(Long vehicleId){
        Optional<Vehicle> vehicle= vehicleRepository.findById(vehicleId);
        if (vehicle.isEmpty()) {
            throw new IllegalArgumentException(
                    "Vehicle is not found"
            );
        }
        return vehicle.get();
    }

    public Vehicle addVehicle(Driver driver,
                              Vehicle newVehicle){
        Vehicle vehicle=Vehicle.builder()
                .driver(driver)
                .model(newVehicle.getModel())
                .type(newVehicle.getType())
                .color(newVehicle.getColor())
                .plateNumber(newVehicle.getPlateNumber())
                .verifications(new ArrayList<>())
                .build();
        driver.getVehicles().add(vehicle);

        return vehicleRepository.save(vehicle);
    }

    public Vehicle updateVehicle(Long vehicleId, Vehicle updatedVehicle) {
        Vehicle vehicle = getVehicleById(vehicleId);

        vehicle.setModel(updatedVehicle.getModel());
        vehicle.setType(updatedVehicle.getType());
        vehicle.setColor(updatedVehicle.getColor());
        vehicle.setPlateNumber(updatedVehicle.getPlateNumber());

        return vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(Long vehcileId){
        Vehicle vehicle=getVehicleById(vehcileId);
        vehicle.setStatus(VehicleStatus.DELETED);
        vehicleRepository.save(vehicle);
    }

    public boolean isVehicleActive(Long vehicleId){
        //if the vehicle latest verification is approved then the vehicle is active
        Vehicle vehicle= getVehicleById(vehicleId);

        if (vehicle.getStatus() == VehicleStatus.DELETED) {
            return false;
        }

        if(vehicle.getVerifications()==null){
            return false;
        }

        VehicleVerification vehicleVerification=
                vehicle.getVerifications()
                .stream()
                .max((v1,v2)->
                        v1.getUploadedAt()
                                .compareTo(v2.getUploadedAt()))
                .orElseThrow();

        return vehicleVerification.getVehicleVerificationStatus()
                == VehicleVerificationStatus.APPROVED;
    }

    public List<Vehicle> getDriverVehicles(Long driverId){
        return vehicleRepository.findByDriverId(driverId);
    }
}
