package ru.clevertec.testWork.controllers.exception;


import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.clevertec.testWork.dto.exceptions.ResponseError;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseError handlerEntityNotFountException (EntityNotFoundException e){
        log.info(e.toString());
        return new ResponseError("INCORRECT REQUEST", e.toString());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    private ResponseError serverErrorRuntime (RuntimeException ex) {
        return new ResponseError("INTERNAL SERVER ERROR", ex.toString());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NoSuchElementException.class)
    private ResponseError noSuchElement (NoSuchElementException ex) {
        return new ResponseError("NO SUCH ELEMENT", ex.toString());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    private ResponseError messageNotReadable (MissingServletRequestParameterException ex) {
        return new ResponseError("NO CORRECT REQUEST", ex.toString());
    }





}
