package com.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    // nezinau, del ko Autowired neveikia, tai galima per Bean tiesiai is konfiguracijos pakurti nauja objekta encoderio
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    };

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
                // .successForwardUrl("/welcommainpage")  < testuojam
                // ir tada issijungiam (?)
                .and().logout().permitAll();
        // praleidziame resourses visus, tada register langa, tada login ir tada nutraukiam viska
    }

    // uzkoduojame (?)
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}
