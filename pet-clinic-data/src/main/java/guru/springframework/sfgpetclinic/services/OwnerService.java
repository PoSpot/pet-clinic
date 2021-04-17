package guru.springframework.sfgpetclinic.services;

import guru.springframework.sfgpetclinic.forms.OwnerForm;
import guru.springframework.sfgpetclinic.model.Owner;

import java.util.List;

public interface OwnerService extends CrudService<Owner, Long> {

    List<Owner> findByLastNameLike(String lastName);

    OwnerForm saveOwnerForm(OwnerForm owner);

    OwnerForm findOwnerFormById(Long l);
}
