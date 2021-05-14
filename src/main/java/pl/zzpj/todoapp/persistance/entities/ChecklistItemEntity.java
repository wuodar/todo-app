package pl.zzpj.todoapp.persistance.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    @JoinColumn(name = "note_id", nullable = false)
    private NoteEntity note;
}
