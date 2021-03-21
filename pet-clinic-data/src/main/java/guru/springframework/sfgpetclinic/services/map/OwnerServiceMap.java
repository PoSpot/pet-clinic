package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Service;

@Service
public class OwnerServiceMap extends BaseMapService<Owner> implements OwnerService {

    @Override
    public Owner findByLastName(String name) {
        return map.values().stream()
                .filter(value -> value.getLastName().equals(name))
                .findFirst()
                .get();
    }
}
