package com.example.rest.Validators;

import com.example.rest.Model.Exercise;

public class ValidateExercise {

    private Exercise exercise;
    
    public ValidateExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public String validate() {
        if (this.exercise.getName().isEmpty()) {
            return "emptyName";
        }

        if (!this.exercise.getType().equals("push") && !this.exercise.getType().equals("pull") &&
            !this.exercise.getType().equals("leg")) {

            return "emptyType";
        }

        if (this.exercise.getLevel() < 1) {
            return "nonPositiveLevel";
        }

        return "ok";
    }
}
