package guru.springframework.sfgpetclinic.services;

import guru.springframework.sfgpetclinic.forms.VisitForm;
import guru.springframework.sfgpetclinic.model.Visit;

public interface VisitService extends CrudService<Visit, Long>{
    VisitForm saveVisitForm(VisitForm visitForm);
}
