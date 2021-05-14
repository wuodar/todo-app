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
@Table(name = "TaskTypes")
@EqualsAndHashCode
public class TaskTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String name;
    private int rgb;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "task_type_id")
    private Set<NoteEntity> noteEntities = new HashSet<>();
}
