package com.auth.controller;

import com.auth.model.User;
import com.auth.service.SecurityService;
import com.auth.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private SecurityService securityService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model){
        // paduosime tuscia useri ir tuomet ji uzregistruosim
        model.addAttribute("userForm", new User());
        // ir graziname i jsp faila
        return "registration";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@ModelAttribute User userForm, Model model){
        // iraso nauja useri
        userRoleService.save(userForm);
        // tikrina sauguma
        securityService.login(userForm.getUsername(), userForm.getPasswordconfirm());
        return "redirect:/welcomemainpage";
    }

    @RequestMapping(value = "/welcomemainpage", method = RequestMethod.GET)
    public String welcome(Model model){
        return "welcommainpage";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error){
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Model model){
        return "redirect:/welcomemainpage";
    }

}
