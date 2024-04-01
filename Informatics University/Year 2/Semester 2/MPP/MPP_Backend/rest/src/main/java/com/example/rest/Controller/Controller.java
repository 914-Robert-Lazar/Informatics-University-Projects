package com.example.rest.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.rest.Exceptions.ExerciseNotFoundException;

import com.example.rest.Model.Exercise;
import com.example.rest.Repository.Repository;
import com.example.rest.Validators.ValidateExercise;

@RestController
public class Controller {
    
    @Autowired
    private final Repository repository;

    public Controller(Repository repository) {
        this.repository = repository;
    }

    @CrossOrigin
    @GetMapping("/api/exercises")
    List<Exercise> all() {
        return repository.findAll();
    }

    @SuppressWarnings("null")
    @CrossOrigin
    @PostMapping("/api/exercises")
    Exercise newExercise(@RequestBody Exercise newExercise) {
        ValidateExercise validator = new ValidateExercise(newExercise);
        if (validator.validate().equals("ok")) {
            return repository.save(newExercise);
        }
        else {
            return new Exercise("Invalid", "", 0);
        }
    }

    @SuppressWarnings("null")
    @CrossOrigin
    @GetMapping("/api/exercises/{id}") 
    Exercise one(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new ExerciseNotFoundException(id));
    }

    @SuppressWarnings("null")
    @CrossOrigin
    @PutMapping("/api/exercises/{id}")
    Exercise replaceExercise(@RequestBody Exercise newExercise, @PathVariable Long id) {
        ValidateExercise validator = new ValidateExercise(newExercise);
        if (validator.validate().equals("ok")) {
            return repository.findById(id)
            .map(exercise -> {
                exercise.setName(newExercise.getName());
                exercise.setType(newExercise.getType());
                exercise.setLevel(newExercise.getLevel());
                return repository.save(exercise);
            })
            .orElseGet(() -> {
                newExercise.setId(id);
                return repository.save(newExercise);
            });
        }
        else {
            return new Exercise("Invalid", "", 0);
        }
    }

    @SuppressWarnings("null")
    @CrossOrigin
    @DeleteMapping("/api/exercises/{id}")
    void deleteExercise(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
