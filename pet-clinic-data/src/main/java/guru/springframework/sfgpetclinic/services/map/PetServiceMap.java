package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import org.springframework.stereotype.Service;

@Service
public class PetServiceMap extends BaseMapService<Pet> implements PetService {

    private final PetTypeService petTypeService;

    public PetServiceMap(PetTypeService petTypeService) {
        this.petTypeService = petTypeService;
    }

    @Override
    public Pet save(Pet pet) {

        if (pet.getPetType() != null) {
            if (pet.getPetType().getId() == null){
                // !! no need to set the saved type
                petTypeService.save(pet.getPetType());
            }
        } else {
            throw new IllegalArgumentException("Pet Type is required on Pet entity");
        }



        return super.save(pet);
    }
}
