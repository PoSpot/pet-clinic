package guru.springframework.sfgpetclinic.forms;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class PetTypeForm {

    private Long id;
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
