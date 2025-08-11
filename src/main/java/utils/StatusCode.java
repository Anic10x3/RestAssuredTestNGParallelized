package utils;

public enum StatusCode {

    CODE_200(200, "OK"),
    CODE_404(404, "Not Found");

    private final int code;
    private final String description;

    StatusCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
