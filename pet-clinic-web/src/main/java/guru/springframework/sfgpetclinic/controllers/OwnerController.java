package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.forms.OwnerForm;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RequestMapping("/owners")
@Controller
public class OwnerController {

    public static final String VIEW_OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    public static final String VIEW_OWNERS_FIND_OWNERS = "owners/findOwners";
    public static final String VIEW_OWNERS_OWNERS_LIST = "owners/ownersList";
    public static final String VIEW_OWNERS_OWNER_DETAILS = "owners/ownerDetails";

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/find")
    public String initFindForm(Model model) {
        model.addAttribute("owner", new Owner());
        return VIEW_OWNERS_FIND_OWNERS;
    }

    @GetMapping
    public String processFindForm(Owner owner, BindingResult result, Model model) {

        // allow parameterless GET request for /owners to return all records
        if (owner.getLastName() == null) {
            owner.setLastName(""); // empty string signifies broadest possible search
        }

        // find owners by last name
        Collection<Owner> results = this.ownerService.findByLastNameLike(owner.getLastName());
        if (results.isEmpty()) {
            // no owners found
            // back to the form with an error set to the lastName field
            result.rejectValue("lastName", "notFound", "not found");
            return VIEW_OWNERS_FIND_OWNERS;
        } else if (results.size() == 1) {
            // 1 owner found
            owner = results.iterator().next();
            return "redirect:/owners/" + owner.getId();
        } else {
            // multiple owners found
            model.addAttribute("selections", results);
            // -> http://localhost:8080/owners?lastName=Wilson :
            // i.e. the current URL (+ the Path var, cos the method is GET)
            return VIEW_OWNERS_OWNERS_LIST;
        }
    }

    @GetMapping("/{id}")
    public String displayOwner(@PathVariable Long id, Model model) {

        model.addAttribute("owner", ownerService.findById(id));
        return VIEW_OWNERS_OWNER_DETAILS;
    }

    @GetMapping("/new")
    public String initCreationForm(Model model){
        model.addAttribute("owner", new OwnerForm());

        return VIEW_OWNERS_CREATE_OR_UPDATE_OWNER_FORM;
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid OwnerForm ownerForm, BindingResult result){
        if (result.hasErrors()) {
            return VIEW_OWNERS_CREATE_OR_UPDATE_OWNER_FORM;
        } else {
            OwnerForm savedOwner = ownerService.saveOwnerForm(ownerForm);
            return "redirect:/owners/" + savedOwner.getId();
        }
    }

    @GetMapping("/{id}/edit")
    public String initUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("owner", ownerService.findOwnerFormById(id));
        return VIEW_OWNERS_CREATE_OR_UPDATE_OWNER_FORM;
    }

    @PostMapping("/{id}/edit")
    public String processUpdateForm(@Valid OwnerForm ownerForm, BindingResult result, @PathVariable Long id){
        if (result.hasErrors()) {
            return VIEW_OWNERS_CREATE_OR_UPDATE_OWNER_FORM;
        } else {
            ownerForm.setId(id);
            OwnerForm savedOwnerForm = ownerService.saveOwnerForm(ownerForm);
            return "redirect:/owners/" + savedOwnerForm.getId();
        }
    }
}
