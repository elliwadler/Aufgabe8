/*
 * project management system
 * Spring-boot, Thymeleaf, MySQL
 * Author: Elisabeth Wadler
 * Last Change: 03.06.2022
 */

package com.example.aufgabe8_wadler.service;

import com.example.aufgabe8_wadler.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }


    public void deleteProject(Long id) {
        boolean exists = projectRepository.existsById(id);
        if(!exists){
            throw new IllegalStateException(("student with id " +id+ "does not exist"));
        }
        projectRepository.deleteById(id);
    }

}
