/*
 * project management system
 * Spring-boot, Thymeleaf, MySQL
 * Author: Elisabeth Wadler
 * Last Change: 03.06.2022
 */

package com.example.aufgabe8_wadler.service;
import com.example.aufgabe8_wadler.Tables.Leader;
import com.example.aufgabe8_wadler.Tables.Student;
import com.example.aufgabe8_wadler.Tables.User;
import com.example.aufgabe8_wadler.repository.LeaderRepository;
import com.example.aufgabe8_wadler.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final StudentRepository studentRepository;
    private final LeaderRepository leaderRepository;

    @Autowired
    public UserService( LeaderRepository leaderRepository, StudentRepository studentRepository){
        this.studentRepository=studentRepository;
        this.leaderRepository=leaderRepository;
    }

    public void addNewUser(User user, int role){
            if (role == 1) {
                Student student = new Student(user.getLastName(), user.getFirstName(), user.getUsername(), user.getPassword());
                studentRepository.save(student);
            } else {
                Leader leader = new Leader(user.getLastName(), user.getFirstName(), user.getUsername(), user.getPassword());
                leaderRepository.save(leader);
            }

    }
}
