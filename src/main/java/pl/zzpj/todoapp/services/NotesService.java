package pl.zzpj.todoapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zzpj.todoapp.errors.NoteNotFoundException;
import pl.zzpj.todoapp.persistance.NotesRepository;
import pl.zzpj.todoapp.persistance.entities.NoteEntity;

import java.util.List;

@Service
public class NotesService {

    public static final String NOTE_NOT_FOUND_MSG = "Note with id '%s' not found.";

    private NotesRepository notesRepository;

    @Autowired
    public NotesService(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
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
}
