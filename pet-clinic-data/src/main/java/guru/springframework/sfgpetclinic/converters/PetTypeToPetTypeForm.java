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

        return PetTypeForm.builder()
                .id(source.getId())
                .name(source.getName())
                .build();
    }
}
