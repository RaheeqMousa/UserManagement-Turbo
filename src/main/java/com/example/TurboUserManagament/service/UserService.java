package com.example.TurboUserManagament.service;

import com.example.TurboUserManagament.entity.User;
import com.example.TurboUserManagament.repository.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public User getUser(Long userId){
        return null;
    }

    public void updateUser(){

    }

    public void removeUser(){

    }

    public List<User> getUsers(){
        return null;
    }
}
