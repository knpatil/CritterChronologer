package com.kpatil.critter.controller;

import com.kpatil.critter.dto.PetDTO;
import com.kpatil.critter.entity.Pet;
import com.kpatil.critter.service.PetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private static final Logger logger = LoggerFactory.getLogger(PetController.class);

    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        logger.info("Received request to create new pet ...");
        Pet pet = Pet.build(petDTO);
        pet = this.petService.save(pet, petDTO.getOwnerId());
        logger.info("Pet create with ID : " + pet.getId());
        return PetDTO.build(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        logger.info("Received request to get pet info for id : " + petId);
        Pet pet = this.petService.getPet(petId);
        logger.info("Found pet : " + pet.getName());
        return PetDTO.build(pet);
    }

    @GetMapping
    public List<PetDTO> getPets() {
        logger.info("Received request to get all pets ...");
        List<Pet> pets = this.petService.getPets();
        return pets.stream()
                .map(PetDTO::build)
                .collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        logger.info("Received request get all pets by owner id : " + ownerId);
        List<Pet> pets = this.petService.getPetsByOwner(ownerId);
        logger.info(String.format("Found %d pets.", pets.size()));
        return pets.stream()
                .map(PetDTO::build)
                .collect(Collectors.toList());
    }
}
