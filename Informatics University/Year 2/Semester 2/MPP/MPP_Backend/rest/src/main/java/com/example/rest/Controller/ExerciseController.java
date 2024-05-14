package com.example.rest.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.example.rest.Exceptions.ExerciseNotFoundException;

import com.example.rest.Model.Exercise;
import com.example.rest.Model.ExerciseDTO;
import com.example.rest.Model.ExerciseWithMuscleCount;
import com.example.rest.Model.Muscle;
import com.example.rest.Repository.ExerciseRepository;
import com.example.rest.Repository.MuscleRepository;
import com.example.rest.Validators.ValidateExercise;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

@RestController
@CrossOrigin
public class ExerciseController {
    
    @Autowired
    private final ExerciseRepository repository;

    @Autowired
    private final MuscleRepository muscleRepository;

    private HashMap<Long, Integer> exerciseWithMuscleCounts;
    
    @Autowired
    private SimpMessagingTemplate template;

    @SuppressWarnings("deprecation")
    private FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-GB"), new RandomService());

    private static final Logger log = LoggerFactory.getLogger(ExerciseController.class);

    ModelMapper modelMapper = new ModelMapper();

    private Exercise createRandomExercise() {
        Random rand = new Random();
        Integer nameLength = rand.nextInt(15) + 1;
        String name = fakeValuesService.regexify("[a-z]{" + nameLength + "}");
        Integer typeNumber = rand.nextInt(3);
        String type = "";
        switch (typeNumber) {
            case 0:
                type = "push";
                break;
            case 1:
                type = "pull";
                break;
            case 2:
                type = "leg";
                break;
            default:
                break;
        }
        
        Integer level = rand.nextInt(5) + 1;

        return new Exercise(name, type, level);
    }

    @MessageMapping("/generatedExercise")
    @SendTo("/topic/generatedExercise")
    public Exercise sendExercise(Exercise exercise) {
        return exercise;
    }

    public Exercise generateExercise() {
        Exercise currentExercise = createRandomExercise();
        log.info("Preloading " + currentExercise);
        // this.template.convertAndSend("/topic/generatedExercise", currentExercise);
        return repository.save(currentExercise);
    }

    public ExerciseController(ExerciseRepository repository, MuscleRepository muscleRepository) {
        this.repository = repository;
        this.muscleRepository = muscleRepository;
        this.exerciseWithMuscleCounts = new HashMap<>();
        List<ExerciseWithMuscleCount> list = this.repository.numberOfMuscles();

        list.forEach(item -> {
            this.exerciseWithMuscleCounts.put(item.getId(), item.getNumberOfMuscles());
            // log.info("ID: " + item.getId() + ", number: " + item.getNumberOfMuscles());
        });
    }

    
    @GetMapping("/status")
    public ResponseEntity<String> checkStatus() {
        return ResponseEntity.ok("Server is running");
    }

    // @GetMapping("/api/exercises")
    // List<Exercise> all() {
    //     return repository.findAll();
    // }

    @GetMapping("/api/exercises")
    public Page<ExerciseDTO> getPage(Pageable pageable) {
        Page<Exercise> page = repository.findAll(pageable);
        return page.map(exercise -> {
            ExerciseDTO mapped = modelMapper.map(exercise, ExerciseDTO.class);
            Integer numberOfMuscles = exerciseWithMuscleCounts.get(exercise.getId());
            if (numberOfMuscles == null) {
                mapped.setNumberOfMuscles(0);
            }
            else {
                mapped.setNumberOfMuscles(numberOfMuscles);
            }
            // mapped.setNumberOfMuscles(exercise.getMuscles().size());
            return mapped;
        });
    }

    @PostMapping("/api/exercises")
    @ResponseStatus(HttpStatus.CREATED)
    ExerciseDTO newExercise(@RequestBody Exercise newExercise) {
        ValidateExercise validator = new ValidateExercise(newExercise);
        if (validator.validate().equals("ok")) {
            return modelMapper.map(repository.save(newExercise), ExerciseDTO.class);
        }
        else {
            return modelMapper.map(new Exercise("Invalid", "", 0), ExerciseDTO.class);
        }
    }

    
    @GetMapping("/api/exercises/{id}") 
    ExerciseDTO one(@PathVariable Long id) {
        // return repository.findById(id).orElseThrow(() -> new ExerciseNotFoundException(id));
        // Pageable pageable = PageRequest.of(0, 50);
        // return repository.findByIdWithMinimalMuscle(id, pageable).toList().get(0);
        Exercise exercise = repository.findById(id).orElseThrow(() -> new ExerciseNotFoundException(id));
        ExerciseDTO mapped = modelMapper.map(exercise, ExerciseDTO.class);
        Integer numberOfMuscles = exerciseWithMuscleCounts.get(exercise.getId());
        if (numberOfMuscles == null) {
            mapped.setNumberOfMuscles(0);
        }
        else {
            mapped.setNumberOfMuscles(numberOfMuscles);
        }
        return mapped;
    }

    
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

    @PutMapping("/api/exercises/{id}/muscle")
    Exercise addMuscleToExercise(@PathVariable("id") Long id, @RequestBody Muscle newMuscle) {
        Integer currentCount = this.exerciseWithMuscleCounts.get(id);
        if (currentCount == null) {
            this.exerciseWithMuscleCounts.put(id, 1);
        }
        else {
            this.exerciseWithMuscleCounts.put(id, currentCount + 1);
        }
        return repository.findById(id)
        .map(exercise -> {
            exercise.addMuscle(newMuscle);
            return repository.save(exercise);
        }).orElseGet(() -> {
            return new Exercise("Invalid", "", 0);
        });
    }

    
    @DeleteMapping("/api/exercises/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteExercise(@PathVariable Long id) {
        repository.findById(id)
        .map(exercise -> {
            for (Muscle muscle : exercise.getMuscles()) {
                muscleRepository.delete(muscle);
            }
            return exercise;
        }).
        orElseGet(() -> {
            return null;
        });
        repository.deleteById(id);
        this.exerciseWithMuscleCounts.remove(id);
    }

    @DeleteMapping("/api/exercises/{exerciseId}/muscles/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteMuscle(@PathVariable("id") Long id, @PathVariable("exerciseId") Long exerciseId) {
        this.exerciseWithMuscleCounts.put(exerciseId, this.exerciseWithMuscleCounts.get(exerciseId) - 1);
        muscleRepository.deleteById(id);
    }
}
