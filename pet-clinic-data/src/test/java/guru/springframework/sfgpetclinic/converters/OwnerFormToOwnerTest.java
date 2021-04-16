package guru.springframework.sfgpetclinic.converters;

import guru.springframework.sfgpetclinic.forms.OwnerForm;
import guru.springframework.sfgpetclinic.forms.PetForm;
import guru.springframework.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

class OwnerFormToOwnerTest {

    public static final Long ID = 1L;
    public static final String FIRST_NAME = "Po";
    public static final String LAST_NAME = "Marinova";
    public static final String ADDRESS = "fwo a owq 12";
    public static final String CITY = "Sofia";
    public static final String TELEPHONE = "4534685";
    public static final long PET_ID1 = 1L;
    public static final long PET_ID2 = 2L;

    OwnerFormToOwner converter;

    @BeforeEach
    public void setUp() {
        converter = new OwnerFormToOwner(new PetFormToPet(new PetTypeFormToPetType()));
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new OwnerForm()));
    }

    @Test
    public void convert() {
        //given
        OwnerForm form = new OwnerForm();
        form.setId(ID);
        form.setFirstName(FIRST_NAME);
        form.setLastName(LAST_NAME);
        form.setAddress(ADDRESS);
        form.setCity(CITY);
        form.setTelephone(TELEPHONE);

        Set<PetForm> pets = new HashSet<>();

        PetForm pet = new PetForm();
        pet.setId(PET_ID1);
        // Overtesting, these are not even very assertable (cos Set)
//        pet.setName(PETTY);
//        PetTypeForm petType = new PetTypeForm();
//        petType.setId(1L);
//        petType.setName("donkey");
//        pet.setPetType(petType);
//        pet.setOwnerId(ID);
//        pet.setBirthDate(LocalDate.now());
//        Set<Long> visits = Set.of(1L, 2L, 3L, 4L);
//        pet.setVisitIds(visits);
        pets.add(pet);
        PetForm pet2 = new PetForm();
        pet2.setId(PET_ID2);
        pets.add(pet2);
        form.setPets(pets);

        //when
        Owner owner = converter.convert(form);

        //then
        assertNotNull(owner);
        assertEquals(ID, owner.getId());
        assertEquals(FIRST_NAME, owner.getFirstName());
        assertEquals(LAST_NAME, owner.getLastName());
        assertEquals(ADDRESS, owner.getAddress());
        assertEquals(CITY, owner.getCity());
        assertEquals(TELEPHONE, owner.getTelephone());
        assertNotNull(owner.getPets());
        assertThat(owner.getPets(), hasSize(2));
    }
}