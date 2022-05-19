package com.example.aufgabe8_wadler;

import com.example.aufgabe8_wadler.Tables.Project;
import com.example.aufgabe8_wadler.Tables.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository, ProjectRepository projectRepository){
        return args -> {

            User Karl = new User( "Huber", "Karl", "huberka", "1234", 3);
            User Elli = new User( "Wadler", "Elisabeth", "wadlerel", "0903", 1);
            User Thomas = new User( "Meier", "Thomas", "meierth", "0502", 2);
            userRepository.saveAll(List.of(Karl,Elli, Thomas));


        };
    }
}
