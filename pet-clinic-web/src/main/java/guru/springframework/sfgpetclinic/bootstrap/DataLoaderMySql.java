package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import guru.springframework.sfgpetclinic.services.SpecialityService;
import guru.springframework.sfgpetclinic.services.VetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile({"dev", "prod"})
public class DataLoaderMySql implements CommandLineRunner {

    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;
    private final VetService vetService;

    public DataLoaderMySql(PetTypeService petTypeService,
                           SpecialityService specialityService, VetService vetService) {
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
    }

    @Override
    public void run(String... args) {

        if (petTypeService.findAll().size() == 0){
            log.debug("Loading Pet Types");
            loadPetTypes();
        }

        if (specialityService.findAll().size() == 0){
            log.debug("Loading Specialities");
            loadSpecialities();
        }

        if (vetService.findAll().size() == 0){
            log.debug("Loading Vets");
            loadVets();
        }
    }

    private void loadPetTypes() {

        PetType dogType = new PetType();
        dogType.setName("dog");
        // !! no need to get the id from the saved entity (same below, catType)
        petTypeService.save(dogType);

        PetType catType = new PetType();
        catType.setName("cat");
        petTypeService.save(catType);

        log.info("Pet Types loaded....");
    }

    private void loadSpecialities() {

        Speciality radiology = new Speciality();
        radiology.setDescription("Radiology");
        Speciality savedRadiology = specialityService.save(radiology);

        Speciality surgery = new Speciality();
        surgery.setDescription("Surgery");
        Speciality savedSurgery = specialityService.save(surgery);

        Speciality dentistry = new Speciality();
        dentistry.setDescription("Dentistry");
        Speciality savedDentistry = specialityService.save(dentistry);

        log.info("Specialities loaded....");
    }

    private void loadVets() {

        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");
        vet1.getSpecialities().add(specialityService.findByDescription("Radiology"));
        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Jessie");
        vet2.setLastName("Porter");
        vet2.getSpecialities().add(specialityService.findByDescription("Surgery"));
        vet2.getSpecialities().add(specialityService.findByDescription("Dentistry"));
        vetService.save(vet2);

        Vet vet3 = new Vet();
        vet3.setFirstName("Po");
        vet3.setLastName("Pa");
        vetService.save(vet3);

        log.info("Vets loaded....");
    }
}
