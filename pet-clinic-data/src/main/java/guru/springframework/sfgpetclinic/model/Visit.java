package guru.springframework.sfgpetclinic.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Visit extends BaseEntity {

    private LocalDate date;
    private String description;

    @ManyToOne
// KEEPINMIND    @JoinColumn(name = "pet_id") Same - I believe it's not needed
    private Pet pet;

    @Builder
    public Visit(Long id, LocalDate date, String description, Pet pet) {
        super(id);
        this.date = date;
        this.description = description;
        this.pet = pet;
    }
}
