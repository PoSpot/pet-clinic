package guru.springframework.sfgpetclinic.converters;

import guru.springframework.sfgpetclinic.forms.VisitForm;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class VisitFormToVisit implements Converter<VisitForm, Visit> {

    @Synchronized
    @Nullable
    @Override
    public Visit convert(VisitForm source) {

        if (source == null) {
            return null;
        }

        Pet pet = null;
        if (source.getPetId() != null) {
            pet = Pet.builder().id(source.getPetId()).build();
        }

        return Visit.builder()
                .id(source.getId())
                .date(source.getDate())
                .description(source.getDescription())
                .pet(pet)
                .build();
    }
}
