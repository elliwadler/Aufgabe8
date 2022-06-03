/*
 * project management system
 * Spring-boot, Thymeleaf, MySQL
 * Author: Elisabeth Wadler
 * Last Change: 03.06.2022
 */

package com.example.aufgabe8_wadler.Tables;

import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String lastName;
    private String firstName;

    @NotNull(message = "username cannot be null!")
    @Column(unique = true)
    private String username;

    private String password;


    public User() {
    }

    public User(String lastName, String firstName, String username, String password) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.username = username;
        this.password = password;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}