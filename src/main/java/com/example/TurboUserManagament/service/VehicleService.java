package com.example.TurboUserManagament.service;

import com.example.TurboUserManagament.appenum.VehicleStatus;
import com.example.TurboUserManagament.appenum.VehicleVerificationStatus;
import com.example.TurboUserManagament.entity.Driver;
import com.example.TurboUserManagament.entity.Vehicle;
import com.example.TurboUserManagament.entity.VehicleVerification;
import com.example.TurboUserManagament.record.VehicleRegistration;
import com.example.TurboUserManagament.repository.VehicleRepository;

import java.util.ArrayList;
import java.util.List;

public class VehicleService {

    private VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository){
        this.vehicleRepository= vehicleRepository;
    }

    public Vehicle getVehicle(Long vehicleId){
        Vehicle vehicle= vehicleRepository.findById(vehicleId);
        if (vehicle == null) {
            throw new IllegalArgumentException(
                    "Vehicle is not found"
            );
        }
        return vehicle;
    }

    public Vehicle addVehicle(Driver driver,
                              VehicleRegistration vehicleRegistration){
        Vehicle vehicle=Vehicle.builder()
                .driver(driver)
                .model(vehicleRegistration.vehicleModel())
                .type(vehicleRegistration.vehicleType())
                .color(vehicleRegistration.vehicleColor())
                .plateNumber(vehicleRegistration.vehiclePlateNumber())
                .verifications(new ArrayList<>())
                .build();
        driver.setVehicles(new ArrayList<>(List.of(vehicle)));

        return vehicleRepository.save(vehicle);
    }

    public Vehicle updateVehicle(Long vehicleId, Vehicle updatedVehicle) {
        Vehicle vehicle = getVehicle(vehicleId);

        vehicle.setModel(updatedVehicle.getModel());
        vehicle.setType(updatedVehicle.getType());
        vehicle.setColor(updatedVehicle.getColor());
        vehicle.setPlateNumber(updatedVehicle.getPlateNumber());

        return vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(Long vehcileId){
        Vehicle vehicle=getVehicle(vehcileId);
        vehicle.setStatus(VehicleStatus.DELETED);
        vehicleRepository.save(vehicle);
    }

    public boolean isVehicleActive(Long vehicleId){
        //if the vehicle latest verification is approved then the vehicle is active
        Vehicle vehicle= getVehicle(vehicleId);
        if(vehicle==null || vehicle.getVerifications()==null){
            return false;
        }

        VehicleVerification vehicleVerification= vehicle.getVerifications()
                .stream()
                .max((v1,v2)->
                        v1.getUploadedAt().compareTo(v2.getUploadedAt()))
                .get();

        return vehicleVerification.getVehicleVerificationStatus()== VehicleVerificationStatus.APPROVED;
    }

    public List<Vehicle> getDriverVehicles(Long driverId){
        List<Vehicle> vehicles= vehicleRepository.findByDriverId(driverId);
        if(vehicles==null|| vehicles.isEmpty()){
            return List.of();
        }
        return vehicles;
    }
}
