package guru.springframework.sfgpetclinic.converters;

import guru.springframework.sfgpetclinic.forms.PetForm;
import guru.springframework.sfgpetclinic.model.Pet;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class PetToPetForm implements Converter<Pet, PetForm> {

    private final PetTypeToPetTypeForm petTypeConverter;

    public PetToPetForm(PetTypeToPetTypeForm petTypeConverter) {
        this.petTypeConverter = petTypeConverter;
    }

    @Synchronized // Converter intfc: Implementations of this interface are thread-safe and can be shared. (?)
    // EXERCISE Is this some Spring magic, so we don't need @Synchronized
    @Nullable
    @Override
    public PetForm convert(Pet source) {

        if (source == null) {
            return null;
        }

        final PetForm form = new PetForm();
        form.setId(source.getId());
        form.setName(source.getName());
        form.setPetType(petTypeConverter.convert(source.getPetType()));
        if (source.getOwner() != null) {
            form.setOwnerId(source.getOwner().getId());
        }
        form.setBirthDate(source.getBirthDate());

        if (source.getVisits() != null){
            source.getVisits()
                    .forEach(visit -> form.getVisitIds().add(visit.getId()));
        }

        return form;
    }
}
