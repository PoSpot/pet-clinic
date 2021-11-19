package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.converters.VisitFormToVisit;
import guru.springframework.sfgpetclinic.converters.VisitToVisitForm;
import guru.springframework.sfgpetclinic.forms.VisitForm;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import guru.springframework.sfgpetclinic.services.VisitService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"default", "dev", "prod"})
public class VisitSDJpaService extends BaseSDJpaService<Visit, VisitRepository> implements VisitService {

    private final VisitFormToVisit visitFormToVisit;
    private final VisitToVisitForm visitToVisitForm;

    public VisitSDJpaService(VisitRepository visitRepo, VisitFormToVisit visitFormToVisit, VisitToVisitForm visitToVisitForm) {
        super(visitRepo);
        this.visitFormToVisit = visitFormToVisit;
        this.visitToVisitForm = visitToVisitForm;
    }

    @Override
    public VisitForm saveVisitForm(VisitForm visitForm) {
        return visitToVisitForm.convert(crudRepo.save(visitFormToVisit.convert(visitForm)));
    }
}
