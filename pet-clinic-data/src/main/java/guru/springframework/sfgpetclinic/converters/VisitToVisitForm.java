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

        final VisitForm form = new VisitForm();
        form.setId(source.getId());
        form.setDate(source.getDate());
        form.setDescription(source.getDescription());
        form.setPet(petConverter.convert(source.getPet()));

        return form;
    }
}
