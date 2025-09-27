package br.com.gdev.spring_auth_server.infra.handler;

import br.com.gdev.spring_auth_server.infra.dto.ErrorFields;
import br.com.gdev.spring_auth_server.infra.dto.ErrorResponse;
import br.com.gdev.spring_auth_server.infra.exception.AlreadyOwnerException;
import br.com.gdev.spring_auth_server.infra.exception.ClientNotFoundExeption;
import br.com.gdev.spring_auth_server.infra.exception.TokenNotFoundException;
import br.com.gdev.spring_auth_server.infra.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class ControllerAdviceHandler {


    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse genericErrorHandler(RuntimeException ex){
        return new ErrorResponse(
                ex.getMessage(),
                LocalDateTime.now(),
                500,
                List.of());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse argumentoNotValidExceptionHandler(MethodArgumentNotValidException ex){
        List<ErrorFields> erros = ex.getFieldErrors()
                .stream()
                .map(er -> new ErrorFields(er.getField(), er.getDefaultMessage())).toList();

        return new ErrorResponse(
                "erro during entity processing",
                LocalDateTime.now(),
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                erros);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse userNotFoundExceptionHandler(UserNotFoundException ex){
        return new ErrorResponse(
                ex.getMessage(),
                LocalDateTime.now(),
                404,
                List.of()
        );
    }


    @ExceptionHandler(ClientNotFoundExeption.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse ClientNotFoundExceptionHandler(ClientNotFoundExeption ex){
        return new ErrorResponse(
                ex.getMessage(),
                LocalDateTime.now(),
                404,
                List.of()
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse parseExceptionHandler(HttpMessageNotReadableException ex){
        return new ErrorResponse(
                ex.getMessage(),
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                List.of()
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse accessDeniedExceptionHandler(AccessDeniedException ex){
        return new ErrorResponse(
                ex.getMessage(),
                LocalDateTime.now(),
                403,
                List.of()
        );
    }

    @ExceptionHandler(TokenNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse tokenErrorExceptionHandler(TokenNotFoundException ex){
        return new ErrorResponse(
                ex.getMessage(),
                LocalDateTime.now(),
                401,
                List.of()
        );
    }

    @ExceptionHandler(AlreadyOwnerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse alreadyOwnerExceptionHandler(AlreadyOwnerException e){
        return new ErrorResponse(e.getMessage(), LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(), List.of());
    }

}
