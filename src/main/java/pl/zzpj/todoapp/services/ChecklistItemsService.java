package pl.zzpj.todoapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zzpj.todoapp.errors.ChecklistItemNotFoundException;
import pl.zzpj.todoapp.persistance.ChecklistItemsRepository;
import pl.zzpj.todoapp.persistance.entities.ChecklistItemEntity;

@Service
public class ChecklistItemsService {

    public static final String CHECKLIST_ITEM_NOT_FOUND_MSG = "Checklist item with id '%s' not found.";

    private ChecklistItemsRepository checklistItemsRepository;

    @Autowired
    public ChecklistItemsService(ChecklistItemsRepository checklistItemsRepository) {
        this.checklistItemsRepository = checklistItemsRepository;
    }

    public ChecklistItemEntity getChecklistItem(long id) {
        return checklistItemsRepository
                .findById(id)
                .orElseThrow(() -> new ChecklistItemNotFoundException(String.format(CHECKLIST_ITEM_NOT_FOUND_MSG, id)));
    }

    public ChecklistItemEntity addChecklistItem(ChecklistItemEntity checklistItemEntity) {
        ChecklistItemEntity added = checklistItemsRepository.save(checklistItemEntity);
        return added;
    }

    public ChecklistItemEntity updateChecklistItem(ChecklistItemEntity checklistItemEntity) {
        ChecklistItemEntity updated = checklistItemsRepository.save(checklistItemEntity);
        return updated;
    }
}
