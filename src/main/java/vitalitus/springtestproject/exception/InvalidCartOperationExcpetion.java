package vitalitus.springtestproject.exception;

public class InvalidCartOperationExcpetion extends RuntimeException {
    public InvalidCartOperationExcpetion(String message) {
        super(message);
    }
}
