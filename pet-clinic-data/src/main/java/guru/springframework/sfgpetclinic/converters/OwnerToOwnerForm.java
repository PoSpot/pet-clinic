package guru.springframework.sfgpetclinic.converters;

import guru.springframework.sfgpetclinic.forms.OwnerForm;
import guru.springframework.sfgpetclinic.model.Owner;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class OwnerToOwnerForm implements Converter<Owner, OwnerForm> {

    private final PetToPetForm petConverter;

    public OwnerToOwnerForm(PetToPetForm petConverter) {
        this.petConverter = petConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public OwnerForm convert(Owner source) {

        if (source == null) {
            return null;
        }

        final OwnerForm form = new OwnerForm();
        form.setId(source.getId());
        form.setFirstName(source.getFirstName());
        form.setLastName(source.getLastName());
        form.setAddress(source.getAddress());
        form.setCity(source.getCity());
        form.setTelephone(source.getTelephone());

        if (source.getPets() != null){
            source.getPets()
                    .forEach(pet -> form.getPets().add(petConverter.convert(pet)));
        }

        return form;
    }
}
