package com.example.TurboUserManagament.service;

import com.example.TurboUserManagament.appenum.AccountStatus;
import com.example.TurboUserManagament.appenum.AddressType;
import com.example.TurboUserManagament.appenum.UserRole;
import com.example.TurboUserManagament.entity.Address;
import com.example.TurboUserManagament.entity.AuthenticationAccount;
import com.example.TurboUserManagament.entity.User;
import com.example.TurboUserManagament.entity.Vendor;
import com.example.TurboUserManagament.exception.UserAlreadyExistException;
import com.example.TurboUserManagament.exception.UserNotFoundException;
import com.example.TurboUserManagament.exception.VendorAlreadyExistException;
import com.example.TurboUserManagament.record.VendorRegistration;
import com.example.TurboUserManagament.repository.VendorRepository;
import java.time.LocalDateTime;
import java.util.Optional;

public class VendorService {
    private final VendorRepository vendorRepository;
    private final UserService userService;
    private final AuthenticationService authenticationService;

    public VendorService(VendorRepository vendorRepository,
                         UserService userService,
                         AuthenticationService authenticationService){
        this.vendorRepository=vendorRepository;
        this.userService= userService;
        this.authenticationService=authenticationService;
    }

    public Vendor getVendor(Long id){
        Optional<Vendor> vendor= vendorRepository.findById(id);
        if(vendor.isEmpty()){
            throw new IllegalArgumentException("Vendor is not found");
        }
        return vendor.get();
    }

    public Vendor registerVendor(VendorRegistration vendorRegistration){
        User user= userService.getUserByPhoneNumber(vendorRegistration.phoneNumber());
        if(user==null){
            user= User.builder()
                    .firstName(vendorRegistration.firstName())
                    .lastName(vendorRegistration.lastName())
                    .phoneNumber(vendorRegistration.phoneNumber())
                    .role(UserRole.VENDOR)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            userService.createUser(user);
            AuthenticationAccount authenticationAccount=
                    authenticationService.register(user,vendorRegistration.password());
            user.setAuthenticationAccount(authenticationAccount);
        }
        if(vendorRepository.findById(user.getId()).isEmpty()){
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

    public Vendor updateVendor(Long vendorId, Vendor updatedVendor){
        Vendor existingVendor= getVendor(vendorId);
        User existingUser = existingVendor.getUser();
        User updatedUser = updatedVendor.getUser();
        User userWithPhone= userService.getUserByPhoneNumber(updatedUser.getPhoneNumber());

        if(userWithPhone.getAuthenticationAccount().getStatus()== AccountStatus.DELETED ||
                userWithPhone.getAuthenticationAccount().getStatus()== AccountStatus.DEACTIVATE){
            throw new UserNotFoundException("User with this ID is not avaialable");
        }
        if(userWithPhone.getId().equals(existingUser.getId())){
            throw new UserAlreadyExistException(
                    "Phone number already exists"
            );
        }

        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());

        existingVendor.setBusinessAddress(updatedVendor.getBusinessAddress());
        existingVendor.setAverageReview(updatedVendor.getAverageReview());
        existingVendor.setVendorType(updatedVendor.getVendorType());

        userService.createUser(existingUser);
        return vendorRepository.save(existingVendor);
    }

}