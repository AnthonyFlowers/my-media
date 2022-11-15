package mymedia.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) throws Exception {
        if (ex instanceof SQLIntegrityConstraintViolationException) {
            return new ResponseEntity<>(
                    "Data Integrity Failure. Your request failed.",
                    HttpStatus.BAD_REQUEST);
        } else if (ex instanceof HttpMessageNotReadableException) {
            return new ResponseEntity<>(
                    "Message Not Readable. Your request failed.",
                    HttpStatus.BAD_REQUEST);
        } else if (ex instanceof DataIntegrityViolationException) {
            return new ResponseEntity<String>(
                    "Data Integrity Failure. Your request failed.",
                    HttpStatus.BAD_REQUEST);
        } else if (ex instanceof HttpMediaTypeNotSupportedException) {
            return new ResponseEntity<String>(
                    "Media Type not supported. Your request failed.",
                    HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        } else {
            ex.printStackTrace();
            return new ResponseEntity<>(
                    "Generic Failure. Error unknown.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
