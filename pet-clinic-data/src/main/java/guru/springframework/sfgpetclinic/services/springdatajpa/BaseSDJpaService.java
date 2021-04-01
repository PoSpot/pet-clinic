package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.BaseEntity;
import guru.springframework.sfgpetclinic.services.CrudService;
import org.springframework.data.repository.CrudRepository;

import java.util.HashSet;
import java.util.Set;

//@Service  You should not have @Component on top of an abstract class (abstr-> not to be instantiated) TODO
public abstract class BaseSDJpaService<T extends BaseEntity> implements CrudService<T, Long> {

    protected final CrudRepository<T, Long> baseRepo;

    protected BaseSDJpaService(CrudRepository<T, Long> baseRepo) {
        this.baseRepo = baseRepo;
    }

    @Override
    public Set<T> findAll() {
        Set<T> set = new HashSet<>();
        baseRepo.findAll().forEach(set::add);
        return set;
    }

    @Override
    public T findById(Long aLong) {

        return baseRepo.findById(aLong).orElse(null);
    }

    @Override
    public T save(T t) {

        return baseRepo.save(t); // TODO need cast? (T)
    }

    @Override
    public void deleteById(Long aLong) {
        baseRepo.deleteById(aLong);
    }

    @Override
    public void delete(T t) {
        baseRepo.delete(t);
    }
}
