package com.example.TurboUserManagament.service;

import com.example.TurboUserManagament.appenum.AddressType;
import com.example.TurboUserManagament.entity.Address;
import com.example.TurboUserManagament.entity.AuthenticationAccount;
import com.example.TurboUserManagament.entity.User;
import com.example.TurboUserManagament.entity.Vendor;
import com.example.TurboUserManagament.exception.VendorAlreadyExistException;
import com.example.TurboUserManagament.record.VendorRegistration;
import com.example.TurboUserManagament.repository.UserRepository;
import com.example.TurboUserManagament.repository.VendorRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class VendorService {
    private VendorRepository vendorRepository;
    private UserRepository userRepository;
    private AuthenticationService authenticationService;


    public VendorService(VendorRepository vendorRepository,
                         UserRepository userRepository,
                         AuthenticationService authenticationService){
        this.vendorRepository=vendorRepository;
        this.userRepository=userRepository;
        this.authenticationService=authenticationService;
    }

    public Vendor registerVendor(VendorRegistration vendorRegistration){
        User user= userRepository.findByPhoneNumber(vendorRegistration.phoneNumber());
        if(user==null){
            user= User.builder()
                    .firstName(vendorRegistration.firstName())
                    .lastName(vendorRegistration.lastName())
                    .phoneNumber(vendorRegistration.phoneNumber())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            AuthenticationAccount authenticationAccount=
                    authenticationService.register(user,vendorRegistration.password());
            user.setAuthenticationAccount(authenticationAccount);
        }
        if(vendorRepository.findById(user.getId())!=null){
            throw new VendorAlreadyExistException("A Vendor profile with this phone number already exist");
        }

        Address address= Address.builder()
                .city(vendorRegistration.city())
                .street(vendorRegistration.street())
                .addressType(AddressType.WORK)
                .latitude(vendorRegistration.latitude())
                .longitude(vendorRegistration.longitude())
                .build();
        Vendor vendor= Vendor.builder()
                .user(user)
                .businessAddress(address)
                .businessName(vendorRegistration.placeName())
                .build();
        return vendorRepository.save(vendor);
    }

}
