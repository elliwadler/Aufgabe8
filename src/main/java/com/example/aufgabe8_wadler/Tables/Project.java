/*
 * project management system
 * Spring-boot, Thymeleaf, MySQL
 * Author: Elisabeth Wadler
 * Last Change: 03.06.2022
 */

package com.example.aufgabe8_wadler.Tables;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table
public class Project {
    @Id
    @SequenceGenerator(
            name="project_sequence",
            sequenceName = "project_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "project_sequence"
    )
    private Long id;
    private String name;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate examDate;

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "user_id", nullable = true)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id1", nullable = false)
    private Leader leader;

    private int type;


    public Project() {
    }

    public Project(String name, String description, LocalDate deadline, LocalDate examDate, Leader leader){
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.examDate = examDate;
        this.leader = leader;
        this.type = 3;
    }

    public Project(String name, String description, LocalDate deadline, Leader leader){
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.leader = leader;
        this.type = 2;
    }
    public Project(String name,  LocalDate deadline,  Leader leader){
        this.name = name;
        this.deadline = deadline;
        this.leader = leader;
        this.type = 1;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", examDate=" + examDate +
                ", student=" + student +
                ", leader=" + leader +
                ", type=" + type +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    public void setExamDate(LocalDate examDate) {
        this.examDate = examDate;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public User getLeader() {
        return leader;
    }

    public void setLeader(Leader leader) {
        this.leader = leader;
    }

    public int getType(){return type;}

    public void setType(int type){this.type = type;}
}
