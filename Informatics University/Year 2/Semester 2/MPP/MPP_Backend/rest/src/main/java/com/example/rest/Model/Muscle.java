package com.example.rest.Model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Muscle")
public class Muscle {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MuscleID")
    private Long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Size")
    private int size;
    
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ExerciseID")
    private Exercise exerciseInUse;


    public Muscle() {
        this.name = "INVALID";
        this.size = 0;
        this.exerciseInUse = null;
    }

    public Muscle(String name, Exercise exerciseInUse, int size) {
        this.name = name;
        this.exerciseInUse = exerciseInUse;
        this.size = size;
    }

    public Long getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }

    @JsonIgnore
    public Exercise getExerciseInUse() {
        return this.exerciseInUse;
    }
    public int getSize() {
        return this.size;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public void setExerciseInUse(Exercise exerciseInUse) {
        if (sameAsFormer(exerciseInUse)) {
            return;
        }

        Exercise oldExercise = this.exerciseInUse;
        this.exerciseInUse = exerciseInUse;

        if (oldExercise != null) {
            oldExercise.removeMuscle(this);
        }

        if (exerciseInUse != null) {
            exerciseInUse.addMuscle(this);
        }
    }

    private boolean sameAsFormer(Exercise newExercise) {
        return exerciseInUse == null ? newExercise == null : exerciseInUse.equals(newExercise);
    }
    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Exercise))
            return false;

        Muscle muscle = (Muscle) o;
            return Objects.equals(this.id, muscle.id) && Objects.equals(this.name, muscle.name)
                && Objects.equals(this.exerciseInUse, muscle.exerciseInUse) && Objects.equals(this.size, muscle.size);
    }

    @Override
    public String toString() {
        return "Muscle{" + "id=" + this.id + ", name='" + this.name + '\'' + ", size='" + this.size + '}';
    }
}
