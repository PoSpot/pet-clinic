package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest extends BaseMapService {

    OwnerMapService service;

    private final long id = 1L;
    private final String smith = "Smith";

    @BeforeEach
    void setUp() {
        service = new OwnerMapService(new PetMapService(new PetTypeMapService()));
        service.save(Owner.builder().id(id).lastName(smith).build());
    }

    @Test
    void findByLastName() {
        Owner owner = service.findByLastName(smith);
        assertNotNull(owner);
        assertEquals(smith, owner.getLastName());
    }

    @Test
    void findByLastNameNotFound() {
        assertNull(service.findByLastName("Marinova"));
    }

    @Test
    void testSaveNull() {
        Owner savedOwner = service.save(null);
        assertNull(savedOwner);
    }

    @Test
    void testSave() {
        Owner savedOwner = service.save(Owner.builder().lastName(smith).build());
        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
        assertEquals(smith, savedOwner.getLastName());
    }

    @Test
    void testSaveOwnerWithPet() {
        Owner owner = Owner.builder().lastName(smith).build();
        PetType type = PetType.builder().name("dog").build();
        owner.getPets().add(Pet.builder().petType(type).owner(owner).name("Terry").build());
        Owner savedOwner = service.save(owner);
        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
        assertNotNull(savedOwner.getPets());
        assertEquals(1, savedOwner.getPets().size());
    }

    @Test
    void testSaveOwnerWithPetWithoutPetType() {
        Owner owner = Owner.builder().lastName(smith).build();
        owner.getPets().add(Pet.builder().owner(owner).name("Terry").build());
        assertThrows(IllegalArgumentException.class, ()->service.save(owner));
    }
}