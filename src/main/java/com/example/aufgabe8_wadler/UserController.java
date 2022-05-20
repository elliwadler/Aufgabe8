package com.example.aufgabe8_wadler;

import com.example.aufgabe8_wadler.Tables.Project;
import com.example.aufgabe8_wadler.Tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
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
    public String index() {
        return "hello";

    }
    @GetMapping("/logout")
    public String logout() {
        user = null;
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

        ArrayList<Project> projects = new ArrayList<com.example.aufgabe8_wadler.Tables.Project>();
        projects = projectRepository.findProjectByStudentID(user.getId());
        model.addAttribute("projects", projects);

        return "student";
    }

    @GetMapping("/findOpenProject")
    public String findOpenProject(Model model) {
        ArrayList<Project> projects = new ArrayList<com.example.aufgabe8_wadler.Tables.Project>();
        ArrayList<Project> help = new ArrayList<com.example.aufgabe8_wadler.Tables.Project>();
        for(int i = 1; i<=user.getLevel(); i++){
            help = projectRepository.findOpenProjectsByType(i);
            for (Project p:help){
                projects.add(p);
            }
        }
        model.addAttribute("projects", projects);

        ArrayList<Project> projectsStudent = new ArrayList<com.example.aufgabe8_wadler.Tables.Project>();
        projectsStudent = projectRepository.findProjectByStudentID(user.getId());
        boolean addNew = false;
        if(projectsStudent.size() < 1){
            addNew = true;
        }
        model.addAttribute("addNew", addNew);

        model.addAttribute("level", user.getLevel());

        return "OpenProjects";
    }

    @PostMapping("/signProject/{id}")
    public String SignProject(@PathVariable("id") long id, Model model) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Project:" + id));

        /**
         @TODO fix to update
         **/
        projectRepository.delete(projectRepository.getById(id));
        project.setStudent(user);
        projectRepository.save(project);

        return "redirect:/WelcomeStudent";
    }

    @PostMapping("/deleteProject/{id}")
    public String DeleteProject(@PathVariable("id") long id, Model model) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Project:" + id));


        if(user.getLevel() == 1 && project.getType() == 1) {
            userRepository.updateLevel(2, user.getId());
        }
        else if(user.getLevel() == 2 && project.getType() == 2) {
            userRepository.updateLevel(3, user.getId());
        }

        /**
         @TODO move Project not delete
         **/
        projectRepository.deleteById(id);
        //reload user
        user= userRepository.findUserByID(user.getId());

        return "redirect:/WelcomeStudent";
    }



    //ADMIN WELCOMEPAGE
    @GetMapping("/WelcomeAdmin")
    public String welcomeAdmin(Model model){
        model.addAttribute("user", user);

        ArrayList<Project> projects = new ArrayList<com.example.aufgabe8_wadler.Tables.Project>();
        projects = projectRepository.findProjectByLeaderID(user.getId());
        model.addAttribute("projects", projects);

        return "admin";
    }

    @GetMapping("/newStudent")
    public String NewStudent(Model model) {
        User newUser =new User();
        model.addAttribute("user", newUser);
        return "NewStudent";
    }

/*    @PostMapping("/saveStudent")
    public String saveStudent(@RequestParam String username, @RequestParam String lastName, @RequestParam String firstName, @RequestParam String password) {
        try {
            User student = new User(lastName, firstName, username, password, 3, 1);
            userService.addNewUser(student);
        }
        catch (IllegalStateException e){
            return "errors/saveStudent";
        }
        return "redirect:/WelcomeAdmin";
    }*/

    @PostMapping("/saveStudent")
    public String saveStudent(@Valid @ModelAttribute("user") User newUser){
        try {
            userService.addNewUser(newUser);
        }
        catch(IllegalStateException e){
            return "redirect:/newStudent";
        }


        return "redirect:/WelcomeAdmin";
    }

    @GetMapping("/newProject")
    public String NewProject(Model model) {
        Project newProject =new Project();
        model.addAttribute("project", newProject);
        return "NewProject";
    }

    @PostMapping("/saveProject")
    public String saveProject(@ModelAttribute("project") Project newProject){
        try {
            newProject.setLeader(user);
            projectRepository.save(newProject);
        }
        catch(IllegalStateException e){
            return "redirect:/newProject";
        }


        return "redirect:/WelcomeAdmin";
    }

    //ASSISTENT WELCOMEPAGE
    @GetMapping("/WelcomeAssistent")
    public String welcomeAssistent(Model model){
        model.addAttribute("user", user);

        ArrayList<Project> projects = new ArrayList<com.example.aufgabe8_wadler.Tables.Project>();
        projects = projectRepository.findProjectByLeaderID(user.getId());
        model.addAttribute("projects", projects);

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
        //userService.updateUser(userID, username);
    }

}
