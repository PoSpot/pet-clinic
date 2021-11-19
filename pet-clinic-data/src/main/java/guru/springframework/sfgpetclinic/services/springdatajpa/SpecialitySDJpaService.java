package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialityRepository;
import guru.springframework.sfgpetclinic.services.SpecialityService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"default", "dev", "prod"})
public class SpecialitySDJpaService extends BaseSDJpaService<Speciality, SpecialityRepository> implements SpecialityService {

    public SpecialitySDJpaService(SpecialityRepository specialityRepo) {
        super(specialityRepo);
    }

    public Speciality findByDescription(String description) {
        return crudRepo.findByDescription(description).orElse(null);
    }
}
