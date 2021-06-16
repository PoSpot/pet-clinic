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
        converter = new OwnerToOwnerForm(new PetToPetForm());
    }

    @Test
    void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new Owner()));
    }

    @Test
    void convert() {

        //given
        Set<Pet> pets = new HashSet<>();
        Pet pet = Pet.builder().id(PET_ID1).build();
        pets.add(pet);
        Pet pet2 = Pet.builder().id(PET_ID2).build();
        pets.add(pet2);

        Owner owner = Owner.builder().id(ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .address(ADDRESS)
                .city(CITY)
                .telephone(TELEPHONE)
                .pets(pets).build();

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