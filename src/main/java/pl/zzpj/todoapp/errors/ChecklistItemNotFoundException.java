package pl.zzpj.todoapp.errors;

public class ChecklistItemNotFoundException extends RuntimeException{
    public ChecklistItemNotFoundException(String message) {
        super(message);
    }
}
