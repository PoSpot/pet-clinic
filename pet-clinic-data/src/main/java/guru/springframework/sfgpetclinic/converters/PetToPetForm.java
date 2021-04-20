package guru.springframework.sfgpetclinic.converters;

import guru.springframework.sfgpetclinic.forms.PetForm;
import guru.springframework.sfgpetclinic.model.Pet;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class PetToPetForm implements Converter<Pet, PetForm> {

    private final PetTypeToPetTypeForm petTypeConverter;

    public PetToPetForm(PetTypeToPetTypeForm petTypeConverter) {
        this.petTypeConverter = petTypeConverter;
    }

    @Synchronized // Converter intfc: Implementations of this interface are thread-safe and can be shared. (?)
    // EXERCISE Is this some Spring magic, so we don't need @Synchronized?
    @Nullable
    @Override
    public PetForm convert(Pet source) {

        if (source == null) {
            return null;
        }

        final Set<Long> visits = new HashSet<>();
        if (source.getVisits() != null){
            source.getVisits()
                    .forEach(visit -> visits.add(visit.getId()));
        }
        final PetForm form = PetForm.builder().id(source.getId())
                .name(source.getName())
                .petType(source.getPetType())
                .birthDate(source.getBirthDate())
                .visitIds(visits)
                .build();

        if (source.getOwner() != null) {
            form.setOwnerId(source.getOwner().getId());
        }

        return form;
    }
}
