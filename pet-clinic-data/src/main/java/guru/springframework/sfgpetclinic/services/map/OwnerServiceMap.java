package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import org.springframework.stereotype.Service;

@Service
public class OwnerServiceMap extends BaseMapService<Owner> implements OwnerService {

    private final PetService petService;

    public OwnerServiceMap(PetService petService) {
        this.petService = petService;
    }

    @Override
    public Owner findByLastName(String name) {
        return map.values().stream()
                .filter(value -> value.getLastName().equals(name))
                .findFirst()
                .get();
    }

    @Override
    public Owner save(Owner owner) {

        if(owner != null){

            if (owner.getPets() != null){

                owner.getPets().forEach(pet -> {

                    if (pet.getId() == null){
                        // !! no need to get the id from the saved entity
                        // !! also saving of petType distributed to PetServiceMap.save
                        petService.save(pet);
                    }
                        }

                );
            }

            return super.save(owner);

        } else {
            return null;
        }
    }
}
