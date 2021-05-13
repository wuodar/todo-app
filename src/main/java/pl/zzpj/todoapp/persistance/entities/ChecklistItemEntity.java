package pl.zzpj.todoapp.persistance.entities;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ChecklistItems")
@EqualsAndHashCode
public class ChecklistItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String text;
    private boolean isDone = false;
}
