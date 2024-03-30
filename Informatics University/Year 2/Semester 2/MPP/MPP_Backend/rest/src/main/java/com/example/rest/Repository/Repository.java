package com.example.rest.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.rest.Model.Exercise;

public interface Repository extends JpaRepository<Exercise, Long>{
    
}
