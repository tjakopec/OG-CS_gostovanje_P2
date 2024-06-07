package hr.ogcs.springbootcrudpostgres.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> resourceNotFoundException(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(value = {IllegalAccessException.class})
    public ResponseEntity<Object> resourceNotFoundException(IllegalAccessException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> backendErrorException(Exception exception) {
        return ResponseEntity.internalServerError().body(exception.getMessage());
    }

}
