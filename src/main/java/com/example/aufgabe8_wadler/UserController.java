package com.example.aufgabe8_wadler;

import com.example.aufgabe8_wadler.Tables.Project;
import com.example.aufgabe8_wadler.Tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@Controller
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private User user;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository, ProjectRepository projectRepository){
        this.userService = userService;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    //LANDINGPAGE
    @GetMapping("/")
    public String index(Model model) {
        return "hello";

    }

    //LOGIN INFORMATION
    @PostMapping("/test")
    public String loginUser(@RequestParam String username, @RequestParam String password) {

        Long id = userRepository.authenticate(username, password);

        if(userRepository.exists(id)) {
            System.out.println(username + ", " + password+ ", "+id);
            user = userRepository.findUserByID(id);
            if(user.getRole() == 1){
                return "redirect:/WelcomeAdmin";
            }
            else if(user.getRole() == 2){
                return "redirect:/WelcomeAssistent";
            }
            else{
                //return "student";
                return "redirect:/WelcomeStudent";
            }

        }
        else{
             return "loginError";
        }
    }


    //STUDENT WELCOMEPAGE
    @GetMapping("/WelcomeStudent")
    public String welcomeStudent(Model model){
        model.addAttribute("user", user);
       // ArrayList<Project> projects = new ArrayList<com.example.aufgabe8_wadler.Tables.Project>();
       // projects = projectRepository.findProjectByStudentID(user.getId());
       // model.addAttribute("projects", projects);
        return "student";
    }

    //ADMIN WELCOMEPAGE
    @GetMapping("/WelcomeAdmin")
    public String welcomeAdmin(Model model){
        model.addAttribute("user", user);
        return "admin";
    }

    //ASSISTENT WELCOMEPAGE
    @GetMapping("/WelcomeAssistent")
    public String welcomeAssistent(Model model){
        model.addAttribute("user", user);

        return "asi";
    }

    @PostMapping
    public void registerNewUser(@RequestBody User user){
        userService.addNewUser(user);
    }
    @DeleteMapping(path = "{userID}")
    public void deleteUser(@PathVariable("userID") Long id){
        userService.deleteStudent(id);
    }
    @PutMapping(path = "{userID}")
    public void updateUser(
            @PathVariable("userID") Long userID,
            @RequestParam(required = false) String username){
        userService.updateUser(userID, username);
    }

}
