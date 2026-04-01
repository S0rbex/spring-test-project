package vitalitus.springtestproject.exception;

public class LoginException extends RuntimeException {
    public LoginException(String message) {
        super(message);
    }
}
