package co.com.poli.products.exceptions.response;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
public class ValidationErrorResponse {
    private List<Violation> errors;

    @Getter
    @Setter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Violation {
        private String code;
        private String message;
    }
}