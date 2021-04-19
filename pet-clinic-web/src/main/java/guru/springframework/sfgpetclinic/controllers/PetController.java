package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.forms.PetForm;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@Controller
@RequestMapping("/owners/{ownerId}/pets")
public class PetController {

    public static final String VIEW_PETS_CREATE_OR_UPDATE_PET_FORM = "pets/createOrUpdatePetForm";

    private final PetService petService;
    private final OwnerService ownerService;
    private final PetTypeService petTypeService;

    public PetController(PetService petService, OwnerService ownerService, PetTypeService petTypeService) {
        this.petService = petService;
        this.ownerService = ownerService;
        this.petTypeService = petTypeService;
    }

    @ModelAttribute("types")
    public Set<PetType> populatePetTypes() {
        return petTypeService.findAll();
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable Long ownerId) {
        return ownerService.findById(ownerId);
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/new")
    public String initCreationForm(Model model) {
        model.addAttribute("petForm", new PetForm());

        return VIEW_PETS_CREATE_OR_UPDATE_PET_FORM;
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid PetForm petForm, BindingResult result, @ModelAttribute Owner owner) {
        if (StringUtils.hasLength(petForm.getName()) && petForm.isNew() && owner.getPet(petForm.getName(), true) != null) {
            result.rejectValue("name", "duplicate", "already exists");
        }
        if (result.hasErrors()) {
            return VIEW_PETS_CREATE_OR_UPDATE_PET_FORM;
        } else {
            PetForm savedPet = petService.savePetForm(petForm);
            return "redirect:/owners/" + savedPet.getOwnerId();
        }
    }

    @GetMapping("/{id}/edit")
    public String initUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("petForm", petService.findPetFormById(id));
        return VIEW_PETS_CREATE_OR_UPDATE_PET_FORM;
    }

    @PostMapping("/{id}/edit")
    public String processUpdateForm(@Valid PetForm petForm, BindingResult result) {
        if (result.hasErrors()) {
            return VIEW_PETS_CREATE_OR_UPDATE_PET_FORM;
        } else {
            // petForm.setId(id); it has it!
            PetForm savedPetForm = petService.savePetForm(petForm);
            return "redirect:/owners/" + savedPetForm.getOwnerId();
        }
    }

    // currently no issues with new/update

    // types and owner are avail.

    // maybe no setAllowedFields, cos we have one hidden id in html (we have one for owner above)

    // FIXED EL1008E: Property or field 'owner' cannot be found on object of type 'guru.springframework.sfgpetclinic.forms.PetForm' - maybe not public or not valid?
    // FIXED Exception evaluating SpringEL expression: "pet.owner?.firstName + ' ' + pet.owner?.lastName" (template: "pets/createOrUpdatePetForm.html" - line 16, col 17)
    // 'owner' in model cos not in my form + it reads from ctrlr's method: @ModelAttribute("owner") findOwner

    // Invalid property 'type' of bean class [guru.springframework.sfgpetclinic.forms.PetForm]:
    // Bean property 'type' is not readable or has an invalid getter method: Does the return type of the getter match the parameter type of the setter?
    // -> pettype in html

    // EL1012E: Cannot index into a null value
    // Exception evaluating SpringEL expression: "pet['new']" (template: "pets/createOrUpdatePetForm.html" - line 7, col 15) ->

//    EL1007E: Property or field 'id' cannot be found on null
// but is there =1 ->
    // again wrong urls -> OK

    //  Neither BindingResult nor plain target object for bean name 'pet' available as request attribute
    // Exception evaluating SpringEL expression: "!#fields.hasErrors(name)" (template: "fragments/inputField" - line 5, col 12)

    // with 'pet' attr name -> would populate the form but is null on submit??

    // pfu renamed model attr -> petForm, but all attr are still in red in html -> Maybe invalidate cache fixed it

}
