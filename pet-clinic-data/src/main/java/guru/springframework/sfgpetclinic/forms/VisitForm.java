package guru.springframework.sfgpetclinic.forms;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitForm {

    private Long id;
    private LocalDate date;
    private String description;
    private PetForm pet;
}
