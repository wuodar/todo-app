package pl.zzpj.todoapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zzpj.todoapp.errors.ChecklistItemNotFoundException;
import pl.zzpj.todoapp.errors.NoteNotFoundException;
import pl.zzpj.todoapp.persistance.ChecklistItemsRepository;
import pl.zzpj.todoapp.persistance.NotesRepository;
import pl.zzpj.todoapp.persistance.entities.ChecklistItemEntity;
import pl.zzpj.todoapp.persistance.entities.NoteEntity;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotesService {

    public static final String NOTE_NOT_FOUND_MSG = "Note with id '%s' not found.";
    public static final String CHECKLIST_ITEM_NOT_FOUND_MSG = "Checklist item with id '%s' not found.";

    private final NotesRepository notesRepository;
    private final ChecklistItemsRepository checklistItemsRepository;

    @Autowired
    public NotesService(NotesRepository notesRepository, ChecklistItemsRepository checklistItemsRepository) {
        this.notesRepository = notesRepository;
        this.checklistItemsRepository = checklistItemsRepository;
    }

    public NoteEntity getNote(long id) {
        return notesRepository
                .findById(id)
                .orElseThrow(() -> new NoteNotFoundException(String.format(NOTE_NOT_FOUND_MSG, id)));
    }

    public List<NoteEntity> getAllNotes() {
        return notesRepository.findAll();
    }

    public NoteEntity addNote(NoteEntity noteEntity) {
        NoteEntity savedNote = notesRepository.save(noteEntity);
        return savedNote;
    }

    @Transactional
    public NoteEntity updateNote(NoteEntity noteEntity) {
        long id = noteEntity.getId();
        notesRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(String.format(NOTE_NOT_FOUND_MSG, id)));
        NoteEntity updatedNote = notesRepository.save(noteEntity);

        return updatedNote;
    }

    @Transactional
    public NoteEntity removeNote(long id) {
        NoteEntity noteEntity = notesRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(String.format(NOTE_NOT_FOUND_MSG, id)));
        notesRepository.delete(noteEntity);

        return noteEntity;
    }

    @Transactional
    public Set<ChecklistItemEntity> addChecklistItemsToNote(Set<ChecklistItemEntity> checklistItems, long id) {
        NoteEntity noteEntity = notesRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(String.format(NOTE_NOT_FOUND_MSG, id)));

        Set<ChecklistItemEntity> addedChecklistItems = new HashSet<>();
        for (ChecklistItemEntity checklistItem : checklistItems) {
            checklistItem.setNote(noteEntity);
            ChecklistItemEntity addedChecklistItem = checklistItemsRepository.save(checklistItem);
            addedChecklistItems.add(addedChecklistItem);
        }

        noteEntity.getChecklistItems().addAll(addedChecklistItems);
        notesRepository.save(noteEntity);

        return addedChecklistItems;
    }

    @Transactional
    public ChecklistItemEntity updateChecklistItem(ChecklistItemEntity checklistItemEntity) {
        long id = checklistItemEntity.getId();
        checklistItemsRepository.findById(id)
                .orElseThrow(() -> new ChecklistItemNotFoundException(String.format(CHECKLIST_ITEM_NOT_FOUND_MSG, id)));
        ChecklistItemEntity savedChecklistItem = checklistItemsRepository.save(checklistItemEntity);

        return savedChecklistItem;
    }

    @Transactional
    public ChecklistItemEntity removeChecklistItem(long id) {
        ChecklistItemEntity checklistItemEntity = checklistItemsRepository.findById(id)
                .orElseThrow(() -> new ChecklistItemNotFoundException(String.format(CHECKLIST_ITEM_NOT_FOUND_MSG, id)));
        checklistItemsRepository.delete(checklistItemEntity);

        return checklistItemEntity;
    }
}
