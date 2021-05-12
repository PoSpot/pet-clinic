package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.converters.OwnerFormToOwner;
import guru.springframework.sfgpetclinic.converters.OwnerToOwnerForm;
import guru.springframework.sfgpetclinic.forms.OwnerForm;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.services.OwnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Profile({"default", "dev", "prod"})
public class OwnerSDJpaService extends BaseSDJpaService<Owner> implements OwnerService {


    private final OwnerFormToOwner ownerFormToOwner;
    private final OwnerToOwnerForm ownerToOwnerForm;

    // the example has a repo field here? KIM (+ all other services)
    public OwnerSDJpaService(OwnerRepository ownerRepo, OwnerFormToOwner ownerFormToOwner, OwnerToOwnerForm ownerToOwnerForm) {
        super(ownerRepo);
        this.ownerFormToOwner = ownerFormToOwner;
        this.ownerToOwnerForm = ownerToOwnerForm;
        log.info("Creating Owner SD JPA Service");
    }

    @Override
    public List<Owner> findByLastNameLike(String lastName) {
        return ((OwnerRepository) crudRepo).findByLastNameLike("%" + lastName + "%");
    }

    @Transactional
    @Override
    public OwnerForm saveOwnerForm(OwnerForm form) {
        Owner detachedOwner = ownerFormToOwner.convert(form);

        Owner savedOwner = crudRepo.save(detachedOwner);
        log.debug("Saved OwnerId:" + savedOwner.getId());
        return ownerToOwnerForm.convert(savedOwner);
    }

    @Transactional
    @Override
    public OwnerForm findOwnerFormById(Long l) {
        return ownerToOwnerForm.convert(findById(l));
    }
}
