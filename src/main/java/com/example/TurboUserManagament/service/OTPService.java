package com.example.TurboUserManagament.service;

import com.example.TurboUserManagament.appenum.PhoneVerificationStatus;
import com.example.TurboUserManagament.entity.PhoneVerification;
import com.example.TurboUserManagament.entity.User;
import com.example.TurboUserManagament.record.PhoneNumber;
import com.example.TurboUserManagament.repository.PhoneVerificationRepository;

import java.time.LocalDateTime;
import java.util.Random;

public class OTPService {

    private PhoneVerificationRepository phoneVerificationRepository;

    public OTPService(PhoneVerificationRepository phoneVerificationRepository){
        this.phoneVerificationRepository=phoneVerificationRepository;
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

        sendSMS(user.getPhoneNumber(), otp);
        return phoneVerificationRepository.save(phoneVerification);
    }

    public boolean verifyOTP(String OTP, User user){
        //find the valid verification for the user's phone number
        //check whether the phone number had been already verified or not
        //check whether the otp expired or not
        //verify verification
        //mark phone as verified if otp is correct
        PhoneVerification phoneVerification= user.getPhoneVerifications()
                                                .getLast();
        if(phoneVerification.getStatus()==PhoneVerificationStatus.VERIFIED){
            return false;
        }
        if(phoneVerification.getStatus()==PhoneVerificationStatus.EXPIRED){
            return false;
        }
        if(!phoneVerification.getOtp().equalsIgnoreCase(OTP)){
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
    }

    private String generateOTP(){
        return String.valueOf(
          new Random().nextInt(900000)+100000
        );
    }

    private String hashOTP(String otp){
        return "";
    }

    private void sendSMS(PhoneNumber phone, String otp){

    }

}
