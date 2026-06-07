package io.kite.domain;

public class OperationResult {
    private final boolean success;
    private final ErrorCode errorCode;
    private final String message;

    private OperationResult(boolean success, ErrorCode errorCode, String message) {
        if (errorCode == null) {
            throw new IllegalArgumentException("Error code must not be null");
        }
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Message must not be blank");
        }
        this.success = success;
        this.errorCode = errorCode;
        this.message = message;
    }

    public static OperationResult success(String message) {
        return new OperationResult(true, ErrorCode.NONE, message);
    }

    public static OperationResult failure(ErrorCode errorCode, String message) {
        if (errorCode == ErrorCode.NONE) {
            throw new IllegalArgumentException("Failure result must have a real error code");
        }
        return new OperationResult(false, errorCode, message);
    }

    public boolean isSuccess() { return success; }
    public boolean isFailure() { return !success; }
    public ErrorCode getErrorCode() { return errorCode; }
    public String getMessage() { return message; }
}
