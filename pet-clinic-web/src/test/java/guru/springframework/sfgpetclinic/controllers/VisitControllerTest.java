package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.forms.VisitForm;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    @Mock
    private VisitService visitService;

    @Mock
    private PetService petService;

    @InjectMocks
    VisitController controller;

    MockMvc mockMvc;

    Owner owner;
    Pet pet;

    @BeforeEach
    void setUp() {

        owner = Owner.builder().id(1L).build();
        pet = Pet.builder().id(2L).owner(owner).build();

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        // Model attrs of the visit controller
        given(petService.findById(2L)).willReturn(pet);
    }

    @Test
    void initCreationForm() throws Exception {

        //when
        mockMvc.perform(get("/owners/1/pets/2/visits/new"))
                .andExpect(status().isOk())
                .andExpect(view().name(VisitController.PETS_CREATE_OR_UPDATE_VISIT_FORM))
                .andExpect(model().attributeExists("visitForm"))
                .andExpect(model().attributeExists("pet"));

        //then
        verifyNoInteractions(visitService);
    }

    @Test
    void processCreationForm() throws Exception {
        //given
        given(visitService.saveVisitForm(any())).willReturn(VisitForm.builder().id(1L).build());

        //when
        mockMvc.perform(post("/owners/1/pets/2/visits/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("date","2018-11-11")
                .param("description", "vm ofjoe qok"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/owners/1"));

        //then
        verify(visitService).saveVisitForm(any());
    }
}