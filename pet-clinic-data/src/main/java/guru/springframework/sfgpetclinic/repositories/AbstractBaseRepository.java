package guru.springframework.sfgpetclinic.repositories;

import guru.springframework.sfgpetclinic.model.BaseEntity;
import org.springframework.data.repository.CrudRepository;

public interface AbstractBaseRepository<T extends BaseEntity> extends CrudRepository<T, Long> {
}
