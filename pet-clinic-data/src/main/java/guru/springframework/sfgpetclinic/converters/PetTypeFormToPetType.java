package guru.springframework.sfgpetclinic.converters;

import guru.springframework.sfgpetclinic.forms.PetTypeForm;
import guru.springframework.sfgpetclinic.model.PetType;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class PetTypeFormToPetType implements Converter<PetTypeForm, PetType> {

    @Synchronized
    @Nullable
    @Override
    public PetType convert(PetTypeForm source) {

        if (source == null) {
            return null;
        }

        return PetType.builder()
                .id(source.getId())
                .name(source.getName())
                .build();
    }
}
