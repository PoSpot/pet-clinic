package guru.springframework.sfgpetclinic.converters;

import guru.springframework.sfgpetclinic.forms.OwnerForm;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class OwnerFormToOwner implements Converter<OwnerForm, Owner> {

    private final PetFormToPet petConverter;

    public OwnerFormToOwner(PetFormToPet petConverter) {
        this.petConverter = petConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Owner convert(OwnerForm source) {

        // Some bug, Intellij reads from Javadoc, not from method signature..source can obviously be null (see test).
        if (source == null) {
            return null;
        }

        final Set<Pet> pets = new HashSet<>();
        if (source.getPets() != null){
            source.getPets()
                    .forEach( petForm -> pets.add(petConverter.convert(petForm)));
        }

        return Owner.builder().id(source.getId())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .address(source.getAddress())
                .city(source.getCity())
                .telephone(source.getTelephone())
                .pets(pets)
                .build();
    }
}
