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

    public static final long ID = 1L;
    public static final String LAST_NAME = "Smith";

    @InjectMocks
    OwnerSDJpaService service;

    @Mock
    OwnerRepository repo;

    Owner owner;

    @BeforeEach
    void setUp() {
        owner = Owner.builder().lastName(LAST_NAME).id(ID).build();
    }

    @Test
    void findByLastName() {
        when(repo.findByLastName(anyString())).thenReturn(Optional.of(owner));

        Owner found = service.findByLastName(LAST_NAME);
        assertNotNull(found);
        assertEquals(LAST_NAME, found.getLastName());
        verify(repo).findByLastName(anyString());
    }
}