package com.example.aufgabe8_wadler.Controller;

import com.example.aufgabe8_wadler.repository.ProjectRepository;
import com.example.aufgabe8_wadler.repository.StudentRepository;
import com.example.aufgabe8_wadler.Tables.Project;
import com.example.aufgabe8_wadler.Tables.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.ArrayList;

@Controller
public class StudentController {

    ProjectRepository projectRepository;
    StudentRepository studentRepository;

    @Autowired
    StudentController(ProjectRepository projectRepository, StudentRepository studentRepository){
        this.projectRepository = projectRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping("/WelcomeStudent")
    public String welcomeStudent(Model model, Principal p) {
        Student student = studentRepository.findStudentByUsername(p.getName()).get();
        model.addAttribute("username",  student.getFirstName());
        model.addAttribute("username1", p.getName());

        ArrayList<Project> projects;
        projects = projectRepository.findProjectByStudentID(studentRepository.findStudentByUsername(p.getName()).get().getId());
        model.addAttribute("projects", projects);

        return "student";
    }

    @GetMapping("/findOpenProject")
    public String findOpenProject(Model model, Principal p) {
        ArrayList<Project> projects = new ArrayList<com.example.aufgabe8_wadler.Tables.Project>();
        ArrayList<Project> help;

        int level = studentRepository.findLevelByUsername(p.getName());

        for (int i = 1; i <= level; i++) {
            help = projectRepository.findOpenProjectsByType(i);
            for (Project pr : help) {
                projects.add(pr);
            }
        }
        model.addAttribute("projects", projects);

        ArrayList<Project> projectsStudent;
        projectsStudent = projectRepository.findProjectByStudentID(studentRepository.findIdByUsername(p.getName()));
        boolean addNew = false;
        if (projectsStudent.size() < 1) {
            addNew = true;
        }
        model.addAttribute("addNew", addNew);

        model.addAttribute("level", level);

        return "OpenProjects";
    }

    @PostMapping("/signProject/{id}")
    public String signProject(@PathVariable("id") long id, Model model, Principal p) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Project:" + id));

/**
 @TODO fix to update
 **/

        projectRepository.delete(projectRepository.getById(id));
        project.setStudent(studentRepository.findStudentByUsername(p.getName()).get());
        projectRepository.save(project);

        return "redirect:/WelcomeStudent";
    }

    @PostMapping("/deleteProject/{id}")
    public String doneProject(@PathVariable("id") long id, Model model, Principal p) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Project:" + id));

        int level = studentRepository.findLevelByUsername(p.getName());
        long ID = studentRepository.findIdByUsername(p.getName());

        if (level == 1 && project.getType() == 1) {
            studentRepository.updateLevel(2, ID);
        } else if (level == 2 && project.getType() == 2) {
            studentRepository.updateLevel(3, ID);
        }

        /**
         @TODO move Project not delete
         **/

        projectRepository.deleteById(id);

        return "redirect:/WelcomeStudent";
    }
}
