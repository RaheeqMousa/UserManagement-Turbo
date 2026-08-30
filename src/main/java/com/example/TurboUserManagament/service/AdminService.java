package com.example.TurboUserManagament.service;

import com.example.TurboUserManagament.appenum.UserRole;
import com.example.TurboUserManagament.entity.AuthenticationAccount;
import com.example.TurboUserManagament.entity.User;
import com.example.TurboUserManagament.exception.UserNotFoundException;
import com.example.TurboUserManagament.record.PhoneNumber;
import com.example.TurboUserManagament.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class AdminService {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    public AdminService(UserService userService, AuthenticationService authenticationService){
        this.userService=userService;
        this.authenticationService=authenticationService;
    }

    public User getUserById(Long id){
        User user= userService.getById(id);
        if (user==null) {
            throw new UserNotFoundException(
                    "Get by ID - User with id " + id + " not found"
            );
        }

        return user;
    }

    public Page<User> getUsers(String firstName, String lastName, PhoneNumber phoneNumber, Pageable pageable, UserRole userRole){
        Page<User> users;
        users= userService.getUsers(firstName, lastName, phoneNumber, userRole, pageable);
        return users;
    }

    public void activateUser(Long userId){
        AuthenticationAccount authenticationAccount=getUserById(userId).getAuthenticationAccount();
        authenticationService.activateAccount(authenticationAccount);
    }

    public void deactivateUser(Long userId){
        AuthenticationAccount authenticationAccount=getUserById(userId).getAuthenticationAccount();
        authenticationService.deactivateAccount(authenticationAccount);
    }

    public void deleteUser(Long userId){
        AuthenticationAccount authenticationAccount=getUserById(userId).getAuthenticationAccount();
        authenticationService.deleteAccount(authenticationAccount);
    }

}