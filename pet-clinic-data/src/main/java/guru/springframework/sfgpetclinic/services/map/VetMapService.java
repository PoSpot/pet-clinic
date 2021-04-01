package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.SpecialityService;
import guru.springframework.sfgpetclinic.services.VetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"default", "map"})
public class VetMapService extends BaseMapService<Vet> implements VetService {

    private final SpecialityService specialityService;

    public VetMapService(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }

    @Override
    public Vet save(Vet vet) {

        if(vet != null){

            // Better have setSpecialities() check for nulls than here.
            if (vet.getSpecialities() != null){

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
