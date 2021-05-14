package pl.zzpj.todoapp.errors;

public class TaskTypeException extends RuntimeException{
    public TaskTypeException(String message) {
        super(message);
    }
}
