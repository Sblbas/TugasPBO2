package response;

public class ErrorResponse {
    private boolean success;
    private String error;
    private int status;

    public ErrorResponse() {
        this.success = false;
    }

    public ErrorResponse(String error, int status) {
        this.success = false;
        this.error = error;
        this.status = status;
    }

    // Getters & Setters
    public boolean isSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }

    public int getStatus() {
        return status;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
