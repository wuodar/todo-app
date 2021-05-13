package pl.zzpj.todoapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        return notesRepository
                .findAll();
    }

    public NoteEntity addNote(NoteEntity noteEntity) {
        NoteEntity savedNote = notesRepository.save(noteEntity);
        return savedNote;
    }

    public NoteEntity updateNote(NoteEntity noteEntity) {
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
}
