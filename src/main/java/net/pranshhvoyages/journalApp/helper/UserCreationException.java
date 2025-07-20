package net.pranshhvoyages.journalApp.helper;

public class UserCreationException extends RuntimeException{

    public UserCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserCreationException(String message) {
        super(message);
    }
}
