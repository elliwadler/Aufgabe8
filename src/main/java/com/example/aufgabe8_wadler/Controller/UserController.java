package com.example.aufgabe8_wadler.Controller;

import com.example.aufgabe8_wadler.LeaderRepository;
import com.example.aufgabe8_wadler.ProjectRepository;
import com.example.aufgabe8_wadler.StudentRepository;
import com.example.aufgabe8_wadler.Tables.Leader;
import com.example.aufgabe8_wadler.Tables.Project;
import com.example.aufgabe8_wadler.Tables.Student;
import com.example.aufgabe8_wadler.Tables.User;
import com.example.aufgabe8_wadler.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Controller
public class UserController {

    private final UserService userService;
    private final StudentRepository studentRepository;
    private final LeaderRepository leaderRepository;
    private final ProjectRepository projectRepository;
    private Student student;
    private Leader leader;
    private boolean check = true;

    @Autowired
    public UserController(UserService userService, LeaderRepository leaderRepository, StudentRepository studentRepository, ProjectRepository projectRepository) {
        this.userService = userService;
        this.studentRepository = studentRepository;
        this.leaderRepository = leaderRepository;
        this.projectRepository = projectRepository;
    }

    //LANDINGPAGE
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("check", check);
        return "hello";
    }

    @GetMapping("/logout")
    public String logout() {
        student = null;
        leader = null;
        return "hello";
    }

    //LOGIN INFORMATION
    @PostMapping("/test")
    public String loginUser(@RequestParam String username, @RequestParam String password, Model model) {

        Long id = studentRepository.authenticate(username, password);

        if (studentRepository.exists(id)) {
            student = studentRepository.findUserByID(id);
            return "redirect:/WelcomeStudent";
        } else {
            id = leaderRepository.authenticate(username, password);
        }
        if (leaderRepository.exists(id)) {
            leader = leaderRepository.findUserByID(id);
            return "redirect:/WelcomeLeader";
        } else {
            check = false;
            return "redirect:/";
        }
    }

    //STUDENT WELCOMEPAGE
    @GetMapping("/WelcomeStudent")
    public String welcomeStudent(Model model) {
        model.addAttribute("user", student);

        ArrayList<Project> projects = new ArrayList<com.example.aufgabe8_wadler.Tables.Project>();
        projects = projectRepository.findProjectByStudentID(student.getId());
        model.addAttribute("projects", projects);

        return "student";
    }

    @GetMapping("/findOpenProject")
    public String findOpenProject(Model model) {
        ArrayList<Project> projects = new ArrayList<com.example.aufgabe8_wadler.Tables.Project>();
        ArrayList<Project> help = new ArrayList<com.example.aufgabe8_wadler.Tables.Project>();

        for (int i = 1; i <= student.getLevel(); i++) {
            help = projectRepository.findOpenProjectsByType(i);
            for (Project p : help) {
                projects.add(p);
            }
        }
        model.addAttribute("projects", projects);

        ArrayList<Project> projectsStudent = new ArrayList<com.example.aufgabe8_wadler.Tables.Project>();
        projectsStudent = projectRepository.findProjectByStudentID(student.getId());
        boolean addNew = false;
        if (projectsStudent.size() < 1) {
            addNew = true;
        }
        model.addAttribute("addNew", addNew);

        model.addAttribute("level", student.getLevel());

        return "OpenProjects";
    }

    @PostMapping("/signProject/{id}")
    public String signProject(@PathVariable("id") long id, Model model) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Project:" + id));

/**
 @TODO fix to update
 **/

        projectRepository.delete(projectRepository.getById(id));
        project.setStudent(student);
        projectRepository.save(project);

        return "redirect:/WelcomeStudent";
    }
    @PostMapping("/deleteProject/{id}")
    public String doneProject(@PathVariable("id") long id, Model model) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Project:" + id));

        if (student.getLevel() == 1 && project.getType() == 1) {
            studentRepository.updateLevel(2, student.getId());
        } else if (student.getLevel() == 2 && project.getType() == 2) {
            studentRepository.updateLevel(3, student.getId());
        }

        /**
         @TODO move Project not delete
         **/

        projectRepository.deleteById(id);
        //reload user
        student = studentRepository.findUserByID(student.getId());

        return "redirect:/WelcomeStudent";
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
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("project", project);
        return "updateProject";
    }

    @PostMapping("/updateProject1/{id}")
    public String updateProject(@PathVariable("id") long id, @RequestParam String name, @RequestParam(required = false, name = "des") String des, @RequestParam String deadline, @RequestParam(required = false, name = "exam") String exam) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Project:" + id));

        project.setName(name);
        project.setDescription(des);


        project.setDeadline(LocalDate.parse(deadline));
        project.setExamDate(LocalDate.parse(exam));

        System.out.println(deadline);

        projectRepository.save(project);

        return "redirect:/WelcomeLeader";
    }

    //Leader WELCOMEPAGE
    @GetMapping("/WelcomeLeader")
    public String welcomeLeader(Model model) {
        model.addAttribute("user", leader);

        ArrayList<Project> projects = new ArrayList<com.example.aufgabe8_wadler.Tables.Project>();
        projects = projectRepository.findProjectByLeaderID(leader.getId());
        model.addAttribute("projects", projects);

        return "leader";
    }

    @GetMapping("/newStudent")
    public String newStudent(Model model) {
        Student newStudent = new Student();
        model.addAttribute("user", newStudent);
        return "NewStudent";
    }


    @PostMapping("/saveStudent")
    public String saveStudent(@RequestParam String username, @RequestParam String lastName, @RequestParam String firstName, @RequestParam String password) {
        try {
            Student student = new Student(lastName, firstName, username, password, 1);
            userService.addNewUser(student);
        }
        catch (IllegalStateException e){
            return "errors/saveStudent";
        }
        return "redirect:/WelcomeLeader";
    }

    @PostMapping("/saveSettings")
    public String saveSettings(@RequestParam(required = false, name = "maxP") String maxP, @RequestParam(required = false, name = "maxB") String maxB, @RequestParam(required = false, name = "maxM") String maxM) {
        List<Project> projects = new ArrayList<>();

        System.out.println(maxB);

        if (maxP != null) {
            projects = projectRepository.findOpenProjectsByLeaderAndType(leader.getId(), 1);
            if (projects.size() <= Integer.parseInt(maxP))
                leader.setMaxP(Integer.parseInt(maxP));
            else {
                throw new IllegalStateException("You cant change your maximal amount because you already have to much!");
            }
        }

        if (maxB != null) {
            projects = projectRepository.findOpenProjectsByLeaderAndType(leader.getId(), 2);
            if (projects.size() <= Integer.parseInt(maxB))
                leader.setMaxB(Integer.parseInt(maxB));
            else {
                throw new IllegalStateException("You cant change your maximal amount because you already have to much!");
            }
        }

        if (maxM != null) {
            projects = projectRepository.findOpenProjectsByLeaderAndType(leader.getId(), 3);
            if (projects.size() <= Integer.parseInt(maxM))
                leader.setMaxM(Integer.parseInt(maxM));
            else {
                throw new IllegalStateException("You cant change your maximal amount because you already have to much!");
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
    public String saveProject(@ModelAttribute("project") Project newProject) {

        int size = projectRepository.findOpenProjectsByLeaderAndType(leader.getId(), newProject.getType()).size();
        switch (newProject.getType()) {
            case 1:
                if (size < leader.getMaxP()) {
                    newProject.setLeader(leader);
                    projectRepository.save(newProject);
                } else {
                    throw new IllegalStateException("To much projects. Increase max value!");
                }
                break;
            case 2:
                if (size < leader.getMaxB()) {
                    newProject.setLeader(leader);
                    projectRepository.save(newProject);
                } else {
                    throw new IllegalStateException("To much projects. Increase max value!");
                }
                break;
            case 3:
                if (size < leader.getMaxM()) {
                    newProject.setLeader(leader);
                    projectRepository.save(newProject);
                } else {
                    throw new IllegalStateException("To much projects. Increase max value!");
                }
                break;
        }

        return "redirect:/WelcomeLeader";
    }

}
