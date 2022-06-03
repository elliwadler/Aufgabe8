/*
 * project management system
 * Spring-boot, Thymeleaf, MySQL
 * Author: Elisabeth Wadler
 * Last Change: 03.06.2022
 */

package com.example.aufgabe8_wadler.Controller;

import com.example.aufgabe8_wadler.repository.StudentRepository;
import com.example.aufgabe8_wadler.Tables.User;
import com.example.aufgabe8_wadler.repository.UserRepository;
import com.example.aufgabe8_wadler.exception.ApiRequestException;
import com.example.aufgabe8_wadler.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/login")
    public String login(){
        return "hello";
    }
    @RequestMapping("/login-error")
    public String loginerror(Model model){
        model.addAttribute("loginError", true);
        return "hello";
    }
    @RequestMapping("/logout")
    public String logout(Model model){
        model.addAttribute("logout", true);
        return "hello";
    }

    //select between roles
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
