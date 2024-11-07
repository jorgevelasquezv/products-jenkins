package co.com.poli.products.exceptions.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
    private int code;
    private String title;
    private String message;
    private List<ValidationErrorResponse.Violation> errors;

    public ErrorResponse(int code, String title, String message) {
        this.code = code;
        this.title = title;
        this.message = message;
        this.errors = List.of();
    }

    public ErrorResponse(int code, String title, String message, List<ValidationErrorResponse.Violation> errors) {
        this.code = code;
        this.title = title;
        this.message = message;
        this.errors = errors;
    }
}

