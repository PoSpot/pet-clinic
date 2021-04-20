package guru.springframework.sfgpetclinic.forms;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class VisitForm {

    private Long id;
    private LocalDate date;
    private String description;
    private PetForm pet;
}
