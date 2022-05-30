package com.example.aufgabe8_wadler.Controller;

import com.example.aufgabe8_wadler.repository.StudentRepository;
import com.example.aufgabe8_wadler.Tables.User;
import com.example.aufgabe8_wadler.UserRepository;
import com.example.aufgabe8_wadler.exception.ApiRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Optional;


@Controller
public class LoginController {

    UserRepository userRepository;
    StudentRepository studentRepository;

    @Autowired
    LoginController(UserRepository userRepository, StudentRepository studentRepository){
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/")
    public String user(Principal p){

        try {
            Optional<User> user = userRepository.findByUsername(p.getName());
            if (studentRepository.findUserById(user.get().getId()) != null) {
                return "redirect:/WelcomeStudent";
            } else {
                return "redirect:/WelcomeLeader";
            }
        }
        catch(Exception e){
            throw new ApiRequestException("Ooops something went wrong!");
        }
    }
}
