package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.PetType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseMapServiceTest extends BaseMapService {

    PetTypeMapService service;

    public final long id = 1L;
    private final String cat = "cat";
    private final String dog = "dog";

    @BeforeEach
    void setUp() {
        // new map on each test
        service = new PetTypeMapService();
        service.save(PetType.builder().id(id).name(dog).build());
    }

    @Test
    void testFindAll() {
        assertEquals(1, service.findAll().size());
        service.save(PetType.builder().name(cat).build());
        assertEquals(2, service.findAll().size());
    }

    @Test
    void testFindById() {
        PetType returned = service.findById(id);
        assertEquals(id, returned.getId());
        assertEquals(dog, returned.getName());
    }

    @Test
    void testSaveExistingId() {
        service.save(PetType.builder().name(cat).id(2L).build());
        assertEquals(cat, service.findById(2L).getName());
    }

    @Test
    void testSaveNoId() {
        PetType savedType = service.save(PetType.builder().name(cat).build());
        assertNotNull(savedType.getId());
        assertEquals(cat, service.findById(savedType.getId()).getName());
    }

    @Test
    void testDeleteById() {
        assertNotNull(service.findById(id));
        service.deleteById(id);
        assertNull(service.findById(id));
    }

    @Test
    void testDelete() {
        PetType type = service.findById(id);
        assertNotNull(service.findById(type.getId()));
        service.delete(type);
        assertNull(service.findById(type.getId()));
    }
}