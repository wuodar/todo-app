package pl.zzpj.todoapp.persistance.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Notes")
@EqualsAndHashCode
public class NoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String title;
    private String description;
    private long dueTime;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "note")
    private Set<ChecklistItemEntity> checklistItems = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "task_type_id", nullable = false)
    private TaskTypeEntity taskType;
}
