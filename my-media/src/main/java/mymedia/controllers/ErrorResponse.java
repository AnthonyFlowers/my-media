package mymedia.controllers;

import mymedia.domain.ResultType;
import mymedia.domain.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorResponse {

    public static <T> ResponseEntity<?> build(Result<T> result) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (result.getType() == null || result.getType() == ResultType.INVALID) {
            status = HttpStatus.BAD_REQUEST;
        } else if (result.getType() == ResultType.NOT_FOUND) {
            status = HttpStatus.NOT_FOUND;
        } else if (result.getType() == ResultType.IN_USE) {
            status = HttpStatus.IM_USED;
        }
        return new ResponseEntity<>(result.getMessages(), status);
    }

}
