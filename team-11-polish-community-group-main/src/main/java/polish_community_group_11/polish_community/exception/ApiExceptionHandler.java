package polish_community_group_11.polish_community.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.invoke.MethodHandles;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @ExceptionHandler(value = {EmptyResultDataAccessException.class})
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        return buildResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<Object> handleNullPointerException(NullPointerException e) {
        return buildResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {IndexOutOfBoundsException.class})
    public ResponseEntity<Object> handleIndexOutOfBoundsException(IndexOutOfBoundsException e) {
        return buildResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ArithmeticException.class})
    public ResponseEntity<Object> handleArithmeticException(ArithmeticException e) {
        return buildResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UnsupportedOperationException.class})
    public ResponseEntity<Object> handleUnsupportedOperationException(UnsupportedOperationException e) {
        return buildResponse(e, HttpStatus.NOT_IMPLEMENTED);
    }

    // Fallback handler for unknown exceptions
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleUnknownException(Exception e) {
        return buildResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Generic method to build the response entity
    private ResponseEntity<Object> buildResponse(Exception e, HttpStatus status) {
        LOG.error("Returning {} status for error {}", status, e.getMessage(), e);
        ApiException apiException = new ApiException(
                e.getMessage(),
                status,
                ZonedDateTime.now(ZoneId.of("UTC"))
        );
        return new ResponseEntity<>(apiException, status);
    }
}
