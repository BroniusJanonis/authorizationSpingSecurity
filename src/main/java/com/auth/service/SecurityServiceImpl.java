package com.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

// sio metodai bus pasiimti is SpringSecurity, reikia servisu klasese @Service anotacijos
@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public String findLogginUsername() {
        // patikrinsim ar sis vartotojas yra prisijunges
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        String username = null;
        // patikrinam ar Objektas userDetails yra priklauso tam paciam objektui UserDetails (jau yra tokis interface), kad butu tas pats (saugumas)
        if(userDetails instanceof UserDetails){
            username = ((UserDetails) userDetails).getUsername();
        }
        return username;
    }

    @Override
    public void login(String username, String passw) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        // headeriuose (puslapio) eina Tokenai, per kuriuos eina autorizacija (per Tokenus sifruojama informacija)
        // pasiimam userdetail name, pasq, ir userdetails (roliu seta) ?
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, passw, userDetails.getAuthorities());
        // autentikuojames
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        // kai autentikavomes, tai patirkinam ar vartotojas autentikuotas
        if(usernamePasswordAuthenticationToken.isAuthenticated()){
            // jei autentikuotas, tai tikrinam
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }
}
