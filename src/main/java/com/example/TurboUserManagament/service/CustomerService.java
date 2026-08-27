package com.example.TurboUserManagament.service;

import com.example.TurboUserManagament.appenum.AddressStatus;
import com.example.TurboUserManagament.appenum.UserRole;
import com.example.TurboUserManagament.entity.Address;
import com.example.TurboUserManagament.entity.Customer;
import com.example.TurboUserManagament.entity.User;
import com.example.TurboUserManagament.exception.CustomerAlreadyExistException;
import com.example.TurboUserManagament.exception.NotAvailableCustomerLocationException;
import com.example.TurboUserManagament.exception.UserNotFoundException;
import com.example.TurboUserManagament.record.CustomerRegistration;
import com.example.TurboUserManagament.repository.CustomerRepository;
import com.example.TurboUserManagament.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class CustomerService {

    private final AuthenticationService authenticationService;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public CustomerService(AuthenticationService authenticationService,
                           CustomerRepository customerRepository,
                           UserRepository userRepository){
        this.authenticationService=authenticationService;
        this.customerRepository=customerRepository;
        this.userRepository=userRepository;
    }

    public Customer getCustomer(Long id){
        Customer customer=customerRepository.findByID(id);
        if(customer==null){
            throw new UserNotFoundException("customer not found");
        }
        return customer;
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
            user = userRepository.save(user);
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

    public Customer updateCustomer(Long id,Customer updatedCustomer){
        Customer existingCustomer=getCustomer(id);

        existingCustomer.setBirthDate(updatedCustomer.getBirthDate());
        existingCustomer.setCurrentLatitude(updatedCustomer.getCurrentLatitude());
        existingCustomer.setCurrentLongitude(updatedCustomer.getCurrentLongitude());

        return customerRepository.save(existingCustomer);
    }

    public void addAddress(Customer customer, Address address){
        address.setStatus(AddressStatus.ACTIVE);
        customer.getAddresses().add(address);
    }

    public void removeAddress(Customer customer, Long addressId){
        List<Address> addresses=customer.getAddresses();
        for(int i=0;i<addresses.size();i++){
            Address address= addresses.get(i);
            if(address.getId().equals(addressId)){
                address.setStatus(AddressStatus.DELETED);
            }
        }
    }

    public Address getDeliveryAddress(Customer customer) {

        if (customer.getSelectedAddress() != null) {
            return customer.getSelectedAddress();
        }

        if(customer.getCurrentLatitude()==null || customer.getCurrentLongitude()==null){
            throw new NotAvailableCustomerLocationException("customer has no selected address or current location");
        }

        Address currentLocation = Address.builder()
                        .latitude(customer.getCurrentLatitude())
                        .longitude(customer.getCurrentLongitude())
                        .build();

        return currentLocation;
    }

}
