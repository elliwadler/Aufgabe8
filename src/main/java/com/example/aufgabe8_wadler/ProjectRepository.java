package com.example.aufgabe8_wadler;

import com.example.aufgabe8_wadler.Tables.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("select p FROM Project p, User u where p.student = ?1")
    ArrayList<Project> findProjectByStudentID(long StudentID);
}
