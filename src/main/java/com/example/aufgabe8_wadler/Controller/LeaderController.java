package com.example.aufgabe8_wadler.Controller;

import com.example.aufgabe8_wadler.LeaderRepository;
import com.example.aufgabe8_wadler.Tables.User;
import com.example.aufgabe8_wadler.repository.ProjectRepository;
import com.example.aufgabe8_wadler.Tables.Leader;
import com.example.aufgabe8_wadler.Tables.Project;
import com.example.aufgabe8_wadler.Tables.Student;
import com.example.aufgabe8_wadler.UserService;
import com.example.aufgabe8_wadler.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class LeaderController {
    ProjectRepository projectRepository;
    LeaderRepository leaderRepository;
    StudentRepository studentRepository;
    UserService userService;
    boolean error;
    boolean errornew;

    @Autowired
    LeaderController(ProjectRepository projectRepository, LeaderRepository leaderRepository, UserService userService, StudentRepository studentRepository){
        this.projectRepository = projectRepository;
        this.leaderRepository = leaderRepository;
        this.userService = userService;
        this.studentRepository = studentRepository;
        error =false;
        errornew=false;
    }

    //Leader WELCOMEPAGE
    @GetMapping("/WelcomeLeader")
    public String welcomeLeader(Model model, Principal p) {
        Optional<Leader> leader = leaderRepository.findLeaderByUsername(p.getName());
        int maxP =  leader.get().getMaxP();
        int maxB =  leader.get().getMaxB();
        int maxM =  leader.get().getMaxM();

        int role = leader.get().getRole();

        model.addAttribute("username", leader.get().getFirstName());
        model.addAttribute("username1", p.getName());
        model.addAttribute("maxP", maxP);
        model.addAttribute("maxB", maxB);
        model.addAttribute("maxM", maxM);
        model.addAttribute("role", role);
        model.addAttribute("error", error);

        ArrayList<Project> projects;

        projects = projectRepository.findProjectByLeaderID(leaderRepository.findIdByUsername(p.getName()));
        model.addAttribute("projects", projects);

        return "leader";
    }

    @PostMapping("/deleteProject1/{id}")
    public String deleteProject(@PathVariable("id") long id, Model model) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Project:" + id));

        projectRepository.deleteById(id);


        return "redirect:/WelcomeLeader";
    }

    @GetMapping("/updateProject/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Project:" + id));
        model.addAttribute("project", project);
        ArrayList<Student> s =studentRepository.findStudentWithoutProject(project.getType());

        if(project.getStudent() != null) {
            s.add((Student) project.getStudent());
        }
        model.addAttribute("students", s);
        return "updateProject";
    }

    @PostMapping("/updateProject1/{id}")
    public String updateProject(@PathVariable("id") long id,@RequestParam long student, @RequestParam String name, @RequestParam(required = false, name = "des") String des, @RequestParam String deadline, @RequestParam(required = false, name = "exam") String exam) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Project:" + id));

        project.setName(name);
        project.setDescription(des);

        System.out.println(student);

        Optional.ofNullable(LocalDate.parse(deadline))
                .map(Date::valueOf)
                .orElse(null);

        if(exam != null)
        Optional.ofNullable(LocalDate.parse(exam))
                .map(Date::valueOf)
                .orElse(null);

        if(student != -1){
            project.setStudent(studentRepository.findUserById(student));
        }
        else
            project.setStudent(null);

        projectRepository.save(project);

        return "redirect:/WelcomeLeader";
    }


    @GetMapping("/newUser")
    public String newStudent(Model model) {
        User newUser = new User();
        model.addAttribute("user", newUser);
        return "NewUser";
    }


    @PostMapping("/saveUser")
    public String saveStudent(@RequestParam int type, @RequestParam String username, @RequestParam String lastName, @RequestParam String firstName, @RequestParam String password) {
        try {
            User user = new User();
            if(type == 1) {
                user = new Student(lastName, firstName, username, password, 1);
            }
            else{
                user = new Leader(lastName, firstName, username, password, 10,10,10,2);
            }
                userService.addNewUser(user);
        }
        catch (IllegalStateException e){
            return "errors/saveStudent";
        }
        return "redirect:/WelcomeLeader";
    }

    @PostMapping("/saveSettings")
    public String saveSettings(Principal p, @RequestParam(required = false, name = "maxP") String maxP, @RequestParam(required = false, name = "maxB") String maxB, @RequestParam(required = false, name = "maxM") String maxM) {
        error = false;

        List<Project> projects = new ArrayList<>();

        System.out.println(maxB);

        Leader leader = leaderRepository.findLeaderByUsername(p.getName()).get();

        if (maxP != null) {
            projects = projectRepository.findOpenProjectsByLeaderAndType(leader.getId(), 1);
            if (projects.size() <= Integer.parseInt(maxP))
                leader.setMaxP(Integer.parseInt(maxP));
            else {
                error = true;
            }
        }

        if (maxB != null) {
            projects = projectRepository.findOpenProjectsByLeaderAndType(leader.getId(), 2);
            if (projects.size() <= Integer.parseInt(maxB))
                leader.setMaxB(Integer.parseInt(maxB));
            else {
                error = true;
            }
        }

        if (maxM != null) {
            projects = projectRepository.findOpenProjectsByLeaderAndType(leader.getId(), 3);
            if (projects.size() <= Integer.parseInt(maxM))
                leader.setMaxM(Integer.parseInt(maxM));
            else {
                error = true;
            }
        }


        leaderRepository.save(leader);
        return "redirect:/WelcomeLeader";
    }


    @GetMapping("/newProject")
    public String newProject(Model model) {
        Project newProject = new Project();
        model.addAttribute("project", newProject);
        return "NewProject";
    }

    @GetMapping("/findProjects")
    public String findProjects(Model model) {
        List<Project> projects = new ArrayList<>();
        projects = projectRepository.findAll();
        model.addAttribute("projects", projects);

        return "FindProjects";
    }

    @PostMapping("/saveProject")
    public String saveProject(@ModelAttribute("project") Project newProject, Principal p) {
        Leader leader = leaderRepository.findLeaderByUsername(p.getName()).get();

        errornew =false;
        int size = projectRepository.findOpenProjectsByLeaderAndType(leader.getId(), newProject.getType()).size();
        switch (newProject.getType()) {
            case 1:
                if (size < leader.getMaxP()) {
                    newProject.setLeader(leader);
                    projectRepository.save(newProject);
                } else {
                    errornew =true;
                }
                break;
            case 2:
                if (size < leader.getMaxB()) {
                    newProject.setLeader(leader);
                    projectRepository.save(newProject);
                } else {
                    errornew =true;
                }
                break;
            case 3:
                if (size < leader.getMaxM()) {
                    newProject.setLeader(leader);
                    projectRepository.save(newProject);
                } else {
                    errornew =true;
                }
                break;
        }

        return "redirect:/WelcomeLeader";
    }
}
