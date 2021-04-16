package guru.springframework.sfgpetclinic.converters;

import guru.springframework.sfgpetclinic.forms.PetForm;
import guru.springframework.sfgpetclinic.forms.PetTypeForm;
import guru.springframework.sfgpetclinic.model.Pet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

class PetFormToPetTest {

    public static final Long ID = 1L;
    public static final String NAME = "Po";
    public static final long PET_TYPE_ID = 1L;
    public static final long OWNER_ID = 2L;
    public static final String LAST_NAME = "Marinova";
    public static final String ADDRESS = "fwo a owq 12";
    public static final String CITY = "Sofia";
    public static final String TELEPHONE = "4534685";

    PetFormToPet converter;

    @BeforeEach
    public void setUp() {
        converter = new PetFormToPet(new PetTypeFormToPetType());
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new PetForm()));
    }

    @Test
    public void convert() {
        //given
        PetForm form = new PetForm();
        form.setId(ID);
        form.setName(NAME);
        PetTypeForm petType = new PetTypeForm();
        petType.setName("donkey");
        petType.setId(PET_TYPE_ID);
        form.setPetType(petType);
        form.setOwnerId(OWNER_ID);
        LocalDate date = LocalDate.now();
        form.setBirthDate(date);
        form.setVisitIds(Set.of(1L, 2L, 3L));

        //when
        Pet pet = converter.convert(form);

        //then
        assertNotNull(pet);
        assertEquals(ID, pet.getId());
        assertEquals(NAME, pet.getName());
        assertEquals("donkey", pet.getPetType().getName());
        assertEquals(PET_TYPE_ID, pet.getPetType().getId());
        assertEquals(OWNER_ID, pet.getOwner().getId());
        assertEquals(date, pet.getBirthDate());
        assertNotNull(pet.getVisits());
        assertThat(pet.getVisits(), hasSize(3));
    }

}