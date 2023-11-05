package com.example.customerms.rabbitmq;

import com.example.customerms.controller.CustomerController;
import com.example.customerms.customer.CustomerRequestDTO;
import com.example.customerms.customer.CustomerResponseDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerListener {
    @Autowired
    private CustomerController customerController;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "saga.autocadastro.customer-req")
    public void onAutoRegister(CustomerRequestDTO data) {
        ResponseEntity<CustomerResponseDTO> customerResponse = this.customerController.saveCustomer(data);
        CustomerResponseDTO customerResponseDTO = customerResponse.getBody();

        String routingKey = "saga.autocadastro.customer-res";
        rabbitTemplate.convertAndSend(routingKey, customerResponseDTO);
    }
}
