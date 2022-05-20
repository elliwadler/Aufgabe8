package com.example.aufgabe8_wadler.Tables;

import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class User {

    @Id
    @SequenceGenerator(
            name="user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    private String lastName;
    private String firstName;
    private String username;
    private String password;
    private int role;
    private int level;
    private int maxP;
    private int maxB;
    private int maxM;

    @OneToOne(mappedBy = "student", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Project project;

    @OneToMany(mappedBy = "leader", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Project> projects1;

    public User() {
    }

    public User(String lastName, String firstName, String username, String password, int role, int level) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.username = username;
        this.password = password;
        this.role = role;
        this.level = level;
    }

    public User(String lastName, String firstName, String username, String password, int role, int maxP, int maxB, int maxM) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.username = username;
        this.password = password;
        this.role = role;
        this.maxP=maxP;
        this.maxB=maxB;
        this.maxM =maxM;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", level=" + level +
                '}';
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if(this.role == 3) {
            this.level = level;
        }
    }

    public int getMaxP() {
        return maxP;
    }

    public void setMaxP(int maxP) {
        if(this.role != 1) {
            this.maxP = maxP;
        }
    }

    public int getMaxB() {
        return maxB;
    }

    public void setMaxB(int maxB) {
        if(this.role != 3) {
            this.maxB = maxB;
        }
    }

    public int getMaxM() {
        return maxM;
    }

    public void setMaxM(int maxM) {
        if(this.role != 3) {
            this.maxM = maxM;
        }
    }
}
