package com.auth.controller;

import com.auth.model.User;
import com.auth.service.SecurityService;
import com.auth.service.UserRoleService;
import com.auth.util.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Iterator;

@Controller
public class UserController {

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    UserValidator userValidator;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model){
        // paduosime tuscia useri ir tuomet ji uzregistruosim
        model.addAttribute("userForm", new User());
        // ir graziname i jsp faila
        return "registration";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    // "userForm" reikia, nes kitaip validation nesiuncia, nes mes registration.jsp esam pasisetine > modelAttribute="userForm"
    // BindingResult pasetins musu apsirasytu apsaugu error ir pagrazins i musu .jsp errors (errors laukeliai .jsp faile apsirasyti)
    public String register(@ModelAttribute ("userForm") User userForm, BindingResult bindresult, Model model){
        // tikrinam is gautu Jsp duomenu ar atitinka validacijas (ilgio, simboliu, etc)
        // bindresult tai yra, koki mes error gausime is userValidator ir koki persiusime i Jsp
        userValidator.validate(userForm, bindresult);
        if(bindresult.hasErrors()){
            return "registration";
        }
        // iraso nauja useri. Apsirasem UserRoleServiceImp klaseje save su roles parinkimu (siuo atveju visas pridedam apsirase, o reiketu tik viena) ir password encoding
        userRoleService.save(userForm);
        // tikrina sauguma
        securityService.login(userForm.getUsername(), userForm.getPasswordconfirm());
        return "redirect:/welcomemainpage";
    }

    // ir /welcomemainpage ir / (tuscias) numeta i welcompage
    @RequestMapping(value = "/welcomemainpage", method = RequestMethod.GET)
    public String welcome(Model model){
        return "welcommainpage";
    }

    // cia pirma jungiasi per controleri "login". get'a
    // kadangi nesi prisijunges, tai nera klaidos, todel permeta i loginMain.jsp
    //    jei ivedant username ir password (apsirase loginMain.jsp), jie atitinka musus spring security
    //    , tai webSecurityConfigurator > .formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/welcomemainpage")
    //    WebSecurityConfiguration tarp login ir success atliekam autorizacija, jei neveikia, tai bus error ir tuomet pagrazina i controleri
    //    Pagrazina i cotnroleri, kurio metodas ir value sutampa, turi buti "/login", kitu atveju, jei butu tarkim "/login1", tai nesugaudyti String error ir neperduotume i web
    //        kitu atveju, gavus ir spring security klaida (nes login spring suceess_, mums pagrazina error, kadangi tai nera lygu null, tai pagrazina i loginMain ir ismeta klaida
    //        , kuria atvaziduojam jau apsirasytam .jsp  (pirmu atveju tik pasijungus nemes klaidos, nes pirma karta uzkrauna puslapi per controleri, o jau jungiantis krauna "/login" per security pirma)
    // Svarbu
    @RequestMapping(value = {"/login", "/"}, method = RequestMethod.GET)
    public String login(Model model, String error){
        if(error!=null){
            model.addAttribute("error", "wrong username arba password");
        }
        return "loginMain";
    }

    @RequestMapping(value= "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response){
        // isgausim is konteksto security authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // jei authentication yra nelygus nuliui, tai padarom atjungima ir siunciame i logouta
        if(authentication != null) {
            // pasikuriame objekta, kuris handlina logouta ir logout() metode paduodame sesijos duomenis (jame turime paduoti requesta, response ir authentication < visu esamu sesiju)
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        // redirectins i login, jei suveiks logout (Spring'o logout)
        return "redirect:/login?logout";
    }

    // skirtas metodas, jog atvaizduotumem kontroleryje sesijos name
    @RequestMapping(value = "/authentication", method = RequestMethod.GET)
    public String username(Model model){
        // keisti galime vietoj .getName() > .getCredentials(), .getPrincipal(), .getDetails()
        Object name = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("authentication", name);
        return "welcome2";
    }


    // REIKIA PABAIGTI!!!!!!!!!!!!
    // gauname role ir pagal ja persiunciame ten, kur norime, prie kokio lango jungtis
    @RequestMapping(value = "/getrole", method = RequestMethod.GET)
    public String getrole(Model model, Authentication authentication){
        // gaunam autentifikacija
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        // per iteratoriu parsinam
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        // kol iteratorius nera tuscias (saugumo sumetimais ir geroji praktika)
        while(iterator.hasNext()){
            GrantedAuthority next = iterator.next();
            String role = next.getAuthority();
            if(role.equals("admin")){
                return "adminjsplangas";
            } else if(role.equals("teacher")){
                return "teacherlangas";
            }else if (role.equals("schoolchild")){
                return "schoolchildlangas";
            }
        }
        // jei neatitinka nei vienas, tai i logino langa pagrazina
        return "login";
    }

}
