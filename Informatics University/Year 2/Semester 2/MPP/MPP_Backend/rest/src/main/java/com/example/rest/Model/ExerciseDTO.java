package com.example.rest.Model;

public class ExerciseDTO {
    private Long id;

    private String name;

    private String type;

    private int level;

    private int numberOfMuscles = 0;

    public ExerciseDTO() {}

    public ExerciseDTO(String name, String type, int level, int numberOfMuscles) {
        this.name = name;
        this.type = type;
        this.level = level;
        this.numberOfMuscles = numberOfMuscles;
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
    public int getNumberOfMuscles() {
        return this.numberOfMuscles;
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
    public void setNumberOfMuscles(int numberOfMuscles) {
        this.numberOfMuscles = numberOfMuscles;
    }
}
