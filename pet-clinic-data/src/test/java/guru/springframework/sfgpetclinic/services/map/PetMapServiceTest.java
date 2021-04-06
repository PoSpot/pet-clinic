package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PetMapServiceTest {

    PetMapService service;

    @BeforeEach
    void setUp() {
        service = new PetMapService(new PetTypeMapService());
    }

    @Test
    void testSave() {
        Owner owner = Owner.builder().lastName("Smith").build();
        PetType type = PetType.builder().name("dog").build();
        Pet pet = Pet.builder().petType(type).owner(owner).name("Terry").build();
        Pet savedPet = service.save(pet);
        assertNotNull(savedPet);
        assertNotNull(savedPet.getId());
        assertNotNull(savedPet.getPetType());
        assertEquals("dog", savedPet.getPetType().getName());
        assertNotNull(savedPet.getOwner());
        assertEquals("Smith", savedPet.getOwner().getLastName());
    }

    @Test
    void testSaveOwnerWithPetWithoutPetType() {
        Pet pet = Pet.builder().name("Terry").build();
        assertThrows(IllegalArgumentException.class, ()->service.save(pet));
    }
}