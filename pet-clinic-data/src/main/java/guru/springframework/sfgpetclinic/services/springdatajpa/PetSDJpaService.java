package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.converters.PetFormToPet;
import guru.springframework.sfgpetclinic.converters.PetToPetForm;
import guru.springframework.sfgpetclinic.forms.PetForm;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.services.PetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Profile("springdatajpa")
public class PetSDJpaService extends BaseSDJpaService<Pet> implements PetService {

    private final PetToPetForm petToPetForm;
    private final PetFormToPet petFormToPet;

    public PetSDJpaService(PetRepository petRepo, PetToPetForm petToPetForm, PetFormToPet petFormToPet) {
        super(petRepo);
        this.petToPetForm = petToPetForm;
        this.petFormToPet = petFormToPet;
    }

    @Transactional
    @Override
    public PetForm findPetFormById(Long id) {
        return petToPetForm.convert(findById(id));
    }

    @Transactional
    @Override
    public PetForm savePetForm(PetForm form) {// TODO BY THE WAY NO SERVICE CLASS TOUCHED IN SFG COMMITS
        return petToPetForm.convert(save(petFormToPet.convert(form)));
    }
}
