package guru.springframework.sfgpetclinic.converters;

import guru.springframework.sfgpetclinic.forms.PetForm;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class PetFormToPet implements Converter<PetForm, Pet> {

    private final PetTypeFormToPetType petTypeConverter;

    public PetFormToPet(PetTypeFormToPetType petTypeConverter) {
        this.petTypeConverter = petTypeConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Pet convert(PetForm source) {

        if (source == null) {
            return null;
        }

        final Set<Visit> visits = new HashSet<>();
        if (source.getVisitIds() != null){
            source.getVisitIds()
                    // Visits with id only, seems to me that it's enough. Let's see. Below the opposite dir-n is also set:
                    // visit.setPet(pet), but seems unnecessary.
                    .forEach(visitId -> visits.add(Visit.builder().id(visitId).build()));
        }

        final Pet pet = Pet.builder().id(source.getId())
                .name(source.getName())
                .petType(petTypeConverter.convert(source.getPetType()))
                .birthDate(source.getBirthDate())
                .visits(visits)
                .build();

        // KIM Kind of shady, think about it dep-ing on usage
        // Should be fine, cos: Ex.: saving pet -> need owner id(u have it) -> find owner in db -> set pet -> save
        if(source.getOwnerId() != null){
            Owner owner = Owner.builder().id(source.getOwnerId()).build();
            // pet.setOwner(owner); unnecessary cos it's set in owner.addPet()
            owner.addPet(pet); // does the owner really need this pet..? (If removed, uncomment the above line)
        }

        return pet;
    }
}