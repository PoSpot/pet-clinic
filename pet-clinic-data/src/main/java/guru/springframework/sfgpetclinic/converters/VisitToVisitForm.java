package guru.springframework.sfgpetclinic.converters;

import guru.springframework.sfgpetclinic.forms.VisitForm;
import guru.springframework.sfgpetclinic.model.Visit;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class VisitToVisitForm implements Converter<Visit, VisitForm> {

    public final PetToPetForm petConverter;

    public VisitToVisitForm(PetToPetForm petConverter) {
        this.petConverter = petConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public VisitForm convert(Visit source) {

        if (source == null) {
            return null;
        }

        return VisitForm.builder()
                .id(source.getId())
                .date(source.getDate())
                .description(source.getDescription())
                .pet(petConverter.convert(source.getPet()))
                .build();
    }
}
