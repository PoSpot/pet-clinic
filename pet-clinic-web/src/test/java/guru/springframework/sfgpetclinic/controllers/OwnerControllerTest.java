package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    public static final long ID = 1L;
    public static final String LAST_NAME = "Po";

    @InjectMocks
    OwnerController controller;

    @Mock
    OwnerService service;

    @Spy
    Owner owner;

    @Mock
    Model model;

    @Captor
    // instead of ArgumentCaptor<List<Owner>> argumentCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<List<Owner>> argumentCaptor;

    MockMvc mockMvc;

    Set<Owner> owners;

    @BeforeEach
    void setUp() {
        owners = new HashSet<>();
        owners.add(Owner.builder().id(ID).build());
        owners.add(Owner.builder().id(2L).build());

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void findOwners() throws Exception {
        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"))
                .andExpect(model().attributeExists("owner"));

        verifyNoInteractions(service);
    }

    @Test
    void displayOwner() throws Exception {

        when(service.findById(anyLong())).thenReturn(Owner.builder().id(ID).build());

        mockMvc.perform(get("/owners/"+ID))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownerDetails"))
                .andExpect(model().attribute("owner", hasProperty("id", is(ID))));
    }

    @Test
    void processFindOwnersManyFound() throws Exception {

        when(service.findByLastNameLike(LAST_NAME)).thenReturn(new ArrayList<>(owners));

        mockMvc.perform(get("/owners").param("lastName", LAST_NAME))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"))
                .andExpect(model().attribute("selections", hasSize(owners.size())));

        verify(service).findByLastNameLike(LAST_NAME);
    }

    @Test /*Unable to send owner-mock with perform in order to verify(owner).setLastName("") =>
    so call the method directly*/
    // EXERCISE maybe u can: testPostNewRecipeForm in recipes
    void processFindOwnersEmptyStringAllOwners() {
        //given
        when(service.findByLastNameLike("")).thenReturn(new ArrayList<>(owners));

        //when
        String viewName = controller.processFindForm(owner, null, model);

        assertEquals("owners/ownersList", viewName);
        verify(owner).setLastName("");
        verify(service).findByLastNameLike("");
        // selections are owners + captors better in verify(), not in when()
        verify(model).addAttribute(eq("selections"), argumentCaptor.capture());
        assertEquals(owners.size(), argumentCaptor.getValue().size());
    }

    @Test
    void processFindOwnersOneFound() throws Exception {

        when(service.findByLastNameLike(anyString())).thenReturn(List.of(Owner.builder().id(ID).build()));

        mockMvc.perform(get("/owners"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/"+ID));
    }

    @Test
    void processFindOwnersNotFound() throws Exception {

        when(service.findByLastNameLike(anyString())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("owner", "lastName"))
                .andExpect(model().attributeHasFieldErrorCode("owner", "lastName", "notFound"))
                .andExpect(view().name("owners/findOwners"));
    }
}