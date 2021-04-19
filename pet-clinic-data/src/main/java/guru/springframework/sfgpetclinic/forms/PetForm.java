package guru.springframework.sfgpetclinic.forms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PetForm {

    private Long id;
    private String name;
    private PetTypeForm petType;
    // This bidir. dep-cy would introduce circ. dep-cy => id here
    private Long ownerId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    // This bidir. dep-cy would introduce circ. dep-cy => ids here
    private Set<Long> visitIds = new HashSet<>();

    public boolean isNew() {
        return this.id == null;
    }
}
