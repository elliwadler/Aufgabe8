package com.example.aufgabe8_wadler;

import com.example.aufgabe8_wadler.Tables.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("select p FROM Project p, User u where p.student.id = ?1 and u.id = ?1")
    ArrayList<Project> findProjectByStudentID(long StudentID);

    @Query("select p FROM Project p, User u where p.leader.id = ?1 and u.id = ?1")
    ArrayList<Project> findProjectByLeaderID(long LeaderID);
}
