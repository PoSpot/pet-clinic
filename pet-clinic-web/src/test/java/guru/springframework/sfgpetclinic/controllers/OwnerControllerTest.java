package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.forms.OwnerForm;
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
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
                .andExpect(view().name(OwnerController.VIEW_OWNERS_FIND_OWNERS))
                .andExpect(model().attributeExists("owner"));

        verifyNoInteractions(service);
    }

    @Test
    void displayOwner() throws Exception {

        when(service.findById(anyLong())).thenReturn(Owner.builder().id(ID).build());

        mockMvc.perform(get("/owners/" + ID))
                .andExpect(status().isOk())
                .andExpect(view().name(OwnerController.VIEW_OWNERS_OWNER_DETAILS))
                .andExpect(model().attribute("owner", hasProperty("id", is(ID))));
    }

    @Test
    void processFindOwnersManyFound() throws Exception {

        when(service.findByLastNameLike(LAST_NAME)).thenReturn(new ArrayList<>(owners));

        mockMvc.perform(get("/owners").param("lastName", LAST_NAME))
                .andExpect(status().isOk())
                .andExpect(view().name(OwnerController.VIEW_OWNERS_OWNERS_LIST))
                .andExpect(view().name(OwnerController.VIEW_OWNERS_OWNERS_LIST))
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

        assertEquals(OwnerController.VIEW_OWNERS_OWNERS_LIST, viewName);
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
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/owners/" + ID));
        // Test a method that doesn't set attrs + u check attr -> NOK (this line checks ctrlr @ModelAttr params)
        // .andExpect(model().attributeExists("owner"));
    }

    @Test
    void processFindOwnersNotFound() throws Exception {

        when(service.findByLastNameLike(anyString())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("owner", "lastName"))
                .andExpect(model().attributeHasFieldErrorCode("owner", "lastName", "notFound"))
                .andExpect(view().name(OwnerController.VIEW_OWNERS_FIND_OWNERS));
    }

    @Test
    void initCreationForm() throws Exception {
        mockMvc.perform(get("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(view().name(OwnerController.VIEW_OWNERS_CREATE_OR_UPDATE_OWNER_FORM))
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attribute("owner", hasProperty("new", is(true))));

        verifyNoInteractions(service);
    }

    @Test
    void processCreationForm() throws Exception {
        OwnerForm ownerForm = new OwnerForm();
        ownerForm.setId(ID);
        when(service.saveOwnerForm(any())).thenReturn(ownerForm);

        mockMvc.perform(post("/owners/new")
                .param("firstName", "Andy")
                .param("city", "Sofia"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/owners/" + ID));
        // Test a method that doesn't set attrs + u check attr -> NOK (this line checks ctrlr @ModelAttr params)
        //.andExpect(model().attributeExists("ownerForm"));//"owner"));
        // HERE OR EXTRa CTRLR PARAM!? (and it's about the type 'OwnerForm'(not the name) of ctrlr param
        // (i.e. '@Valid OwnerForm owner' param won't work)...(?))

        verify(service).saveOwnerForm(any());
    }

    @Test
    void initUpdateForm() throws Exception {
        OwnerForm ownerForm = new OwnerForm();
        ownerForm.setId(ID);
        when(service.findOwnerFormById(anyLong())).thenReturn(ownerForm);

        mockMvc.perform(get("/owners/" + ID + "/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name(OwnerController.VIEW_OWNERS_CREATE_OR_UPDATE_OWNER_FORM))
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attribute("owner", hasProperty("new", is(false))));

        verify(service).findOwnerFormById(ID);
    }

    @Test
        // TODO Negative scenarios on the 2 posts, when validation added: see
        //  https://github.com/spring-projects/spring-framework/blob/master/spring-test/src/test/java/org/springframework/test/web/servlet/samples/standalone/RedirectTests.java
    void processUpdateForm() throws Exception {
        OwnerForm ownerForm = new OwnerForm();
        ownerForm.setId(ID);
        when(service.saveOwnerForm(any())).thenReturn(ownerForm);

        mockMvc.perform(post("/owners/" + ID + "/edit")
                .param("address", "")
                .param("city", "Sofia"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/owners/" + ID));
        // Test a method that doesn't set attrs + u check attr -> NOK (this line checks ctrlr @ModelAttr params)
        // .andExpect(model().attributeExists("owner"));

        verify(service).saveOwnerForm(any());
    }
}