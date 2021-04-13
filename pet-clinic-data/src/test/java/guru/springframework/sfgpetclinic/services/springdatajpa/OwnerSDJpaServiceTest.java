package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
}