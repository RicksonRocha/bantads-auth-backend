package com.example.customerms.customer;

import com.example.customerms.customer.Customer;

public record CustomerResponseDTO(Long id, String name, String email, String password, String cpf, Float salary,
                                  String phoneNumber, String publicPlace, String state, String city, String number,
                                  String cep, String complement) {
    public CustomerResponseDTO(Customer customer) {
        this(customer.getId(), customer.getName(), customer.getEmail(), customer.getPassword(), customer.getCpf(),
                customer.getSalary(), customer.getPhoneNumber(), customer.getPublicPlace(), customer.getState(),
                customer.getCity(), customer.getNumber(), customer.getCep(), customer.getComplement());
    }
}
