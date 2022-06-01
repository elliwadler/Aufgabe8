package com.example.aufgabe8_wadler;

import com.example.aufgabe8_wadler.Tables.Leader;
import com.example.aufgabe8_wadler.Tables.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeaderRepository extends JpaRepository<Leader, Long> {

    Optional<Leader> findLeaderByUsername(String username);

    @Query("select u FROM Leader u where u.id = ?1")
    Leader findUserByID(long ID);

    @Query("select u.id FROM Leader u where u.username = ?1 and u.password=?2 ")
    Long authenticate(String username, String password);

    @Query("SELECT CASE WHEN COUNT(u)> 0 THEN true ELSE false END FROM Leader u WHERE u.id = ?1")
    boolean exists(Long id);

    @Query("select u.id FROM Leader u where u.username = ?1")
    long findIdByUsername(String username);

}