package com.example.rest.Exceptions;

public class ExerciseNotFoundException extends RuntimeException{
    
    public ExerciseNotFoundException(Long id) {
        super("Could not find exercise" + id);
    }
}
