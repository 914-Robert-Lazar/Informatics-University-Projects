package com.example.rest.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.rest.Model.Muscle;

public interface MuscleRepository extends JpaRepository<Muscle, Long>{
    
}
