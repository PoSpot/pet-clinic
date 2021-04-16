package guru.springframework.sfgpetclinic.converters;

import guru.springframework.sfgpetclinic.forms.VisitForm;
import guru.springframework.sfgpetclinic.model.Visit;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class VisitFormToVisit implements Converter<VisitForm, Visit> {

    private final PetFormToPet petConverter;

    public VisitFormToVisit(PetFormToPet petConverter) {
        this.petConverter = petConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Visit convert(VisitForm source) {

        if (source == null) {
            return null;
        }

        return Visit.builder()
                .id(source.getId())
                .date(source.getDate())
                .description(source.getDescription())
                .pet(petConverter.convert(source.getPet()))
                .build();
    }
}
