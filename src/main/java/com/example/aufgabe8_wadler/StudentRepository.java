package com.example.aufgabe8_wadler;

import com.example.aufgabe8_wadler.Tables.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("select u FROM Student u where u.username = ?1")
    Optional<Student> findUserByUsername(String username);

    Student findUserById(long ID);

    @Query("select u.level FROM Student u where u.username = ?1")
    int findLevelByUsername(String username);

    @Query("select u.id FROM Student u where u.username = ?1")
    long findIdByUsername(String username);

    @Query("select u.id FROM Student u where u.username = ?1 and u.password=?2 ")
    Long authenticate(String username, String password);

    @Query("SELECT CASE WHEN COUNT(u)> 0 THEN true ELSE false END FROM Student u WHERE u.id = ?1")
    boolean exists(Long id);

    @Transactional
    @Modifying
    @Query("update Student u set u.level = ?1 where u.id = ?2")
    void updateLevel(int level, long id);
}
