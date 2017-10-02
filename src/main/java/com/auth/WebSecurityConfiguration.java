package com.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService userDetailsService;

    // jei autorizacija nepatvirtinta, tai kur numesti (i pagridnini ar error, ar registruotis per naujo)
    protected void configure(HttpSecurity http) throws Exception{
        // kuriuos psl praleidziame (nes pirma login langa praleidziame neautorizavus, gali buti nepasijunges
        // Taip pat ir JavaScript source ar pan)
        // /resources/** < /** reiskia, kad viska
        http.authorizeRequests().antMatchers("/resources/**", "/register").permitAll()
                .anyRequest().authenticated()
                // jei dar turim papildomu salygu, tai su and() pridedame
                .and()
                // login page
                .formLogin().loginPage("/login").permitAll()
                // ir tada issijungiam (?)
                .and().logout().permitAll();
        // praleidziame resourses visus, tada register langa, tada login ir tada nutraukiam viska
    }
}
