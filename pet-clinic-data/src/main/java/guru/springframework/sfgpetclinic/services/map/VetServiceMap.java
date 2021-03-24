package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.SpecialityService;
import guru.springframework.sfgpetclinic.services.VetService;
import org.springframework.stereotype.Service;

@Service
public class VetServiceMap extends BaseMapService<Vet> implements VetService {

    private final SpecialityService specialityService;

    public VetServiceMap(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }

    @Override
    public Vet save(Vet vet) {

        if(vet != null){

            if (!vet.getSpecialities().isEmpty()){

                vet.getSpecialities().forEach(speciality -> {

                            if (speciality.getId() == null){
                                // !! no need to get the id from the saved entity
                                specialityService.save(speciality);
                            }
                        }

                );
            }

            return super.save(vet);

        } else {
            return null;
        }
    }
}
