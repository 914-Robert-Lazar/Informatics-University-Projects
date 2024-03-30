package com.example.rest.Model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Exercise {
    private @Id @GeneratedValue Long id;
    private String name;
    private String type;
    private int level;

    public Exercise() {}

    public Exercise(String name, String type, int level) {
        this.name = name;
        this.type = type;
        this.level = level;
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
