package com.lottus.tech.processo_seletico_lottus_tech.web.dto.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ErrorMessage {

    private String path;
    private String method;
    private String status;
    private String statusText;
    private String message;
    private Map<String, String> errors;

    public ErrorMessage() {
    }

    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = String.valueOf(status.value());
        this.statusText = status.getReasonPhrase();
        this.message = message;
    }


    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message, BindingResult bindingResult) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = String.valueOf(status.value());
        this.statusText = status.getReasonPhrase();
        this.message = message;
        addErrors(bindingResult);
    }


    private void addErrors(BindingResult bindingResult) {
        this.errors = new HashMap<>();
        for (var error : bindingResult.getFieldErrors()) {
            this.errors.put(error.getField(), error.getDefaultMessage());
        }
    }
}
