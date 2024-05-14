package com.example.rest.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.rest.Model.Exercise;
import com.example.rest.Model.ExerciseWithMuscleCount;

public interface ExerciseRepository extends JpaRepository<Exercise, Long>{
    
    @Query(nativeQuery = true, value = "SELECT TOP 1 * FROM exercise ORDER BY NEWID()")
    Exercise randomExercise();

    // @Query("SELECT e FROM Exercise e LEFT JOIN FETCH e.muscles m WHERE e.id = :exerciseID")
    // Page<Exercise> findByIdWithMinimalMuscle(@Param("exerciseID") Long exerciseId, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM muscle WHERE ExerciseID = :exerciseID")
    int numberOfMusclesWithID(@Param("exerciseID") Long id);

    @Query(nativeQuery = true, value = "SELECT ExerciseID as id, COUNT(*) as numberOfMuscles FROM muscle GROUP BY ExerciseID")
    List<ExerciseWithMuscleCount> numberOfMuscles();
}
