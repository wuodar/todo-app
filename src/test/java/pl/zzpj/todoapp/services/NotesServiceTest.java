package pl.zzpj.todoapp.services;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pl.zzpj.todoapp.TodoAppApplication;
import pl.zzpj.todoapp.errors.NoteNotFoundException;
import pl.zzpj.todoapp.persistance.NotesRepository;
import pl.zzpj.todoapp.persistance.entities.NoteEntity;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TodoAppApplication.class})
@TestPropertySource(locations = "classpath:application-integration.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class NotesServiceTest {

    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private NotesService notesService;

    @Before
    public void setUp() {
        notesRepository.deleteAllInBatch();
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenNoResult() {
        //given

        //when
        Throwable result = Assertions.catchThrowable(() -> notesService.getNote(1));

        //then
        assertThat(result).isInstanceOf(NoteNotFoundException.class);
        assertThat(result.getMessage()).isEqualTo("Note with id '1' not found.");
    }

    @Test
    public void shouldSaveNote() {
        //given
        NoteEntity note = new NoteEntity(1, "Note 1", "something to do", LocalDateTime.of(2021, 6, 1, 13, 30).toEpochSecond(ZoneOffset.UTC));

        //when
        notesRepository.save(note);

        //then
        assertThat(notesRepository.findById(1L).isPresent()).isTrue();
        assertThat(notesRepository.findById(1L).get()).isEqualTo(note);
    }

    @Test
    public void shouldAddNote() {
        //given
        NoteEntity note = new NoteEntity(1, "Note 1", "something to do", LocalDateTime.of(2021, 6, 1, 13, 30).toEpochSecond(ZoneOffset.UTC));

        //when
        NoteEntity addedNote = notesService.addNote(note);

        //then
        assertThat(note).isEqualTo(addedNote);
        assertThat(notesRepository.findById(1L).isPresent()).isTrue();
        assertThat(notesRepository.findById(1L).get()).isEqualTo(note);
    }

    @Test
    public void shouldGetNote() {
        //given
        NoteEntity note = new NoteEntity(1, "Note 1", "something to do", LocalDateTime.of(2021, 6, 1, 13, 30).toEpochSecond(ZoneOffset.UTC));
        notesRepository.save(note);

        //when
        NoteEntity noteFromRepo = notesService.getNote(1L);

        //then
        assertThat(noteFromRepo).isEqualTo(note);
    }

    @Test
    public void shouldUpdateNote() {
        //given
        NoteEntity note = new NoteEntity(1, "Note 1", "something to do", LocalDateTime.of(2021, 6, 1, 13, 30).toEpochSecond(ZoneOffset.UTC));
        NoteEntity note2 = new NoteEntity(1, "Note 1a", "something even more to do", LocalDateTime.of(2021, 6, 10, 13, 30).toEpochSecond(ZoneOffset.UTC));
        notesRepository.save(note);

        //when
        NoteEntity updatedNote = notesService.updateNote(note2);
        Optional<NoteEntity> noteFromRepo = notesRepository.findById(1L);

        //then
        assertThat(note2).isEqualTo(updatedNote);
        assertThat(noteFromRepo.isPresent()).isTrue();
        assertThat(noteFromRepo.get()).isEqualTo(note2);
    }
}