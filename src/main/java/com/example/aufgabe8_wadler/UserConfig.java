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
            Project P2 = new Project("Project2", "blbl", LocalDate.now(), LocalDate.now(), Elli,2 );
            Project P3 = new Project("Project3", "Aquarium sauber machen", LocalDate.now(), LocalDate.now(), Maria, Thomas,3 );
            Project P4 = new Project("Project4", "af", LocalDate.now(), LocalDate.now(), Elli,1 );
            Project P5 = new Project("Project5", "afds", LocalDate.now(), LocalDate.now(), Thomas,2 );
            Project P6 = new Project("Project6", "ka", LocalDate.now(), LocalDate.now(), Thomas,3 );
            Project P7 = new Project("Project7", "af", LocalDate.now(), LocalDate.now(), Elli,3);
            Project P8 = new Project("Project8", "afds", LocalDate.now(), LocalDate.now(), Thomas,2 );


            projectRepository.saveAll(List.of(P1,P2,P3, P4, P5, P6, P7, P8));


        };
    }
}
