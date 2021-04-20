package guru.springframework.sfgpetclinic.converters;

import guru.springframework.sfgpetclinic.forms.PetForm;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.model.Visit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;

class PetToPetFormTest {

    // + same tests in PetType & Visit converters
    public static final Long ID = 1L;
    public static final String NAME = "Po";
    public static final long PET_TYPE_ID = 1L;
    public static final long OWNER_ID = 2L;
    public static final String LAST_NAME = "Marinova";
    public static final String ADDRESS = "fwo a owq 12";
    public static final String CITY = "Sofia";
    public static final String TELEPHONE = "4534685";

    PetToPetForm converter;

    @BeforeEach
    public void setUp() {
        converter = new PetToPetForm(new PetTypeToPetTypeForm());
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new Pet()));
    }

    @Test
    public void convert() {
        //given
        LocalDate date = LocalDate.now();
        PetType petType = PetType.builder().id(PET_TYPE_ID).name("donkey").build();
        Set<Visit> visits = Set.of(Visit.builder().id(1L).build(), Visit.builder().id(2L).build(), Visit.builder().id(3L).build());

        //when
        PetForm form = converter.convert(Pet.builder().id(ID).name(NAME)
                                                        .petType(petType)
                                                        .owner(Owner.builder().id(OWNER_ID).build())
                                                        .birthDate(date)
                                                        .visits(visits).build());

        //then
        assertNotNull(form);
        assertEquals(ID, form.getId());
        assertEquals(NAME, form.getName());
        assertEquals(petType, form.getPetType());
        assertEquals(OWNER_ID, form.getOwnerId());
        assertEquals(date, form.getBirthDate());
        assertNotNull(form.getVisitIds());
        assertThat(form.getVisitIds(), containsInAnyOrder(1L, 2L, 3L));
    }
}