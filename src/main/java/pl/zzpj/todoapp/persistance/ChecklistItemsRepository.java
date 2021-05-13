package pl.zzpj.todoapp.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zzpj.todoapp.persistance.entities.ChecklistItemEntity;

@Repository
public interface ChecklistItemsRepository extends JpaRepository<ChecklistItemEntity, Long> {
}
