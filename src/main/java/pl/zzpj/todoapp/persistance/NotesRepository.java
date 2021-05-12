package pl.zzpj.todoapp.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zzpj.todoapp.persistance.entities.NoteEntity;

@Repository
public interface NotesRepository extends JpaRepository<NoteEntity, Long> {
}
