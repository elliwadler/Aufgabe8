package com.example.aufgabe8_wadler.Tables;

import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
@Table
public class Student extends User{

    int level;
    @OneToOne(mappedBy = "student", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Project project;

    public Student() {
    }

    public Student(String lastName, String firstName, String username, String password, int level) {
        super(lastName, firstName, username, password);
        this.level = level;
    }

    @Override
    public String toString() {
        return "Student{" +
                "level=" + level +
                ", project=" + project +
                '}';
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

}
