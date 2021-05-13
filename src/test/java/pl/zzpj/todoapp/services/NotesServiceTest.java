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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import pl.zzpj.todoapp.TodoAppApplication;
import pl.zzpj.todoapp.errors.NoteNotFoundException;
import pl.zzpj.todoapp.persistance.ChecklistItemsRepository;
import pl.zzpj.todoapp.persistance.NotesRepository;
import pl.zzpj.todoapp.persistance.entities.ChecklistItemEntity;
import pl.zzpj.todoapp.persistance.entities.NoteEntity;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TodoAppApplication.class})
@TestPropertySource(locations = "classpath:application-integration.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class NotesServiceTest {

    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private ChecklistItemsRepository checklistItemsRepository;

    @Autowired
    private NotesService notesService;

    @Before
    public void setUp() {
        notesRepository.deleteAllInBatch();
        checklistItemsRepository.deleteAllInBatch();
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
    public void shouldAddNote() {
        //given
        long time = LocalDateTime.of(2021, 6, 1, 13, 30).toEpochSecond(ZoneOffset.UTC);
        NoteEntity note = new NoteEntity(1, "Note 1", "something to do", time, new HashSet<>());

        //when
        notesService.addNote(note);

        //then
        Optional<NoteEntity> noteFromRepo = notesRepository.findById(1L);
        assertThat(noteFromRepo.isPresent()).isTrue();
    }

    @Test
    public void shouldAddChecklistItemToNoteChecklist() {
        //given
        ChecklistItemEntity checklistItem1 = new ChecklistItemEntity(1, "task 1", false, null);
        ChecklistItemEntity checklistItem2 = new ChecklistItemEntity(2, "task 2", false, null);

        long time = LocalDateTime.of(2021, 6, 1, 13, 30).toEpochSecond(ZoneOffset.UTC);
        NoteEntity note = new NoteEntity(1, "Note 1", "something to do", time, new HashSet<>());
        notesService.addNote(note);

        //when
        notesService.addChecklistItemsToNote(Set.of(checklistItem1, checklistItem2), 1);

        //then
        assertThat(checklistItemsRepository.count()).isEqualTo(2);

        Optional<NoteEntity> noteFromRepo = notesRepository.findById(1L);
        assertThat(noteFromRepo.isPresent()).isTrue();
    }

    @Test
    public void shouldGetNote() {
        //given
        long time = LocalDateTime.of(2021, 6, 1, 13, 30).toEpochSecond(ZoneOffset.UTC);
        NoteEntity note = new NoteEntity(1, "Note 1", "something to do", time, null);
        notesRepository.save(note);

        //when
        Throwable result = Assertions.catchThrowable(() -> notesService.getNote(1));

        //then
        assertThat(result).isNull();
    }

    @Test
    public void shouldUpdateNote() {
        //given
        long time1 = LocalDateTime.of(2021, 6, 1, 13, 30).toEpochSecond(ZoneOffset.UTC);
        long time2 = LocalDateTime.of(2021, 6, 10, 13, 30).toEpochSecond(ZoneOffset.UTC);
        NoteEntity note = new NoteEntity(1, "Note 1", "something to do", time1, null);
        NoteEntity note2 = new NoteEntity(1, "Note 1a", "something even more to do", time2, null);
        notesRepository.save(note);

        //when
        notesService.updateNote(note2);

        //then
        Optional<NoteEntity> noteFromRepo = notesRepository.findById(1L);
        assertThat(noteFromRepo.isPresent()).isTrue();
        assertThat(noteFromRepo.get().getTitle()).isEqualTo("Note 1a");
    }

    @Test
    public void shouldRemoveNote() {
        //given
        long time = LocalDateTime.of(2021, 6, 1, 13, 30).toEpochSecond(ZoneOffset.UTC);
        NoteEntity note = new NoteEntity(1, "Note 1", "something to do", time, null);
        notesRepository.save(note);

        //when
        notesService.removeNote(1);

        //then
        Throwable result = Assertions.catchThrowable(() -> notesService.getNote(1));
        assertThat(result).isInstanceOf(NoteNotFoundException.class);
    }
}