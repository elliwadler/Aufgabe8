/*
 * project management system
 * Spring-boot, Thymeleaf, MySQL
 * Author: Elisabeth Wadler
 * Last Change: 03.06.2022
 */

package com.example.aufgabe8_wadler.repository;

import com.example.aufgabe8_wadler.Tables.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findStudentByUsername(String username);

    Student findUserById(long ID);

    @Query("select u.level FROM Student u where u.username = ?1")
    int findLevelByUsername(String username);

    @Query("select u.id FROM Student u where u.username = ?1")
    long findIdByUsername(String username);

    @Query("select u from Student u left join Project p on u.id = p.student.id where p.student.id is null and ?1 <= u.level")
    ArrayList<Student> findStudentWithoutProject(int type);

    @Transactional
    @Modifying
    @Query("update Student u set u.level = ?1 where u.id = ?2")
    void updateLevel(int level, long id);
}
