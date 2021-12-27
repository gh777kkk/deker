package com.deker.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
    private Integer status;

    private String error;

    private String message;

    private String path;

    public ExceptionResponse(HttpStatus httpStatus, BindingResult bindingResult, String path) {
        this.status = httpStatus.value();
        this.error = httpStatus.name();
        this.message = createErrorMessage(bindingResult);
        this.path = path;
    }

    public ExceptionResponse(Integer httpStatus, String reason, String path) {
        this.status = httpStatus;
        this.message = reason;
        this.path = path;
    }

    private static String createErrorMessage(BindingResult bindingResult) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean isFirst = true;

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            if (!isFirst) {
                stringBuilder.append(", ");
            } else {
                isFirst = false;
            }

            stringBuilder.append("[");
            stringBuilder.append(fieldError.getField());
            stringBuilder.append("] ");
            stringBuilder.append(fieldError.getDefaultMessage());
            stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }
}
