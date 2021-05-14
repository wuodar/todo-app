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
import pl.zzpj.todoapp.errors.ChecklistItemNotFoundException;
import pl.zzpj.todoapp.persistance.ChecklistItemsRepository;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TodoAppApplication.class})
@TestPropertySource(locations = "classpath:application-integration.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ChecklistItemServiceTest {

    @Autowired
    private ChecklistItemsRepository checklistItemsRepository;

    @Autowired
    private ChecklistItemsService checklistItemsService;

    @Before
    public void setUp() {
        checklistItemsRepository.deleteAllInBatch();
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenNoResult() {
        //given

        //when
        Throwable result = Assertions.catchThrowable(() -> checklistItemsService.getChecklistItem(1));

        //then
        assertThat(result).isInstanceOf(ChecklistItemNotFoundException.class);
        assertThat(result.getMessage()).isEqualTo("Checklist item with id '1' not found.");
    }
}


