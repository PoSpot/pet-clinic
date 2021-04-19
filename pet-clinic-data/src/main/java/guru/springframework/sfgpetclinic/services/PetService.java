package guru.springframework.sfgpetclinic.services;

import guru.springframework.sfgpetclinic.forms.PetForm;
import guru.springframework.sfgpetclinic.model.Pet;

public interface PetService extends CrudService<Pet, Long> {

    PetForm findPetFormById(Long id);

    PetForm savePetForm(PetForm form);
}
