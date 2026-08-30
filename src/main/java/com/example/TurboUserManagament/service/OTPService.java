package com.example.TurboUserManagament.service;

import com.example.TurboUserManagament.appenum.AccountStatus;
import com.example.TurboUserManagament.appenum.PhoneVerificationStatus;
import com.example.TurboUserManagament.entity.PhoneVerification;
import com.example.TurboUserManagament.entity.User;
import com.example.TurboUserManagament.record.PhoneNumber;
import com.example.TurboUserManagament.repository.PhoneVerificationRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.security.SecureRandom;
import java.time.LocalDateTime;

public class OTPService {

    private final PhoneVerificationRepository phoneVerificationRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecureRandom secureRandom = new SecureRandom();

    public OTPService(PhoneVerificationRepository phoneVerificationRepository, PasswordEncoder passwordEncoder){
        this.phoneVerificationRepository=phoneVerificationRepository;
        this.passwordEncoder=passwordEncoder;
    }

    public PhoneVerification sendOTP(User user){
        String otp= generateOTP();
        String hashedOTP= hashOTP(otp);

        PhoneVerification phoneVerification= PhoneVerification.builder()
                .user(user)
                .otp(hashedOTP)
                .createdAt(LocalDateTime.now())
                .expiryDate(LocalDateTime.now().plusMinutes(5))
                .status(PhoneVerificationStatus.PENDING)
                .build();
        user.getPhoneVerifications().add(phoneVerification);

        PhoneVerification savedVerification= phoneVerificationRepository.save(phoneVerification);
        sendSMS(user.getPhoneNumber(), otp);
        return savedVerification;
    }

    public boolean verifyOTP(String OTP, User user){
        //find the valid verification for the user's phone number
        //check whether the phone number had been already verified or not
        //check whether the otp expired or not
        //verify verification
        //mark phone as verified if otp is correct
        if(user.getPhoneVerifications().isEmpty()){
            return false;
        }
        PhoneVerification phoneVerification= user.getPhoneVerifications()
                                                .getLast();
        if(phoneVerification.getStatus()==PhoneVerificationStatus.VERIFIED){
            return false;
        }
        if (LocalDateTime.now().isAfter(phoneVerification.getExpiryDate())) {
            phoneVerification.setStatus(PhoneVerificationStatus.EXPIRED);
            phoneVerificationRepository.save(phoneVerification);
            return false;
        }
        if(!passwordEncoder.matches(OTP, phoneVerification.getOtp())){
            return false;
        }
        phoneVerification.setStatus(PhoneVerificationStatus.VERIFIED);
        phoneVerification.setVerifiedAt(LocalDateTime.now());
        phoneVerificationRepository.save(phoneVerification);
        return true;
    }

    public void resendOTP(User user){
        //invalidate the old OTP status
        //generate new otp
        //sends new otp
        boolean verified=user.getPhoneVerifications()
                .stream()
                .anyMatch(pv->
                            pv.getStatus()==PhoneVerificationStatus.VERIFIED);
        if(verified){
            throw new IllegalArgumentException("Phone already verified");
        }
        if(!user.getPhoneVerifications().isEmpty()){
            PhoneVerification lastVerification=user.getPhoneVerifications().getLast();
            lastVerification.setStatus(PhoneVerificationStatus.EXPIRED);
            phoneVerificationRepository.save(lastVerification);

        }
        String otp= generateOTP();
        String hashedOTP= hashOTP(otp);

        PhoneVerification phoneVerification= PhoneVerification.builder()
                .user(user)
                .otp(hashedOTP)
                .createdAt(LocalDateTime.now())
                .expiryDate(LocalDateTime.now().plusMinutes(5))
                .status(PhoneVerificationStatus.PENDING)
                .build();

        user.getPhoneVerifications().add(phoneVerification);
        phoneVerificationRepository.save(phoneVerification);
        sendSMS(user.getPhoneNumber(), otp);
    }

    private String generateOTP(){
        return String.valueOf(
          secureRandom.nextInt(900000)+100000
        );
    }

    private String hashOTP(String otp){
        return passwordEncoder.encode(otp);
    }

    private void sendSMS(PhoneNumber phone, String otp){

    }

}
