package io.kite;

public class EnrollmentResult {
    private boolean success;
    private String reason;

    public EnrollmentResult(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getReason() {
        return reason;
    }
}
