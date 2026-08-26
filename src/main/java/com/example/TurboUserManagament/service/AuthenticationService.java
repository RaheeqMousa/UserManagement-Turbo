package com.example.TurboUserManagament.service;

import com.example.TurboUserManagament.appenum.AccountStatus;
import com.example.TurboUserManagament.appenum.PhoneVerificationStatus;
import com.example.TurboUserManagament.entity.AuthenticationAccount;
import com.example.TurboUserManagament.entity.User;
import com.example.TurboUserManagament.record.Password;
import com.example.TurboUserManagament.record.PhoneNumber;
import com.example.TurboUserManagament.repository.AuthenticationRepository;
import com.example.TurboUserManagament.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

public class AuthenticationService {

    private OTPService otpService;
    private AuthenticationRepository authenticationRepository;
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(OTPService otpService,
                                 AuthenticationRepository authenticationRepository,
                                 PasswordEncoder passwordEncoder,
                                 UserRepository userRepository){
        this.otpService=otpService;
        this.authenticationRepository=authenticationRepository;
        this.passwordEncoder=passwordEncoder;
        this.userRepository=userRepository;
    }

    public AuthenticationAccount register(User user, String rawPassword){
        Password.validate(rawPassword);

        Password hashedPassword = hashPassword(rawPassword);
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

    public User login(PhoneNumber phoneNumber, String rawPassword){
        //find account with that phoneNumber
        //check whether the phone number is verified or not
        //verify password
        //check account status
        User user= userRepository.findByPhoneNumber(phoneNumber);
        if(user==null){
            throw new IllegalArgumentException("Invalid phone number or password");
        }
        AuthenticationAccount authenticationAccount= user.getAuthenticationAccount();
        if(authenticationAccount==null){
            throw new IllegalArgumentException("Invalid phone numebr or password");
        }
        boolean isPhoneVerified= user.getPhoneVerifications()
                .stream()
                .anyMatch(phoneVerification ->
                        phoneVerification.getStatus()==PhoneVerificationStatus.VERIFIED);
        if(!isPhoneVerified){
            throw new IllegalArgumentException("User with this phone number is not verified");
        }
        if (!passwordEncoder.matches(
                rawPassword,
                authenticationAccount.getPassword().value()
        )) {
            throw new IllegalArgumentException(
                    "Invalid phone number or password"
            );
        }

        if(user.getAuthenticationAccount().getStatus()!= AccountStatus.ACTIVE){
            throw new IllegalArgumentException("User account is not active");
        }

        return user;
    }

    public void changePassword(User user, String oldPassword, String newPassword){
        //verify if the old password is correct
        //checks the new password against constraints
        //update password
        AuthenticationAccount authenticationAccount=user.getAuthenticationAccount();
        if(authenticationAccount==null){
            throw new IllegalArgumentException("Authentication account is not found");
        }

        if(!passwordEncoder.matches(oldPassword,authenticationAccount.getPassword().value())){
            throw new IllegalArgumentException("Old Password is wrong");
        }

        Password.validate(newPassword);

        Password encodedNewPassword= hashPassword(newPassword);

        authenticationAccount.setPassword(encodedNewPassword);
        authenticationAccount.setUpdatedAt(LocalDateTime.now());

        authenticationRepository.save(authenticationAccount);
    }

    public void deactivateAccount(User user){
        //If someone wants to delete their account, we will deactviate it (soft delete)
        AuthenticationAccount authenticationAccount= user
                .getAuthenticationAccount();
        if(authenticationAccount==null){
            throw new IllegalArgumentException("Authentication Account is not found");
        }
        authenticationAccount.setStatus(AccountStatus.DEACTIVATE);
        authenticationAccount.setUpdatedAt(LocalDateTime.now());
        authenticationRepository.save(authenticationAccount);
    }

    public void deleteAccount(User user){
        AuthenticationAccount authenticationAccount= user
                .getAuthenticationAccount();
        if(authenticationAccount==null){
            throw new IllegalArgumentException("Authentication Account is not found");
        }
        authenticationAccount.setStatus(AccountStatus.DELETED);
        authenticationAccount.setUpdatedAt(LocalDateTime.now());
        authenticationRepository.save((authenticationAccount));
    }

    private Password hashPassword(String password) {
        String hashedPassword= passwordEncoder.encode(password);
        return new Password(hashedPassword);
    }

}
