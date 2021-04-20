package guru.springframework.sfgpetclinic.converters;

import guru.springframework.sfgpetclinic.forms.OwnerForm;
import guru.springframework.sfgpetclinic.forms.PetForm;
import guru.springframework.sfgpetclinic.model.Owner;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

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

        Set<PetForm> pets = new HashSet<>();
        if (source.getPets() != null){
            source.getPets()
                    .forEach(pet -> pets.add(petConverter.convert(pet)));
        }

        return OwnerForm.builder().id(source.getId())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .address(source.getAddress())
                .city(source.getCity())
                .telephone(source.getTelephone())
                .pets(pets)
                .build();
    }
}
