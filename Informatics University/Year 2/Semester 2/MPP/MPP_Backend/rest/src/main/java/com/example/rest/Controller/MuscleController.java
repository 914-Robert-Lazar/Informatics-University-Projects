package com.example.rest.Controller;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.example.rest.Exceptions.MuscleNotFoundException;
import com.example.rest.Model.Exercise;
import com.example.rest.Model.Muscle;
import com.example.rest.Repository.ExerciseRepository;
import com.example.rest.Repository.MuscleRepository;
import com.example.rest.Validators.ValidateMuscle;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

@RestController
@CrossOrigin
public class MuscleController {
    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private MuscleRepository muscleRepository;
    
    @Autowired
    private SimpMessagingTemplate template;

    @SuppressWarnings("deprecation")
    private FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-GB"), new RandomService());

    private static final Logger log = LoggerFactory.getLogger(ExerciseController.class);


    private Muscle createRandomMuscle() {
        Random rand = new Random();
        Integer nameLength = rand.nextInt(15) + 1;
        String name = fakeValuesService.regexify("[a-z]{" + nameLength + "}");
        
        Integer size = rand.nextInt(5) + 1;
        Exercise randomExercise = exerciseRepository.randomExercise();

        return new Muscle(name, randomExercise, size);
    }

    @MessageMapping("/generatedMuscle")
    @SendTo("/topic/generatedMuscle")
    public Muscle sendMuscle(Muscle muscle) {
        return muscle;
    }

    @Scheduled(fixedRate = 20000)
    public void generateMuscle() {
        Muscle currentMuscle = createRandomMuscle();
        Exercise givenExercise = currentMuscle.getExerciseInUse();
        givenExercise.addMuscle(currentMuscle);
        exerciseRepository.save(givenExercise);
        log.info("Preloading " + currentMuscle);
        this.template.convertAndSend("/topic/generatedMuscle", currentMuscle);
    }

    public MuscleController(ExerciseRepository exerciseRepository, MuscleRepository muscleRepository) {
        this.exerciseRepository = exerciseRepository;
        this.muscleRepository = muscleRepository;
    }

    @GetMapping("/api/muscles")
    List<Muscle> all() {
        return muscleRepository.findAll();
    }

    @PostMapping("/api/muscles")
    @ResponseStatus(HttpStatus.CREATED)
    Muscle newMuscle(@RequestBody Muscle newMuscle) {
        ValidateMuscle validator = new ValidateMuscle(newMuscle);
        if (validator.validate().equals("ok")) {
            return muscleRepository.save(newMuscle);
        }
        else {
            return new Muscle();
        }
    }
    
    @GetMapping("/api/muscles/{id}") 
    Muscle one(@PathVariable Long id) {
        return muscleRepository.findById(id).orElseThrow(() -> new MuscleNotFoundException(id));
    }
    
    @PutMapping("/api/muscles/{id}")
    Muscle replaceMuscle(@RequestBody Muscle newMuscle, @PathVariable Long id) {
        ValidateMuscle validator = new ValidateMuscle(newMuscle);
        if (validator.validate().equals("ok")) {
            return muscleRepository.findById(id)
            .map(muscle -> {
                muscle.setName(newMuscle.getName());
                muscle.setSize(newMuscle.getSize());
                return muscleRepository.save(muscle);
            })
            .orElseGet(() -> {
                newMuscle.setId(id);
                return muscleRepository.save(newMuscle);
            });
        }
        else {
            return new Muscle();
        }
    }

    
    @DeleteMapping("/api/muscles/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteMuscle(@PathVariable Long id) {
        muscleRepository.deleteById(id);
    }
}
