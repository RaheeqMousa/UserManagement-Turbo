package com.example.TurboUserManagament.service;

import com.example.TurboUserManagament.appenum.UserRole;
import com.example.TurboUserManagament.entity.Address;
import com.example.TurboUserManagament.entity.Customer;
import com.example.TurboUserManagament.entity.User;
import com.example.TurboUserManagament.exception.CustomerAlreadyExistException;
import com.example.TurboUserManagament.exception.NotAvailableCustomerLocationException;
import com.example.TurboUserManagament.record.CustomerRegistration;
import com.example.TurboUserManagament.repository.CustomerRepository;
import com.example.TurboUserManagament.repository.UserRepository;

import java.time.LocalDateTime;

public class CustomerService {

    private AuthenticationService authenticationService;
    private CustomerRepository customerRepository;
    private UserRepository userRepository;

    public CustomerService(AuthenticationService authenticationService,
                           CustomerRepository customerRepository,
                           UserRepository userRepository){
        this.authenticationService=authenticationService;
        this.customerRepository=customerRepository;
        this.userRepository=userRepository;
    }

    public Customer registerCustomer(CustomerRegistration registration){
        User user= userRepository.findByPhoneNumber(registration.phoneNumber());

        if(user==null) {
            user = User.builder()
                    .firstName(registration.firstName())
                    .lastName(registration.lastName())
                    .phoneNumber(registration.phoneNumber())
                    .role(UserRole.CUSTOMER)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            authenticationService.register(user, registration.password());
        }

        if(customerRepository.findByID(user.getId())!=null){
            throw new CustomerAlreadyExistException("Customer with this phone number already exist");
        }

        Customer customer= new Customer();
        customer.setUser(user);
        customer.setBirthDate(registration.birthDate());

        return customerRepository.save(customer);
    }

    public void getCustomer(Long id){

    }

    public void updateCustomer(Customer customer){

    }

    public void addAddress(Customer customer, Address address){

    }

    public void removeAddress(Customer customer, Long addressId){

    }

    public Address getDeliveryAddress(Customer customer) {

        if (customer.getSelectedAddress() != null) {
            return customer.getSelectedAddress();
        }

        if(customer.getCurrentLatitude()==null || customer.getCurrentLatitude()==null){
            throw new NotAvailableCustomerLocationException("customer has no selected address or current location");
        }

        Address currentLocation = new Address();

        currentLocation.setLatitude(customer.getCurrentLatitude());
        currentLocation.setLongitude(customer.getCurrentLongitude());

        return currentLocation;
    }

}
