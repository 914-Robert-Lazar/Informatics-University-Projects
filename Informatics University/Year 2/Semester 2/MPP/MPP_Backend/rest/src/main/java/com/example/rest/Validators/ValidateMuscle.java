package com.example.rest.Validators;

import com.example.rest.Model.Muscle;

public class ValidateMuscle {
    private Muscle muscle;
    
    public ValidateMuscle(Muscle muscle) {
        this.muscle = muscle;
    }

    public String validate() {
        if (this.muscle.getName().isEmpty()) {
            return "emptyName";
        }

        if (this.muscle.getSize() < 1) {
            return "nonPositiveSize";
        }

        return "ok";
    }
}
