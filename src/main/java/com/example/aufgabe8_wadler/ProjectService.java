package com.example.aufgabe8_wadler;

import com.example.aufgabe8_wadler.Tables.Project;
import com.example.aufgabe8_wadler.Tables.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }
    public List<Project> all(){
        return projectRepository.findAll();
    }
    public void addNewProject(Project project){
        Optional<Project> projectOptional = projectRepository.findById(project.getId());
        if(projectOptional.isPresent()){
            throw new IllegalStateException("student has a active project!");
        }
        projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        boolean exists = projectRepository.existsById(id);
        if(!exists){
            throw new IllegalStateException(("student with id " +id+ "does not exist"));
        }
        projectRepository.deleteById(id);
    }

    public void updateUser(Long userID, String username) {
    }
}
