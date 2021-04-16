package guru.springframework.sfgpetclinic.converters;

import guru.springframework.sfgpetclinic.forms.OwnerForm;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

class OwnerToOwnerFormTest {

    public static final Long ID = 1L;
    public static final String FIRST_NAME = "Po";
    public static final String LAST_NAME = "Marinova";
    public static final String ADDRESS = "fwo a owq 12";
    public static final String CITY = "Sofia";
    public static final String TELEPHONE = "4534685";
    public static final long PET_ID1 = 1L;
    public static final long PET_ID2 = 2L;

    OwnerToOwnerForm converter;

    @BeforeEach
    public void setUp() {
        converter = new OwnerToOwnerForm(new PetToPetForm(new PetTypeToPetTypeForm()));
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new Owner()));
    }

    @Test
    public void convert() {

        //given
        Owner owner = new Owner();
        owner.setId(ID);
        owner.setFirstName(FIRST_NAME);
        owner.setLastName(LAST_NAME);
        owner.setAddress(ADDRESS);
        owner.setCity(CITY);
        owner.setTelephone(TELEPHONE);

        Set<Pet> pets = new HashSet<>();
        Pet pet = new Pet();
        pet.setId(PET_ID1);
        pets.add(pet);
        Pet pet2 = new Pet();
        pet2.setId(PET_ID2);
        pets.add(pet2);
        owner.setPets(pets);

        //when
        OwnerForm form = converter.convert(owner);

        //then
        assertNotNull(form);
        assertEquals(ID, form.getId());
        assertEquals(FIRST_NAME, form.getFirstName());
        assertEquals(LAST_NAME, form.getLastName());
        assertEquals(ADDRESS, form.getAddress());
        assertEquals(CITY, form.getCity());
        assertEquals(TELEPHONE, form.getTelephone());
        assertNotNull(form.getPets());
        assertThat(form.getPets(), hasSize(2));
    }
}