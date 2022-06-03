/*
 * project management system
 * Spring-boot, Thymeleaf, MySQL
 * Author: Elisabeth Wadler
 * Last Change: 03.06.2022
 */

package com.example.aufgabe8_wadler.Controller;

import com.example.aufgabe8_wadler.repository.LeaderRepository;
import com.example.aufgabe8_wadler.service.ProjectService;
import com.example.aufgabe8_wadler.Tables.User;
import com.example.aufgabe8_wadler.repository.ProjectRepository;
import com.example.aufgabe8_wadler.Tables.Leader;
import com.example.aufgabe8_wadler.Tables.Project;
import com.example.aufgabe8_wadler.Tables.Student;
import com.example.aufgabe8_wadler.service.UserService;
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
    ProjectService projectService;


    @Autowired
    LeaderController(ProjectRepository projectRepository,
                     ProjectService projectService,
                     LeaderRepository leaderRepository,
                     UserService userService,
                     StudentRepository studentRepository){
        this.projectRepository = projectRepository;
        this.leaderRepository = leaderRepository;
        this.userService = userService;
        this.studentRepository = studentRepository;
        this.projectService = projectService;
    }

    //Leader WELCOMEPAGE
    @GetMapping("/WelcomeLeader")
    public String welcomeLeader(Model model, Principal p) {

        findLeader(model, p);
        model.addAttribute("error", false);

        ArrayList<Project> projects;

        projects = projectRepository.findProjectByLeaderID(leaderRepository.findIdByUsername(p.getName()));
        model.addAttribute("projects", projects);

        return "leader";
    }

    private void findLeader(Model model, Principal p) {
        Optional<Leader> leader = leaderRepository.findLeaderByUsername(p.getName());

        int maxP=0;
        int maxB=0;
        int maxM=0;
        int role=0;

        if(leader.isPresent()) {
            maxP = leader.get().getMaxP();
            maxB = leader.get().getMaxB();
            maxM = leader.get().getMaxM();

            role = leader.get().getRole();
        }

        model.addAttribute("username", leader.get().getFirstName());
        model.addAttribute("username1", p.getName());
        model.addAttribute("maxP", maxP);
        model.addAttribute("maxB", maxB);
        model.addAttribute("maxM", maxM);
        model.addAttribute("role", role);
    }

    //Error if max value of projects is too low
    @GetMapping("/WelcomeLeader-error")
    public String welcomeLeaderError(Model model, Principal p) {

        findLeader(model, p);
        model.addAttribute("error", true);

        ArrayList<Project> projects;

        projects = projectRepository.findProjectByLeaderID(leaderRepository.findIdByUsername(p.getName()));
        model.addAttribute("projects", projects);

        return "leader";
    }

    //Delete Project
    @PostMapping("/deleteProject1/{id}")
    public String deleteProject(@PathVariable("id") long id) {
        projectService.deleteProject(id);

        return "redirect:/WelcomeLeader";
    }

    //Update Project
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
                .map(Date::valueOf);

        if(exam != null)
        Optional.ofNullable(LocalDate.parse(exam))
                .map(Date::valueOf);

        if(student != -1){
            project.setStudent(studentRepository.findUserById(student));
        }
        else
            project.setStudent(null);

        projectRepository.save(project);

        return "redirect:/WelcomeLeader";
    }

    //Add new User
    @GetMapping("/newUser")
    public String newStudent(Model model) {
        User newUser = new User();
        model.addAttribute("user", newUser);
        model.addAttribute("error", false);
        return "newUser";
    }
    @GetMapping("/newUser-error")
    public String newStudentError(Model model) {
        User newUser = new User();
        model.addAttribute("user", newUser);
        model.addAttribute("error", true);
        return "newUser";
    }

    @PostMapping("/saveUser")
    public String saveStudent(@ModelAttribute("user") User newUser, @RequestParam int type){
        try {
            userService.addNewUser(newUser, type);
        }
        catch(IllegalStateException e){
            return "redirect:/newUser-error";
        }
        return "redirect:/WelcomeLeader";
    }
    //Add new Project
    @GetMapping("/newProject")
    public String newProject(Model model) {

        Project newProject = new Project();
        model.addAttribute("project", newProject);
        model.addAttribute("error", false);
        return "newProject";
    }
    @GetMapping("/newProject-error")
    public String newProjectError(Model model) {

        Project newProject = new Project();
        model.addAttribute("project", newProject);
        model.addAttribute("error", true);
        return "newProject";
    }

    @PostMapping("/saveProject")
    public String saveProject(@ModelAttribute("project") Project newProject, Principal p) {
        Leader leader = leaderRepository.findLeaderByUsername(p.getName()).get();

        int size = projectRepository.findOpenProjectsByLeaderAndType(leader.getId(), newProject.getType()).size();
        switch (newProject.getType()) {
            case 1:
                if (size < leader.getMaxP()) {
                    newProject.setLeader(leader);
                    projectRepository.save(newProject);
                } else {
                    return "redirect:/newProject-error";
                }
                break;
            case 2:
                if (size < leader.getMaxB()) {
                    newProject.setLeader(leader);
                    projectRepository.save(newProject);
                } else {
                    return "redirect:/newProject-error";
                }
                break;
            case 3:
                if (size < leader.getMaxM()) {
                    newProject.setLeader(leader);
                    projectRepository.save(newProject);
                } else {
                    return "redirect:/newProject-error";
                }
                break;
        }

        return "redirect:/WelcomeLeader";
    }

    //Change settings
    @PostMapping("/saveSettings")
    public String saveSettings(Principal p, @RequestParam(required = false, name = "maxP") String maxP, @RequestParam(required = false, name = "maxB") String maxB, @RequestParam(required = false, name = "maxM") String maxM) {

        List<Project> projects;

        System.out.println(maxB);

        Leader leader = leaderRepository.findLeaderByUsername(p.getName()).get();

        if (maxP != null) {
            projects = projectRepository.findOpenProjectsByLeaderAndType(leader.getId(), 1);
            if (projects.size() <= Integer.parseInt(maxP))
                leader.setMaxP(Integer.parseInt(maxP));
            else {
                return "redirect:/WelcomeLeader-error";
            }
        }

        if (maxB != null) {
            projects = projectRepository.findOpenProjectsByLeaderAndType(leader.getId(), 2);
            if (projects.size() <= Integer.parseInt(maxB))
                leader.setMaxB(Integer.parseInt(maxB));
            else {
                return "redirect:/WelcomeLeader-error";
            }
        }

        if (maxM != null) {
            projects = projectRepository.findOpenProjectsByLeaderAndType(leader.getId(), 3);
            if (projects.size() <= Integer.parseInt(maxM))
                leader.setMaxM(Integer.parseInt(maxM));
            else {
                return "redirect:/WelcomeLeader-error";
            }
        }


        leaderRepository.save(leader);
        return "redirect:/WelcomeLeader";
    }


    //See all projects
    @GetMapping("/findProjects")
    public String findProjects(Model model) {
        List<Project> projects;
        projects = projectRepository.findAll();
        model.addAttribute("projects", projects);

        return "findProjects";
    }

}
