package guru.springframework.sfgpetclinic.forms;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetTypeForm {

    private Long id;
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
