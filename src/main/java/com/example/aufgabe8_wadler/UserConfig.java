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

            User Karl = new User( "Huber", "Karl", "huberka", "1234", 1);
            User Maria = new User( "Wadler", "Maria", "wadlerma", "1234",  3);
            User Elli = new User( "Wadler", "Elisabeth", "wadlerel", "0903", 1, 2,2,2);
            User Thomas = new User( "Meier", "Thomas", "meierth", "0502", 2,3,3,3);
            userRepository.saveAll(List.of(Karl,Elli, Thomas, Maria));

            Project P1 = new Project("Project1", "Test1", LocalDate.now().plusDays(3), LocalDate.now(), Elli );
            Project P2 = new Project("Project2", "Test2", LocalDate.now().plusDays(10), Elli );
            Project P3 = new Project("Project3", "Test3", LocalDate.now().plusDays(5),  Thomas);
            Project P4 = new Project("Project4",  LocalDate.now().plusDays(1), Elli);
            Project P5 = new Project("Project5", "Test5", LocalDate.now().plusDays(25), LocalDate.now().plusDays(26), Thomas );
            Project P6 = new Project("Project6",  LocalDate.now(), Thomas );
            Project P7 = new Project("Project7", "Test7", LocalDate.now().plusDays(60), LocalDate.now().plusDays(64), Elli);
            Project P8 = new Project("Project8", "Test8", LocalDate.now().plusDays(2),  Thomas);


            P6.setStudent(Karl);
            P3.setStudent(Maria);

            projectRepository.saveAll(List.of(P1,P2,P3, P4, P5, P6, P7, P8));


        };
    }
}
