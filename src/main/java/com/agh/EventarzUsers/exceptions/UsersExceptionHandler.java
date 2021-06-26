package com.agh.EventarzUsers.exceptions;

import com.agh.EventarzUsers.model.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class UsersExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity handleUserNotFoundException(UserNotFoundException exception, HttpServletRequest request) {
        return getResponse(HttpStatus.NOT_FOUND, request.getRequestURI(), "User not found!");
    }

    private ResponseEntity<ErrorDTO> getResponse(HttpStatus status, String requestURI, String message) {
        ErrorDTO errorDTO = new ErrorDTO(status, requestURI, message);
        return ResponseEntity.status(status).body(errorDTO);
    }
}
