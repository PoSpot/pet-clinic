package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.services.SpecialityService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("map")
public class SpecialityMapService extends BaseMapService<Speciality> implements SpecialityService {

    @Override
    public Speciality findByDescription(String description) {
        throw new RuntimeException("Not implemented yet!");
    }
}
