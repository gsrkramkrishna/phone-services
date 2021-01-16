package com.gsrk.exception;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.gsrk.model.APIError;
import com.gsrk.model.APIErrorDetails;


@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	private ResponseEntity<Object> errorResponse(HttpStatus status, Set<String> messages) {
		APIErrorDetails error = new APIErrorDetails(new Date(), status.value(), messages);

		return ResponseEntity.status(status).body(error);
	}
	
	private ResponseEntity<Object> errorResponse(HttpStatus status, String message) {
		APIError error = new APIError(new Date(), status.value(), message);

		return ResponseEntity.status(status).body(error);
	}
	
	@ExceptionHandler({ HttpStatusCodeException.class,DataIntegrityViolationException.class })
    final ResponseEntity<Object> handleHttpException(Exception ex) {

        final HttpStatus status;
        String message;
        if (ex instanceof HttpStatusCodeException) {
            status = ((HttpStatusCodeException)ex).getStatusCode();
            message = ((HttpStatusCodeException)ex).getStatusText();
            if("".equals(message)) message = "Not Found";
        } else {
            message = ex.getMessage();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return errorResponse(status, message);
    }

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException exception,
			WebRequest request) {
		Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
		
		if (violations != null && violations.size() > 1) {
			Set<String> errors = new HashSet<String>();
			violations.stream().map(cv -> cv.getMessage()).forEach(errors::add);
			return errorResponse(HttpStatus.BAD_REQUEST, errors);
		} else {
			Optional<ConstraintViolation<?>> constraintViolation = exception.getConstraintViolations().stream()
					.findFirst();
			if (constraintViolation.isPresent()) {
				return errorResponse(HttpStatus.BAD_REQUEST, constraintViolation.get().getMessage());
			}
		}
		return errorResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
	}
	
	
	
	@Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatus status,
            @NonNull WebRequest request)
    {
    	List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
    	if(fieldErrors.size() < 2) {
    		String message = fieldErrors.stream()
                    .map((fieldError) -> String.format("Error found for parameter '%s'; %s", fieldError.getField(), fieldError.getDefaultMessage()))
                    .findFirst()
                    .orElse(ex.getMessage());
    		return errorResponse(status, message);
    	}else {
    		Set<String> errors = new TreeSet<String>();
    		fieldErrors.stream()
    		.map((fieldError) -> String.format("Error found for parameter '%s'; %s", fieldError.getField(), fieldError.getDefaultMessage()))
    		.forEach(errors::add);
    		
    		return errorResponse(status, errors);
    	}
    }

}
