package guru.springframework.sfgpetclinic.converters;

import guru.springframework.sfgpetclinic.forms.PetTypeForm;
import guru.springframework.sfgpetclinic.model.PetType;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class PetTypeToPetTypeForm implements Converter<PetType, PetTypeForm> {

    @Synchronized
    @Nullable
    @Override
    public PetTypeForm convert(PetType source) {

        if (source == null) {
            return null;
        }

        final PetTypeForm form = new PetTypeForm();
        form.setId(source.getId());
        form.setName(source.getName());

        return form;
    }
}
