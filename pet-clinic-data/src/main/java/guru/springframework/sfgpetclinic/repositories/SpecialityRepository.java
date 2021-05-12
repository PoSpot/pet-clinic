package guru.springframework.sfgpetclinic.repositories;

import guru.springframework.sfgpetclinic.model.Speciality;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SpecialityRepository extends CrudRepository<Speciality, Long> {

    Optional<Speciality> findByDescription(String description);
}
