package com.kpatil.critter.service;

import com.kpatil.critter.entity.Customer;
import com.kpatil.critter.entity.Pet;
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
public class PetService {
    private static final Logger logger = LoggerFactory.getLogger(PetService.class);

    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public PetService(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    public Pet save(Pet pet, long ownerId) {
        logger.info("Creating a a new pet for ownerId = " + ownerId);
        Customer customer = this.customerRepository.getOne(ownerId);
        pet.setCustomer(customer);
        pet = petRepository.save(pet);

        logger.info("Updating customer with pet : " + pet.getId());
        customer.getPets().add(pet);
        this.customerRepository.save(customer);

        return pet;
    }

    public Pet getPet(long petId) {
        logger.info("Finding a pet with id " + petId);
        return this.petRepository.getOne(petId);
    }

    public List<Pet> getPets() {
        logger.info("Finding all pets ...");
        return this.petRepository.findAll();
    }

    public List<Pet> getPetsByOwner(long ownerId) {
        logger.info("Finding a pet by owner id " + ownerId);
        return this.petRepository.findByCustomerId(ownerId);
    }
}
