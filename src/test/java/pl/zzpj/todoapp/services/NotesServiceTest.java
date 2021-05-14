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
import pl.zzpj.todoapp.TodoAppApplication;
import pl.zzpj.todoapp.errors.NoteNotFoundException;
import pl.zzpj.todoapp.persistance.ChecklistItemsRepository;
import pl.zzpj.todoapp.persistance.NotesRepository;

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
}