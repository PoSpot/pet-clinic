package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.converters.OwnerFormToOwner;
import guru.springframework.sfgpetclinic.converters.OwnerToOwnerForm;
import guru.springframework.sfgpetclinic.forms.OwnerForm;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    public static final long ID = 1L;
    public static final String LAST_NAME = "Smith";

    @InjectMocks
    OwnerSDJpaService service;

    @Mock
    OwnerRepository repo;

    @Mock
    // very questionable should these be mocked..to light, better use real, as h2 db
    OwnerToOwnerForm ownerToOwnerForm;

    @Mock
    OwnerFormToOwner ownerFormToOwner;

    Owner owner;

    @BeforeEach
    void setUp() {
        owner = Owner.builder().lastName(LAST_NAME).id(ID).build();
    }

    @Test
    void findByLastNameLike() {
        //given
        given(repo.findByLastNameLike("%" + LAST_NAME + "%")).willReturn(List.of(Owner.builder().id(ID).build(),
                                                                                    Owner.builder().id(2L).build(),
                                                                                    Owner.builder().id(3L).build()));

        //when
        List<Owner> list = service.findByLastNameLike(LAST_NAME);

        //then
        assertNotNull(list);
        assertThat(list, hasSize(3));
        verify(repo).findByLastNameLike("%" + LAST_NAME + "%");
    }

    @Test
    // A strange test, not much to test..could be unmocked & assert the saved owner props.
    void saveOwnerForm() {
        //given
        OwnerForm ownerForm = OwnerForm.builder().id(ID).build();
        given(ownerFormToOwner.convert(ownerForm)).willReturn(owner);
        given(repo.save(owner)).willReturn(owner);
        given(ownerToOwnerForm.convert(owner)).willReturn(ownerForm);

        //when
        OwnerForm savedForm = service.saveOwnerForm(ownerForm);

        //then
        assertNotNull(savedForm);
        assertEquals(ownerForm.getId(), savedForm.getId());
        assertEquals(ownerForm.getLastName(), savedForm.getLastName());

        verify(ownerFormToOwner).convert(ownerForm);
        verify(repo).save(owner);
        verify(ownerToOwnerForm).convert(owner);
    }

    @Test
    void findOwnerFormById() {
        //given
        when(repo.findById(anyLong())).thenReturn(Optional.of(owner));

        OwnerForm ownerForm = OwnerForm.builder().id(ID).build();

        when(ownerToOwnerForm.convert(any())).thenReturn(ownerForm);

        //when
        OwnerForm formById = service.findOwnerFormById(ID);

        //then
        assertNotNull(formById, "Null recipe returned");
        assertEquals(ownerForm.getId(), formById.getId());

        verify(repo).findById(anyLong());
        verify(ownerToOwnerForm).convert(any());
    }
}