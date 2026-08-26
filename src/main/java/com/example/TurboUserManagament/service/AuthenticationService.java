package com.example.TurboUserManagament.service;

import com.example.TurboUserManagament.appenum.AccountStatus;
import com.example.TurboUserManagament.appenum.PhoneVerificationStatus;
import com.example.TurboUserManagament.entity.AuthenticationAccount;
import com.example.TurboUserManagament.entity.User;
import com.example.TurboUserManagament.record.Password;
import com.example.TurboUserManagament.record.PhoneNumber;
import com.example.TurboUserManagament.repository.AuthenticationRepository;
import com.example.TurboUserManagament.repository.UserRepository;

import java.time.LocalDateTime;

public class AuthenticationService {

    private OTPService otpService;
    private AuthenticationRepository authenticationRepository;
    private UserRepository userRepository;

    public AuthenticationService(OTPService otpService,
                                 AuthenticationRepository authenticationRepository){
        this.otpService=otpService;
        this.authenticationRepository=authenticationRepository;
    }

    public AuthenticationAccount register(User user, Password password){
        Password hashedPassword= hashPassword(password);

        AuthenticationAccount account = AuthenticationAccount.builder()
                .user(user)
                .password(hashedPassword)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(AccountStatus.ACTIVE)
                .build();
        otpService.sendOTP(user);
        return authenticationRepository.save(account);
    }

    public User login(PhoneNumber phoneNumber, String password){
        //find account with that phoneNumber
        //check whether the phone number is verified or not
        //verify password
        //check account status
        return null;
    }

    public void changePassword(User user, String oldPassword, String newPassword){
        //verify if the old password is correct
        //checks the new password against constraints
        //update password

    }

    public void deactivateAccount(User user){
        //If someone wants to delete their account, we will deactviate it (soft delete)
    }

    private Password hashPassword(Password password) {
        return password;
    }

}
