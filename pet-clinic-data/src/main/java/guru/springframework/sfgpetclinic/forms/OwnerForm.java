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
}