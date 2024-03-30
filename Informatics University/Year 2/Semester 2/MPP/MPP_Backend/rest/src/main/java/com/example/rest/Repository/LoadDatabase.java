package com.example.rest.Repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.rest.Model.Exercise;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(Repository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new Exercise("Push-up", "push",  2)));
            log.info("Preloading " + repository.save(new Exercise("Pull-up", "pull", 3)));
            log.info("Preloading " + repository.save(new Exercise("One leg squat", "leg", 4)));
            log.info("Preloading " + repository.save(new Exercise("Paralell Bar Dip", "push", 3)));
            log.info("Preloading " + repository.save(new Exercise("Ring dip", "push", 4)));
            log.info("Preloading " + repository.save(new Exercise("Pike push-up", "push", 3)));
            log.info("Preloading " + repository.save(new Exercise("Ring bodyrows", "pull", 2)));
            log.info("Preloading " + repository.save(new Exercise("Reverse nordic curl", "leg", 5)));
            log.info("Preloading " + repository.save(new Exercise("One arm push up", "push", 5)));
        };
    }
}
