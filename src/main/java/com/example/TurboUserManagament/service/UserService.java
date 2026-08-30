package com.example.TurboUserManagament.service;

import com.example.TurboUserManagament.appenum.AccountStatus;
import com.example.TurboUserManagament.appenum.UserRole;
import com.example.TurboUserManagament.converter.PhoneNumberConverter;
import com.example.TurboUserManagament.entity.AuthenticationAccount;
import com.example.TurboUserManagament.entity.User;
import com.example.TurboUserManagament.exception.UserAlreadyExistException;
import com.example.TurboUserManagament.exception.UserNotFoundException;
import com.example.TurboUserManagament.record.PhoneNumber;
import com.example.TurboUserManagament.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final PhoneNumberConverter phoneNumberConverter;

    public UserService(UserRepository userRepository,
                       AuthenticationService authenticationService,
                       PhoneNumberConverter phoneNumberConverter){
        this.userRepository=userRepository;
        this.authenticationService=authenticationService;
        this.phoneNumberConverter=phoneNumberConverter;
    }

    public User getById(Long id) {
        //Optional<User> means that the result might contain a user or may be empty
        Optional<User> user = userRepository.findByIdAndDeletedFalse(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException(
                    "Get by ID - User with id " + id + " not found"
            );
        }

        return user.get();
    }

    public User getUserByPhoneNumber(PhoneNumber phoneNumber){
        Optional<User> user= userRepository.findByPhoneNumberAndDeletedFalse(phoneNumber);
        if (user.isEmpty())
            throw new UserNotFoundException("Add User - User with this phone number already exist");

        return user.get();
    }

    public Page<User> getUsers(String firstName, String lastName, PhoneNumber phoneNumber, UserRole role, Pageable pageable) {
        Page<User> users;
        users= userRepository.getUsers(firstName, lastName, phoneNumber, role, pageable);
        return users;
    }

    public User createUser(User user) {
        if (!userRepository.findByPhoneNumberAndDeletedFalse(user.getPhoneNumber()).isEmpty())
            throw new UserAlreadyExistException("Add User - User with this phone number already exist");
        return userRepository.save(user);
    }

    public void deleteUser(Long userId){
        User user=getById(userId);
        authenticationService.deleteAccount(user.getAuthenticationAccount());
        userRepository.save(user);
    }

    public User updateUser(Long id, User updatedUser) {
        Optional<User> userOptional = userRepository.findByIdAndDeletedFalse(id);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(
                    "Update User - User not found"
            );
        }

        User user = userOptional.get();
        Optional<User> existingUser = userRepository.findByPhoneNumberAndDeletedFalse(updatedUser.getPhoneNumber());

        if (existingUser.isPresent() && !existingUser.get().getId().equals(id)) {

            throw new UserAlreadyExistException(
                    "Update User - User with this phone number already exists"
            );
        }

        user.setLastName(updatedUser.getLastName());
        user.setFirstName(updatedUser.getFirstName());
        user.setPhoneNumber(updatedUser.getPhoneNumber());

        return userRepository.save(user);
    }

    public User updatePartOfUser(Long id, User updatedUser) {
        Optional<User> userOptional = userRepository.findByIdAndDeletedFalse(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("Update user - User not found");
        }
        User user = userOptional.get();
        if (updatedUser.getPhoneNumber() != null) {
            Optional<User> existingUser = userRepository.findByPhoneNumberAndDeletedFalse(updatedUser.getPhoneNumber());

            if (existingUser.isPresent() && !existingUser.get().getId().equals(id)) {
                throw new UserAlreadyExistException(
                        "Update part of User - User with this phone number already exists"
                );
            }

            user.setPhoneNumber(updatedUser.getPhoneNumber());
        }
        if (updatedUser.getLastName() != null)
            user.setLastName(updatedUser.getLastName());
        if (updatedUser.getFirstName() != null)
            user.setFirstName(updatedUser.getFirstName());
        return userRepository.save(user); //return the user object after it is updated
    }

}
