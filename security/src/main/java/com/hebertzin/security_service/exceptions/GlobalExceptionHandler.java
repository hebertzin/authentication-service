package com.hebertzin.security_service.exceptions;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiError> handleValidation( MethodArgumentNotValidException ex,
                                              HttpServletRequest request) {

         return buildError(
                 HttpStatus.BAD_REQUEST,
                 ex.getCause().toString(),
                 ex.getMessage(),
                 request.getRequestURL().toString());
    }


    @ExceptionHandler(ConflictException.class)
    ResponseEntity<ApiError> handleConflictException(ConflictException ex, HttpServletRequest request){
        return buildError(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                ex.getCause().toString(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(BadRequestException.class)
    ResponseEntity<ApiError> handleBadRequestException(BadRequestException ex, HttpServletRequest request){
        return buildError(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                ex.getCause().toString(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(InternalServerErrorException.class)
    ResponseEntity<ApiError> handleInternalServerErrorException(InternalServerErrorException ex, HttpServletRequest request){
        return buildError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                ex.getCause().toString(),
                request.getRequestURI()
        );
    }


    private ResponseEntity<ApiError> buildError(HttpStatus status,
                                                String title,
                                                String message,
                                                String path
    ) {
        ApiError error = new ApiError(
                status.getReasonPhrase(),
                title,
                message,
                path
        );

        return ResponseEntity.status(status).body(error);
    }

}
