package homework3.service.exceptions;

import java.io.IOException;

public class EmptyInputException extends IOException {
    public EmptyInputException() {
        super("Ввод не может быть пустым.");
    }
}