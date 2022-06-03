/*
 * project management system
 * Spring-boot, Thymeleaf, MySQL
 * Author: Elisabeth Wadler
 * Last Change: 03.06.2022
 */

package com.example.aufgabe8_wadler.Config;

import com.example.aufgabe8_wadler.repository.ProjectRepository;
import com.example.aufgabe8_wadler.Tables.Leader;
import com.example.aufgabe8_wadler.Tables.Project;
import com.example.aufgabe8_wadler.Tables.Student;
import com.example.aufgabe8_wadler.Tables.User;
import com.example.aufgabe8_wadler.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository, ProjectRepository projectRepository){
        return args -> {
            User Maria = new Student( "Huber", "Maria", "huberma", "1234",  1);
            User Elli = new Leader( "Wadler", "Elisabeth", "wadlerel", "1234", 1, 2,2,1);
            User Thomas = new Leader( "Meier", "Thomas", "meierth", "1234", 2,3,3,2);
            userRepository.saveAll(List.of(Elli, Thomas, Maria));

            Project P1 = new Project("Project1", "Test1", LocalDate.now().plusDays(3), LocalDate.now(), (Leader) Elli );
            Project P2 = new Project("Project2", "Test2", LocalDate.now().plusDays(10), (Leader) Elli );
            Project P3 = new Project("Project3", "Test3", LocalDate.now().plusDays(5),  (Leader) Thomas);
            Project P4 = new Project("Project4",  LocalDate.now().plusDays(1), (Leader) Elli);
            Project P5 = new Project("Project5", "Test5", LocalDate.now().plusDays(25), LocalDate.now().plusDays(26), (Leader) Thomas );
            Project P6 = new Project("Project6",  LocalDate.now(), (Leader) Thomas );
            Project P7 = new Project("Project7", "Test7", LocalDate.now().plusDays(60), LocalDate.now().plusDays(64), (Leader) Elli);
            Project P8 = new Project("Project8", "Test8", LocalDate.now().plusDays(2),  (Leader) Thomas);


            P6.setStudent((Student) Maria);

            projectRepository.saveAll(List.of(P1,P2,P3, P4, P5, P6, P7, P8));
        };
    }
    @Bean(name = "dataSource")
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/test4");
        driverManagerDataSource.setUsername("admin");
        driverManagerDataSource.setPassword("test");
        return driverManagerDataSource;
    }

    //For customized error message
    @ControllerAdvice
    public class ErrorController {

        private static Logger logger = LoggerFactory.getLogger(ErrorController.class);

        @ExceptionHandler(Throwable.class)
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        public String exception(final Throwable throwable, final Model model) {
            logger.error("Exception during execution of SpringSecurity application", throwable);
            String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
            model.addAttribute("errorMessage", errorMessage);
            return "error";
        }

    }

}
