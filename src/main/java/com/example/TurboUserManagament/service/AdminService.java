package com.example.TurboUserManagament.service;

import com.example.TurboUserManagament.appenum.UserRole;
import com.example.TurboUserManagament.entity.User;
import com.example.TurboUserManagament.exception.UserNotFoundException;
import com.example.TurboUserManagament.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class AdminService {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    public AdminService(UserRepository userRepository, AuthenticationService authenticationService){
        this.userRepository=userRepository;
        this.authenticationService=authenticationService;
    }

    public User getUserById(Long id){
        User user= userRepository.findById(id);
        if (user==null) {
            throw new UserNotFoundException(
                    "Get by ID - User with id " + id + " not found"
            );
        }

        return user;
    }

    public Page<User> getUsers(String firstName, String lastName, String phoneNumber, Pageable pageable, UserRole userRole){
        Page<User> users;
        users= userRepository.getUsers(firstName, lastName, phoneNumber, userRole, pageable);
        return users;
    }

    public void activateUser(Long userId){
        authenticationService.activateAccount(getUserById(userId));
    }

    public void deactivateUser(Long userId){
        authenticationService.deactivateAccount(getUserById(userId));
    }

    public void deleteUser(Long userId){
        authenticationService.deleteAccount(getUserById(userId));
    }

}