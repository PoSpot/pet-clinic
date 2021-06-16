package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.forms.VisitForm;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/owners/{ownerId}/pets/{petId}/visits")
@Controller
public class VisitController {

    public static final String PETS_CREATE_OR_UPDATE_VISIT_FORM = "pets/createOrUpdateVisitForm";
    private final VisitService visitService;
    private final PetService petService;

    public VisitController(VisitService visitService, PetService petService) {
        this.visitService = visitService;
        this.petService = petService;
    }

    // Pet in the model (it contains owner)
    @ModelAttribute("pet")
    public Pet findPet(@PathVariable Long petId) {
        return petService.findById(petId);
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/new")
    public String initCreationForm(Model model) {
        model.addAttribute("visitForm", new VisitForm());
        return PETS_CREATE_OR_UPDATE_VISIT_FORM;
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid VisitForm visitForm, BindingResult result, @PathVariable Long ownerId) {

        if (result.hasErrors()) {
            return PETS_CREATE_OR_UPDATE_VISIT_FORM;
        }

        // visitForm.petId auto-bound from URL
        visitService.saveVisitForm(visitForm);
        return "redirect:/owners/" + ownerId;
    }
}