package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.BaseEntity;
import guru.springframework.sfgpetclinic.services.CrudService;

import java.util.*;

public abstract class BaseMapService<T extends BaseEntity> implements CrudService<T, Long> {

    protected Map<Long, T> map = new HashMap<>();

    public Set<T> findAll() {
        return new HashSet<>(map.values());
    }

    public T findById(Long id) {
        return map.get(id);
    }

    public T save(T t) {
        if (t.getId() == null) {
            t.setId(getNextId());
        }
        return map.put(t.getId(), t);
    }

    public void deleteById(Long id) {
        map.remove(id);
    }

    public void delete(T t) {
        map.values().removeIf(t::equals);
    }

    private Long getNextId() {

        if (map.isEmpty()) return 1L;
        return Collections.max(map.keySet()) + 1;
    }
}
