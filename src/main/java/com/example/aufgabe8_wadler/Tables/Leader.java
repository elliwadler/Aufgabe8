/*
 * project management system
 * Spring-boot, Thymeleaf, MySQL
 * Author: Elisabeth Wadler
 * Last Change: 03.06.2022
 */

package com.example.aufgabe8_wadler.Tables;

import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Table
public class Leader extends User {

    @OneToMany(mappedBy = "leader", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Project> projects1;

    private int maxP;
    private int maxB;
    private int maxM;

    private int role;

    public Leader() {
    }

    public Leader(String lastName, String firstName, String username, String password, int maxP, int maxB, int maxM, int role) {
        super(lastName, firstName, username, password);
        this.maxP = maxP;
        this.maxB = maxB;
        this.maxM = maxM;
        this.role = role;
    }
    public Leader(String lastName, String firstName, String username, String password) {
        super(lastName, firstName, username, password);
        this.maxP = 10;
        this.maxB = 10;
        this.maxM = 10;
        this.role = 2;
    }


    @Override
    public String toString() {
        return "Leader{" +
                "projects1=" + projects1 +
                ", maxP=" + maxP +
                ", maxB=" + maxB +
                ", maxM=" + maxM +
                ", role=" + role +
                '}';
    }

    public Set<Project> getProjects1() {
        return projects1;
    }

    public void setProjects1(Set<Project> projects1) {
        this.projects1 = projects1;
    }

    public int getMaxP() {
        return maxP;
    }

    public void setMaxP(int maxP) {
        this.maxP = maxP;
    }

    public int getMaxB() {
        return maxB;
    }

    public void setMaxB(int maxB) {
        this.maxB = maxB;
    }

    public int getMaxM() {
        return maxM;
    }

    public void setMaxM(int maxM) {
        this.maxM = maxM;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
