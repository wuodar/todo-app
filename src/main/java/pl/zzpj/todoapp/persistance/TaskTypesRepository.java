package pl.zzpj.todoapp.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zzpj.todoapp.persistance.entities.TaskTypeEntity;

@Repository
public interface TaskTypesRepository extends JpaRepository<TaskTypeEntity, Long> {
}
