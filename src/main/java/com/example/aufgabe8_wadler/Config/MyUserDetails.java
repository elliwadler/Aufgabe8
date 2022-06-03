/*
 * project management system
 * Spring-boot, Thymeleaf, MySQL
 * Author: Elisabeth Wadler
 * Last Change: 03.06.2022
 */

package com.example.aufgabe8_wadler.Config;

import com.example.aufgabe8_wadler.Tables.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;


public class MyUserDetails implements UserDetails {

    private String userName;
    private String password;

    public MyUserDetails(User user){
        this.userName = user.getUsername();
        this.password = user.getPassword();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("nix"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
