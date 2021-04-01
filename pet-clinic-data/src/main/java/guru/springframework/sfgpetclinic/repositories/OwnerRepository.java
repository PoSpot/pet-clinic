package guru.springframework.sfgpetclinic.repositories;

import guru.springframework.sfgpetclinic.model.Owner;

import java.util.Optional;

public interface OwnerRepository extends AbstractBaseRepository<Owner> {

    Optional<Owner> findByLastName(String lastName);
}
