package com.example.aufgabe8_wadler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class Aufgabe8WadlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Aufgabe8WadlerApplication.class, args);
    }
}
