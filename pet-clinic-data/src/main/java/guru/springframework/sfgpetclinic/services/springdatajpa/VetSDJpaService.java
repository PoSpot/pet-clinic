package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.repositories.VetRepository;
import guru.springframework.sfgpetclinic.services.VetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"default", "dev", "prod"})
public class VetSDJpaService extends BaseSDJpaService<Vet, VetRepository> implements VetService {

    public VetSDJpaService(VetRepository vetRepo) {
        super(vetRepo);
    }
}
