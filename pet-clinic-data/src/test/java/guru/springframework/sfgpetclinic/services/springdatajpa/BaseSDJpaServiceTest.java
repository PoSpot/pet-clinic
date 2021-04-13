package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BaseSDJpaServiceTest {

    public static final long ID = 1L;
    public static final String LAST_NAME = "Smith";
    public static final String PET_TYPE = "dog";
    public static final String PET_NAME = "Terry";
    public static final String OTHER_PET_TYPE = "cat";
    public static final String OTHER_PET_NAME = "Tochka";

    @InjectMocks
    PetSDJpaService service;

    @Mock
    PetRepository repo;

    Pet pet;

    @BeforeEach
    void setUp() {
        Owner owner = Owner.builder().lastName(LAST_NAME).build();
        PetType type = PetType.builder().name(PET_TYPE).build();
        pet = Pet.builder().petType(type).owner(owner).name(PET_NAME).id(ID).build();
    }

    @Test
    void findAll() {
        Set<Pet> set = new HashSet<>();
        set.add(pet);
        set.add(Pet.builder().owner(pet.getOwner())
                            .name(OTHER_PET_NAME)
                            .petType(PetType.builder().name(OTHER_PET_TYPE).build())
                            .build());
        when(repo.findAll()).thenReturn(set);

        Set<Pet> all = service.findAll();
        assertNotNull(all);
        assertEquals(2, all.size());
        verify(repo).findAll();
    }

    @Test
    void findById() {
        when(repo.findById(anyLong())).thenReturn(Optional.of(pet));

        Pet petFound = service.findById(ID);
        assertNotNull(petFound);
        assertEquals(pet.getId(), petFound.getId());
        verify(repo).findById(anyLong());
    }

    @Test
    void findByIdNotFound() {
        when(repo.findById(anyLong())).thenReturn(Optional.empty());

        Pet petFound = service.findById(ID);
        assertNull(petFound);
        verify(repo).findById(anyLong());
    }

    @Test
    void save() {
        when(repo.save(any())).thenReturn(pet);
        Pet savedPet = service.save(pet);
        assertNotNull(savedPet);
        verify(repo).save(any());
    }

    @Test
    void deleteById() {
        service.deleteById(ID);
        verify(repo).deleteById(ID);
    }

    @Test
    void delete() {
        service.delete(pet);
        verify(repo).delete(pet);
    }

    // EXERCISE ...different scenarios to be added...as map service
}