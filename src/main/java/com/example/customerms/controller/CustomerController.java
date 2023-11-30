package com.example.customerms.controller;

import com.example.customerms.customer.Customer;
import com.example.customerms.customer.CustomerRequestDTO;
import com.example.customerms.customer.CustomerResponseDTO;
import com.example.customerms.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("api/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAll() {
        List<CustomerResponseDTO> customerList = customerRepository.findAll().stream()
                .map(CustomerResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(customerList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isPresent()) {
            CustomerResponseDTO responseDTO = new CustomerResponseDTO(customer.get());
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> saveCustomer(@ModelAttribute CustomerRequestDTO data) {
        Customer customerData = new Customer(data);
        customerData = customerRepository.save(customerData);
        CustomerResponseDTO responseDTO = new CustomerResponseDTO(customerData);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable Long id,
            @ModelAttribute CustomerRequestDTO data) {
        Optional<Customer> existingCustomer = customerRepository.findById(id);

        if (existingCustomer.isPresent()) {
            Customer customer = existingCustomer.get();

            customer.setName(data.name());
            customer.setEmail(data.email());
            customer.setCpf(data.cpf());
            customer.setPhoneNumber(data.phoneNumber());
            customer.setState(data.state());
            customer.setCity(data.city());
            customer.setPublicPlace(data.publicPlace());
            customer.setNumber(data.number());
            customer.setCep(data.cep());

            customerRepository.save(customer);

            CustomerResponseDTO responseDTO = new CustomerResponseDTO(customer);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> deleteCustomer(@PathVariable Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            customerRepository.delete(customer);
            CustomerResponseDTO responseDTO = new CustomerResponseDTO(customer);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
