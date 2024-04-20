package com.example.rest.Exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ControllerAdvice
class NotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(ExerciseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String exerciseNotFoundHandler(ExerciseNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(MuscleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String muscleNotFoundHandler(MuscleNotFoundException ex) {
        return ex.getMessage();
    }
}