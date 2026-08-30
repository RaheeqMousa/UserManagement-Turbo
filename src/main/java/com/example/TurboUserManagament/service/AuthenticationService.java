package com.example.TurboUserManagament.service;

import com.example.TurboUserManagament.appenum.AccountStatus;
import com.example.TurboUserManagament.appenum.PhoneVerificationStatus;
import com.example.TurboUserManagament.converter.PhoneNumberConverter;
import com.example.TurboUserManagament.entity.AuthenticationAccount;
import com.example.TurboUserManagament.entity.User;
import com.example.TurboUserManagament.record.Password;
import com.example.TurboUserManagament.record.PhoneNumber;
import com.example.TurboUserManagament.repository.AuthenticationRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;

public class AuthenticationService {

    private final OTPService otpService;
    private final AuthenticationRepository authenticationRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(OTPService otpService,
                                 AuthenticationRepository authenticationRepository,
                                 PasswordEncoder passwordEncoder,
                                 UserService userService){
        this.otpService=otpService;
        this.authenticationRepository=authenticationRepository;
        this.passwordEncoder=passwordEncoder;
        this.userService=userService;
    }

    public AuthenticationAccount register(User user, String rawPassword){
        Password.validate(rawPassword);

        Password hashedPassword = hashPassword(rawPassword);
        AuthenticationAccount account = AuthenticationAccount.builder()
                .user(user)
                .password(hashedPassword)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(AccountStatus.PENDING)
                .build();

        AuthenticationAccount savedAccount =
                authenticationRepository.save(account);
        otpService.sendOTP(user);
        //after otp verification activate account
        return savedAccount;
    }

    public User login(PhoneNumber phoneNumber, String rawPassword){
        //find account with that phoneNumber
        //check whether the phone number is verified or not
        //verify password
        //check account status
        User user= userService.getUserByPhoneNumber(phoneNumber);

        AuthenticationAccount authenticationAccount= user.getAuthenticationAccount();
        if(authenticationAccount==null){
            throw new IllegalArgumentException("Invalid phone number or password");
        }
        if(authenticationAccount.getStatus()!=AccountStatus.ACTIVE){
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

    public boolean verifyOTP(User user, String otp){
        boolean verified= otpService.verifyOTP(otp,user);

        if(!verified){
            return false;
        }
        activateAccount(user.getAuthenticationAccount());

        return true;
    }

    public void activateAccount(AuthenticationAccount authenticationAccount){
        if(authenticationAccount==null){
            throw new IllegalArgumentException("Authentication Account is not found");
        }
        authenticationAccount.setStatus(AccountStatus.ACTIVE);
        authenticationAccount.setUpdatedAt(LocalDateTime.now());
        authenticationRepository.save(authenticationAccount);
    }

    public void deactivateAccount(AuthenticationAccount authenticationAccount){
        if(authenticationAccount==null){
            throw new IllegalArgumentException("Authentication Account is not found");
        }
        authenticationAccount.setStatus(AccountStatus.DEACTIVATE);
        authenticationAccount.setUpdatedAt(LocalDateTime.now());
        authenticationRepository.save(authenticationAccount);
    }

    public void deleteAccount(AuthenticationAccount authenticationAccount){ //soft delete
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
