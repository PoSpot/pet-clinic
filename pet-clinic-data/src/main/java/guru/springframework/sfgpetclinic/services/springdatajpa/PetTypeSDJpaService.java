package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"default", "dev", "prod"})
public class PetTypeSDJpaService extends BaseSDJpaService<PetType, PetTypeRepository> implements PetTypeService {

    public PetTypeSDJpaService(PetTypeRepository petTypeRepo) {
        super(petTypeRepo);
    }
}
