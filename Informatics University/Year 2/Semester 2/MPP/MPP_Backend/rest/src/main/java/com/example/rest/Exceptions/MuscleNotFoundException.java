package com.example.rest.Exceptions;

public class MuscleNotFoundException extends RuntimeException{
    
    public MuscleNotFoundException(Long id) {
        super("Could not find muscle" + id);
    }
}
