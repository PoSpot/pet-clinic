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

    private final long id = 1L;

    @InjectMocks
    PetSDJpaService service;

    @Mock
    PetRepository repo;

    Pet pet;

    @BeforeEach
    void setUp() {
        Owner owner = Owner.builder().lastName("Smith").build();
        PetType type = PetType.builder().name("dog").build();
        pet = Pet.builder().petType(type).owner(owner).name("Terry").id(id).build();
    }

    @Test
    void findAll() {
        Set<Pet> set = new HashSet<>();
        set.add(pet);
        set.add(Pet.builder().owner(pet.getOwner())
                            .name("Tochka")
                            .petType(PetType.builder().name("cat").build())
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

        Pet petFound = service.findById(id);
        assertNotNull(petFound);
        assertEquals(pet.getId(), petFound.getId());
        verify(repo).findById(anyLong());
    }

    @Test
    void findByIdNotFound() {
        when(repo.findById(anyLong())).thenReturn(Optional.empty());

        Pet petFound = service.findById(id);
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
        service.deleteById(id);
        verify(repo).deleteById(id);
    }

    @Test
    void delete() {
        service.delete(pet);
        verify(repo).delete(pet);
    }

    // ...different scenarios to be added...as map service
}