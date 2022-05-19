package com.example.aufgabe8_wadler;

import com.example.aufgabe8_wadler.Tables.Project;
import com.example.aufgabe8_wadler.Tables.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository, ProjectRepository projectRepository){
        return args -> {

            User Karl = new User( "Huber", "Karl", "huberka", "1234", 3, 1);
            User Maria = new User( "Wadler", "Maria", "wadlerma", "1234", 3, 3);
            User Elli = new User( "Wadler", "Elisabeth", "wadlerel", "0903", 1, 2,2,2);
            User Thomas = new User( "Meier", "Thomas", "meierth", "0502", 2,3,3,3);
            userRepository.saveAll(List.of(Karl,Elli, Thomas, Maria));

            Project P1 = new Project("Project1", "blbl", LocalDate.now(), LocalDate.now(), Karl, Elli,1 );
            Project P2 = new Project("Project2", "blbl", LocalDate.now(), LocalDate.now(), Karl, Elli,2 );
            Project P3 = new Project("Project3", "Aquarium sauber machen", LocalDate.now(), LocalDate.now(), Maria, Thomas,3 );

            projectRepository.saveAll(List.of(P1,P2,P3));


        };
    }
}
