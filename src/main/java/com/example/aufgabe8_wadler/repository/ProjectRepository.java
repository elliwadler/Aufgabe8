/*
 * project management system
 * Spring-boot, Thymeleaf, MySQL
 * Author: Elisabeth Wadler
 * Last Change: 03.06.2022
 */

package com.example.aufgabe8_wadler.repository;

import com.example.aufgabe8_wadler.Tables.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("select p FROM Project p, User u where p.student.id = ?1 and u.id = ?1")
    ArrayList<Project> findProjectByStudentID(long StudentID);

    @Query("select p FROM Project p, User u where p.leader.id = ?1 and u.id = ?1")
    ArrayList<Project> findProjectByLeaderID(long LeaderID);

    @Query("select p FROM Project p where p.student.id = null ")
    ArrayList<Project> findOpenProjects();

    @Query("select p FROM Project p where p.student.id = null and  p.type=?1")
    ArrayList<Project> findOpenProjectsByType(int level);

    @Query("select p FROM Project p where p.leader.id = ?1  and  p.type=?2 ")
    ArrayList<Project> findOpenProjectsByLeaderAndType(long leaderID, int type);
}
