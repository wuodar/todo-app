package pl.zzpj.todoapp.persistance.entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ChecklistItems")
@EqualsAndHashCode(exclude = "note")
public class ChecklistItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String text;
    private boolean isDone = false;

    @ManyToOne
    @JoinColumn(name = "note_id")
    private NoteEntity note;
}
