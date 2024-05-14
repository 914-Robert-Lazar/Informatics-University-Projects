package com.example.rest.Model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Exercise")
public class Exercise {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ExerciseID")
    private  Long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Type")
    private String type;

    @Column(name = "Level")
    private int level;

    @JsonManagedReference
    @OneToMany(mappedBy = "exerciseInUse", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Muscle> muscles;

    public Exercise() {}

    public Exercise(String name, String type, int level) {
        this.name = name;
        this.type = type;
        this.level = level;
        this.muscles = new ArrayList<Muscle>();
    }

    public Long getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getType() {
        return this.type;
    }
    public int getLevel() {
        return this.level;
    }
    
    @JsonIgnore
    public List<Muscle> getMuscles() {
        return this.muscles;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public void setMuscles(List<Muscle> muscles) {
        this.muscles = muscles;
    }

    public void addMuscle(Muscle muscle) {
        if (muscles.contains(muscle)) {
            return;
        }

        this.muscles.add(muscle);

        muscle.setExerciseInUse(this);
    }

    public void removeMuscle(Muscle muscle) {
        if (!muscles.contains(muscle)) {
            return;
        }

        muscles.remove(muscle);

        muscle.setExerciseInUse(null);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Exercise))
            return false;

        Exercise exercise = (Exercise) o;
            return Objects.equals(this.id, exercise.id) && Objects.equals(this.name, exercise.name)
                && Objects.equals(this.type, exercise.type) && Objects.equals(this.level, exercise.level);
    }

    @Override
    public String toString() {
        return "Exercise{" + "id=" + this.id + ", name='" + this.name + '\'' + ", type='" + this.type + '\'' + ", level='" + this.level + '}';
    }
}
