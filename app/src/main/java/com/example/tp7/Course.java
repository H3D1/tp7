package com.example.tp7;

public class Course {
    private String name;
    private float hours;
    private String type; // "Course" or "Workshop"
    private int teacherId;

    public Course(String name, float hours, String type, int teacherId) {
        this.name = name;
        this.hours = hours;
        this.type = type;
        this.teacherId = teacherId;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public float getHours() { return hours; }
    public void setHours(float hours) { this.hours = hours; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getTeacherId() { return teacherId; }
    public void setTeacherId(int teacherId) { this.teacherId = teacherId; }
}