package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.forms.PetForm;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

    @Mock
    PetService petService;

    @Mock
    OwnerService ownerService;

    @Mock
    PetTypeService petTypeService;

    @InjectMocks
    PetController petController;

    MockMvc mockMvc;

    Owner owner;
    Set<PetType> petTypes;

    @BeforeEach
    void setUp() {
        owner = Owner.builder().id(1L).build();

        petTypes = new HashSet<>();
        petTypes.add(PetType.builder().id(1L).name("Dog").build());
        petTypes.add(PetType.builder().id(2L).name("Cat").build());

        mockMvc = MockMvcBuilders
                .standaloneSetup(petController)
                .build();

        // Model attrs of the pet controller
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);
    }

    @Test
    void initCreationForm() throws Exception {

        mockMvc.perform(get("/owners/1/pets/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attributeExists("petForm"))
                .andExpect(model().attributeExists("types"))
                .andExpect(view().name(PetController.VIEW_PETS_CREATE_OR_UPDATE_PET_FORM));
    }

    @Test
    void processCreationForm() throws Exception {
        when(petService.savePetForm(any())).thenReturn(PetForm.builder().id(2L).ownerId(1L).build());

        mockMvc.perform(post("/owners/1/pets/new"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/owners/1"));

        verify(petService).savePetForm(any());
    }

    @Test
    void initUpdateForm() throws Exception {

        when(petService.findPetFormById(anyLong())).thenReturn(PetForm.builder().id(2L).ownerId(1L).build());

        mockMvc.perform(get("/owners/1/pets/2/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attributeExists("petForm"))
                .andExpect(view().name(PetController.VIEW_PETS_CREATE_OR_UPDATE_PET_FORM));
    }

    @Test
    void processUpdateForm() throws Exception {

        when(petService.savePetForm(any())).thenReturn(PetForm.builder().id(2L).ownerId(1L).build());

        mockMvc.perform(post("/owners/1/pets/2/edit"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/owners/1"));

        verify(petService).savePetForm(any());
    }
}