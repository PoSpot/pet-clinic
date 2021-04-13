package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {

    public static final String PET_NAME = "Terry";
    public static final String PET_TYPE = "dog";
    public static final long ID = 1L;
    public static final String LAST_NAME = "Smith";
    public static final String OTHER_LAST_NAME = "Marinova";

    OwnerMapService service;

    @BeforeEach
    void setUp() {
        service = new OwnerMapService(new PetMapService(new PetTypeMapService()));
        service.save(Owner.builder().id(ID).lastName(LAST_NAME).build());
    }

    @Test
    void findByLastName() {
        Owner owner = service.findByLastName(LAST_NAME);
        assertNotNull(owner);
        assertEquals(LAST_NAME, owner.getLastName());
    }

    @Test
    void findByLastNameNotFound() {
        assertNull(service.findByLastName(OTHER_LAST_NAME));
    }

    @Test
    void testSaveNull() {
        Owner savedOwner = service.save(null);
        assertNull(savedOwner);
    }

    @Test
    void testSave() {
        Owner savedOwner = service.save(Owner.builder().lastName(LAST_NAME).build());
        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
        assertEquals(LAST_NAME, savedOwner.getLastName());
    }

    @Test
    void testSaveOwnerWithPet() {
        Owner owner = Owner.builder().lastName(LAST_NAME).build();
        PetType type = PetType.builder().name(PET_TYPE).build();
        owner.getPets().add(Pet.builder().petType(type).owner(owner).name(PET_NAME).build());
        Owner savedOwner = service.save(owner);
        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
        assertNotNull(savedOwner.getPets());
        assertEquals(1, savedOwner.getPets().size());
    }

    @Test
    void testSaveOwnerWithPetWithoutPetType() {
        Owner owner = Owner.builder().lastName(LAST_NAME).build();
        owner.getPets().add(Pet.builder().owner(owner).name(PET_NAME).build());
        assertThrows(IllegalArgumentException.class, ()->service.save(owner));
    }
}