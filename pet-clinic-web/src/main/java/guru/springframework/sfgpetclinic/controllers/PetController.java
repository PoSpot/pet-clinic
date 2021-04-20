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
            // petForm.ownerId is auto-bound from URL
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
            // petForm.id & .ownerId are auto-bound from URL
            PetForm savedPetForm = petService.savePetForm(petForm);
            return "redirect:/owners/" + savedPetForm.getOwnerId();
        }
    }

}
