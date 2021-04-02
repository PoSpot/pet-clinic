package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.services.OwnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Profile("springdatajpa")
public class OwnerSDJpaService extends BaseSDJpaService<Owner> implements OwnerService {

    // the example has a repo field here? TODO (+ all other services)
    public OwnerSDJpaService(OwnerRepository ownerRepo) {
        super(ownerRepo);
        log.info("Creating Owner SD JPA Service");
    }

    @Override
    public Owner findByLastName(String name) {
        return ((OwnerRepository) crudRepo).findByLastName(name).orElse(null);
    }
}
