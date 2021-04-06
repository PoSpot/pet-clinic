package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    private final long id = 1L;
    private final String smith = "Smith";

    @InjectMocks
    OwnerSDJpaService service;

    @Mock
    OwnerRepository repo;

    Owner owner;

    @BeforeEach
    void setUp() {
        owner = Owner.builder().lastName(smith).id(id).build();
    }

    @Test
    void findByLastName() {
        when(repo.findByLastName(anyString())).thenReturn(Optional.of(owner));

        Owner found = service.findByLastName(smith);
        assertNotNull(found);
        assertEquals(smith, found.getLastName());
        verify(repo).findByLastName(anyString());
    }
}