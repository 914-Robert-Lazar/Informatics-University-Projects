package com.example.rest.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.rest.Model.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, Long>{
    
    @Query(nativeQuery = true, value = "SELECT TOP 1 * FROM exercise ORDER BY NEWID()")
    Exercise randomExercise();
}
