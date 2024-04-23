package com.example.rest.Controller;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.rest.Model.Exercise;

@Configuration
class LoadDatabase {
    @Bean
    CommandLineRunner initDatabase(ExerciseController exerciseController, MuscleController muscleController) {

        return args -> {
            // log.info("Preloading " + repository.save(new Exercise("Push-up", "push",  2)));
            // log.info("Preloading " + repository.save(new Exercise("Pull-up", "pull", 3)));
            // log.info("Preloading " + repository.save(new Exercise("One leg squat", "leg", 4)));
            // log.info("Preloading " + repository.save(new Exercise("Paralell Bar Dip", "push", 3)));
            // log.info("Preloading " + repository.save(new Exercise("Ring dip", "push", 4)));
            // log.info("Preloading " + repository.save(new Exercise("Pike push-up", "push", 3)));
            // log.info("Preloading " + repository.save(new Exercise("Ring bodyrows", "pull", 2)));
            // log.info("Preloading " + repository.save(new Exercise("Reverse nordic curl", "leg", 5)));
            // log.info("Preloading " + repository.save(new Exercise("One arm push up", "push", 5)));
            // for (int i = 0; i < 1000; ++i) {
            //     Exercise currentExercise = exerciseController.generateExercise();
            //     for (int j = 0; j < 100; ++j) {
            //         muscleController.generateMuscle(currentExercise);
            //     }
            // }
            // for (int i = 0; i < 10; ++i) {
            //     muscleController.generateMuscle();
            //     TimeUnit.SECONDS.sleep(2);
            // }
        };
    }
}
