package com.example.customerms.customer;

public record CustomerRequestDTO(String name, String email, String password, String cpf, Float salary,
                                 String phoneNumber, String publicPlace, String state, String city, String number,
                                 String cep, String complement) {
}
