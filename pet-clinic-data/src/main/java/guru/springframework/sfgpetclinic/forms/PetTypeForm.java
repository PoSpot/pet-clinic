package guru.springframework.sfgpetclinic.forms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PetTypeForm {

    private Long id;
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
