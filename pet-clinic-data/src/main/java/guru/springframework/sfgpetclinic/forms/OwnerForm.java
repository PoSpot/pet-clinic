package guru.springframework.sfgpetclinic.forms;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OwnerForm {

    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String telephone;
    @Builder.Default // default init in builder
    private Set<PetForm> pets = new HashSet<>();

    public boolean isNew() {
        return this.id == null;
    }

    /**
     * Return the Pet with the given name, or null if none found for this Owner.
     *
     * @param name to test
     * @return Pet if pet name is already in use
     */
    public PetForm getPet(String name, boolean ignoreNew) {
        name = name.toLowerCase();
        for (PetForm pet : pets) {
            if (!ignoreNew || !pet.isNew()) {
                String compName = pet.getName();
                compName = compName.toLowerCase();
                if (compName.equals(name)) {
                    return pet;
                }
            }
        }
        return null;
    }
}