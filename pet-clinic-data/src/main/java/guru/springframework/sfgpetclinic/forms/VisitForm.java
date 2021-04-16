package guru.springframework.sfgpetclinic.forms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class VisitForm {

    private Long id;
    private LocalDate date;
    private String description;
    private PetForm pet;
}
