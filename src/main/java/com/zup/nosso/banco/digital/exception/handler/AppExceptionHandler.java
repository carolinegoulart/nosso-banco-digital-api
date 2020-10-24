package com.zup.nosso.banco.digital.exception.handler;

import com.zup.nosso.banco.digital.constants.HttpCodeAndStatus;
import com.zup.nosso.banco.digital.dto.ErrorResponseDTO;
import com.zup.nosso.banco.digital.exception.CpfAlreadyRegisteredException;
import com.zup.nosso.banco.digital.exception.EmailAlreadyRegisteredException;
import com.zup.nosso.banco.digital.exception.InvalidInputDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(InvalidInputDataException.class)
    public ResponseEntity<Object> handleInvalidInputDataException(InvalidInputDataException ex, WebRequest request) {
        HttpCodeAndStatus httpCodeAndStatus = HttpCodeAndStatus.BAD_REQUEST;
        ErrorResponseDTO errorResponse = createErrorResponseBuilder(httpCodeAndStatus, ex, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(CpfAlreadyRegisteredException.class)
    public ResponseEntity<Object> handleCpfAlreadyRegisteredException(CpfAlreadyRegisteredException ex, WebRequest request) {
        HttpCodeAndStatus httpCodeAndStatus = HttpCodeAndStatus.BAD_REQUEST;
        ErrorResponseDTO errorResponse = createErrorResponseBuilder(httpCodeAndStatus, ex, "CPF is already registered in the database");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    public ResponseEntity<Object> handleEmailAlreadyRegisteredException(EmailAlreadyRegisteredException ex, WebRequest request) {
        HttpCodeAndStatus httpCodeAndStatus = HttpCodeAndStatus.BAD_REQUEST;
        ErrorResponseDTO errorResponse = createErrorResponseBuilder(httpCodeAndStatus, ex, "Email is already registered in the database");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
        HttpCodeAndStatus httpCodeAndStatus = HttpCodeAndStatus.BAD_REQUEST;
        ErrorResponseDTO errorResponse = createErrorResponseBuilder(httpCodeAndStatus, ex,
                ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleDefaultException(Exception ex, WebRequest request) {
        HttpCodeAndStatus httpCodeAndStatus = HttpCodeAndStatus.UNEXPECTED_ERROR;
        ErrorResponseDTO errorResponse = createErrorResponseBuilder(httpCodeAndStatus, ex, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    private ErrorResponseDTO createErrorResponseBuilder(HttpCodeAndStatus httpCodeAndStatus,
                                                        Exception ex,
                                                        String message) {
        return ErrorResponseDTO.builder()
                .error(httpCodeAndStatus.getStatus())
                .errorCode(httpCodeAndStatus.getCode())
                .errorMessage(message)
                .timestamp(LocalDateTime.now())
                .trace(getStackTrace(ex))
                .build();
    }

    public String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

}