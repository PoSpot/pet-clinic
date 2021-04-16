package guru.springframework.sfgpetclinic.forms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class OwnerForm {

    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String telephone;
    private Set<PetForm> pets = new HashSet<>();

    public boolean isNew() {
        return this.id == null;
    }
}