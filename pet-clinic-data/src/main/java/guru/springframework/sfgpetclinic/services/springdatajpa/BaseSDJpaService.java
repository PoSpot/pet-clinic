package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.BaseEntity;
import guru.springframework.sfgpetclinic.services.CrudService;
import org.springframework.data.repository.CrudRepository;

import java.util.HashSet;
import java.util.Set;

//@Service  You should not have @Component on top of an abstract class (abstr-> not to be instantiated) KEEPINMIND
public abstract class BaseSDJpaService<T extends BaseEntity> implements CrudService<T, Long> {

    protected final CrudRepository<T, Long> crudRepo;

    protected BaseSDJpaService(CrudRepository<T, Long> crudRepo) {
        this.crudRepo = crudRepo;
    }

    @Override
    public Set<T> findAll() {
        Set<T> set = new HashSet<>();
        crudRepo.findAll().forEach(set::add);
        return set;
    }

    @Override
    public T findById(Long aLong) {

        return crudRepo.findById(aLong).orElse(null);
    }

    @Override
    public T save(T t) {

        return crudRepo.save(t);
    }

    @Override
    public void deleteById(Long aLong) {
        crudRepo.deleteById(aLong);
    }

    @Override
    public void delete(T t) {
        crudRepo.delete(t);
    }
}
