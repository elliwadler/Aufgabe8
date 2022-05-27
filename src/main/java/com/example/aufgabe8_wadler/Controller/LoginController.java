package com.example.aufgabe8_wadler.Controller;

import com.example.aufgabe8_wadler.MyUserDetails;
import com.example.aufgabe8_wadler.StudentRepository;
import com.example.aufgabe8_wadler.Tables.User;
import com.example.aufgabe8_wadler.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

        Optional<User> user =  userRepository.findByUsername(p.getName());
        if(studentRepository.findUserById(user.get().getId()) != null) {
            return "redirect:/WelcomeStudent";
        }
        else{
            return "Leader";
        }
    }
}
