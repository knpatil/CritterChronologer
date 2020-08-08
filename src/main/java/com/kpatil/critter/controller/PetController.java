package com.kpatil.critter.controller;

import com.kpatil.critter.entity.Pet;
import com.kpatil.critter.dto.PetDTO;
import com.kpatil.critter.service.PetService;
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

    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = Pet.build(petDTO);
        pet = this.petService.save(pet, petDTO.getOwnerId());
        return PetDTO.build(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = this.petService.getPet(petId);
        return PetDTO.build(pet);
    }

    @GetMapping
    public List<PetDTO> getPets() {
        List<Pet> pets = this.petService.getPets();
        return pets.stream()
                .map(PetDTO::build)
                .collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = this.petService.getPetsByOwner(ownerId);
        return pets.stream()
                .map(PetDTO::build)
                .collect(Collectors.toList());
    }
}
