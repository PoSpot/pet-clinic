package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PetMapServiceTest {

    public static final String LAST_NAME = "Smith";
    public static final String PET_TYPE = "dog";
    public static final String PET_NAME = "Terry";
    PetMapService service;

    @BeforeEach
    void setUp() {
        service = new PetMapService(new PetTypeMapService());
    }

    @Test
    void testSave() {
        Owner owner = Owner.builder().lastName(LAST_NAME).build();
        PetType type = PetType.builder().name(PET_TYPE).build();
        Pet pet = Pet.builder().petType(type).owner(owner).name(PET_NAME).build();
        Pet savedPet = service.save(pet);
        assertNotNull(savedPet);
        assertNotNull(savedPet.getId());
        assertNotNull(savedPet.getPetType());
        assertEquals(PET_TYPE, savedPet.getPetType().getName());
        assertNotNull(savedPet.getOwner());
        assertEquals(LAST_NAME, savedPet.getOwner().getLastName());
    }

    @Test
    void testSaveOwnerWithPetWithoutPetType() {
        Pet pet = Pet.builder().name(PET_NAME).build();
        assertThrows(IllegalArgumentException.class, ()->service.save(pet));
    }
}