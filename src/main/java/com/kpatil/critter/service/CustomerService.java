package com.kpatil.critter.service;

import com.kpatil.critter.entity.Customer;
import com.kpatil.critter.repository.CustomerRepository;
import com.kpatil.critter.repository.PetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CustomerService {

    private static final Logger logger =
            LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;
    private final PetRepository petRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, PetRepository petRepository) {
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
    }

    public Customer saveCustomer(Customer customer) {
        logger.info("Saving customer : " + customer.getName());
        return this.customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        logger.info("Finding all customers ...");
        return this.customerRepository.findAll();
    }

    public Customer getOwnerByPet(long petId) {
        logger.info("Find owner by pet id " + petId);
        return this.petRepository.getOne(petId).getCustomer();
    }
}
