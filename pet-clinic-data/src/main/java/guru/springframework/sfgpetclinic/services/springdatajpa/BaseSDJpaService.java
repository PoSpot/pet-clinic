package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.BaseEntity;
import guru.springframework.sfgpetclinic.repositories.AbstractBaseRepository;
import guru.springframework.sfgpetclinic.services.CrudService;

import java.util.HashSet;
import java.util.Set;

//@Service  You should not have @Component on top of an abstract class (abstr-> not to be instantiated) TODO
public abstract class BaseSDJpaService<T extends BaseEntity> implements CrudService<T, Long> {

    protected final AbstractBaseRepository<T> baseRepo;

    protected BaseSDJpaService(AbstractBaseRepository<T> baseRepo) {
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

        System.out.println("******************************");
        System.out.println("******************************");
        System.out.println("******************************");
        System.out.println("******************************");
        System.out.println("******************************");
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
