package pl.zzpj.todoapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zzpj.todoapp.errors.TaskTypeException;
import pl.zzpj.todoapp.persistance.NotesRepository;
import pl.zzpj.todoapp.persistance.TaskTypesRepository;
import pl.zzpj.todoapp.persistance.entities.TaskTypeEntity;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TaskTypesService {

    public static final String TASK_TYPE_NOT_FOUND_MSG = "Task type with id '%s' not found.";
    public static final String TASK_TYPE_IS_BEING_USED_MSG = "Task type with id '%s' is being used by some notes and it can't be removed.";

    private final TaskTypesRepository taskTypesRepository;
    private final NotesRepository notesRepository;

    @Autowired
    public TaskTypesService(TaskTypesRepository taskTypesRepository, NotesRepository notesRepository) {
        this.taskTypesRepository = taskTypesRepository;
        this.notesRepository = notesRepository;
    }

    public TaskTypeEntity getTaskType(long id) {
        TaskTypeEntity taskTypeEntity = taskTypesRepository.findById(id)
                .orElseThrow(() -> new TaskTypeException(String.format(TASK_TYPE_NOT_FOUND_MSG, id)));

        return taskTypeEntity;
    }

    public List<TaskTypeEntity> getAllTaskTypes() {
        return taskTypesRepository.findAll();
    }

    public TaskTypeEntity addTaskType(TaskTypeEntity taskTypeEntity) {
        TaskTypeEntity savedTaskType = taskTypesRepository.save(taskTypeEntity);
        return savedTaskType;
    }

    @Transactional
    public TaskTypeEntity updateTaskType(TaskTypeEntity taskTypeEntity) {
        long id = taskTypeEntity.getId();
        taskTypesRepository.findById(id)
                .orElseThrow(() -> new TaskTypeException(String.format(TASK_TYPE_NOT_FOUND_MSG, id)));
        TaskTypeEntity updatedTaskType = taskTypesRepository.save(taskTypeEntity);

        return updatedTaskType;
    }

    @Transactional
    public TaskTypeEntity removeTaskType(long id) {
        TaskTypeEntity taskTypeEntity = taskTypesRepository.findById(id)
                .orElseThrow(() -> new TaskTypeException(String.format(TASK_TYPE_NOT_FOUND_MSG, id)));
        if(!taskTypeEntity.getNoteEntities().isEmpty())
            throw new TaskTypeException(String.format(TASK_TYPE_IS_BEING_USED_MSG, id));

        taskTypesRepository.delete(taskTypeEntity);

        return taskTypeEntity;
    }
}
