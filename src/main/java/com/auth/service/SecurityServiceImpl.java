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
// sita klase yra atsakinga uz kontekstine viso userio informacija, tai tokenus, passwordus, etc
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // cia nenaudojame, nes controler "/authentication" metoda, jau apsirasem viename SecurityContextHolder.getContext().getAuthentication().getDetails().getName();
    // tad sio nebereikia, taciau negalime istrinti, nes implementuojam
    // Sie metodai yra musu aspirasyti SecurityService, kuris implementuoja SecurityService (si apsirase)
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

    // login'ui skirta validacijai. Naudojam UserController "/register" metoda, kad patikrinam ar irasytas useris atitinka prisijungimo duomenis
    // Info ateina is frontendo
    @Override
    public void login(String username, String passw) {
        // UserDetailsServiceImpl klaseje apsirasem kaip loadUserByUsername gauname pagal varda. tikrinam DB esanti objekta (atkoduota) su name, password, role
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        // headeriuose (puslapio) eina Tokenai, per kuriuos eina autorizacija (per Tokenus sifruojama informacija)
        // pasiimam userdetail name, pasq, ir userdetails (roliu seta) bei pridedam dar is webo gauta passw
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, passw, userDetails.getAuthorities());
        // autentikuojames. Tikrinam LoadUserByUsename( gaula per web ivesta username objekta[viduje username, password, role]) su ivestu per web passw, jei atitinka, tesiam
        // , kitu atveju error ir WebScurityConfiguration.java permeta mus i logina langa beui UserController.java permeta zinute error
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        // kai autentikavomes, tai patirkinam ar vartotojas autentikuotas
        if(usernamePasswordAuthenticationToken.isAuthenticated()){
            // jei autentikuotas, tai tikrinam
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }
}
