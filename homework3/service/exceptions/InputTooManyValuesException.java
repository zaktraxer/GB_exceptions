package homework3.service.exceptions;

import java.io.IOException;

public class InputTooManyValuesException extends IOException {
    public InputTooManyValuesException() {
        super("Введено слишком много значений.");
    }
}