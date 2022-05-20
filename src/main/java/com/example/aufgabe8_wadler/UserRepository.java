package com.example.aufgabe8_wadler;

import com.example.aufgabe8_wadler.Tables.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u FROM User u where u.username = ?1")
    Optional<User> findUserByUsername(String username);
    @Query("select u FROM User u where u.id = ?1")
    User findUserByID(long ID);

    @Query("select u.id FROM User u where u.username = ?1 and u.password=?2 ")
    Long authenticate(String username, String password);

    @Query("SELECT CASE WHEN COUNT(u)> 0 THEN true ELSE false END FROM User u WHERE u.id = ?1")
    boolean exists(Long id);

    @Transactional
    @Modifying
    @Query("update User u set u.level = ?1 where u.id = ?2")
    void updateLevel(int level, long id);

}
