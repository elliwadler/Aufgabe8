package com.example.aufgabe8_wadler.Controller;

import com.example.aufgabe8_wadler.LeaderRepository;
import com.example.aufgabe8_wadler.ProjectRepository;
import com.example.aufgabe8_wadler.StudentRepository;
import com.example.aufgabe8_wadler.Tables.Leader;
import com.example.aufgabe8_wadler.Tables.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;

@Controller
public class AdminController {
    ProjectRepository projectRepository;
    LeaderRepository leaderRepository;

    @Autowired
    AdminController(ProjectRepository projectRepository, LeaderRepository leaderRepository){
        this.projectRepository = projectRepository;
        this.leaderRepository = leaderRepository;
    }

    //Leader WELCOMEPAGE
    @GetMapping("/WelcomeLeader")
    public String welcomeLeader(Model model, Principal p) {
        Leader leader = leaderRepository.findUserByUsername(p.getName()).get();
        leader.getMaxB()
        model.addAttribute("username", leader);

        ArrayList<Project> projects = new ArrayList<Project>();
        projects = projectRepository.findProjectByLeaderID(leaderRepository.findIdByUsername(p.getName()));
        model.addAttribute("projects", projects);

        return "leader";
    }
}
