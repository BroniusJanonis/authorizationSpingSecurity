package com.auth.service;

import com.auth.model.User;
import com.auth.repository.RoleRep;
import com.auth.repository.UserRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

// cia apsirase mes patys pagal UserRoleService
@Service
public class UserRoleServiceImp implements UserRoleService {

    @Autowired
    UserRep userRep;
    @Autowired
    RoleRep roleRep;
    // Encoder'is, uzkoduoti password, jog duomenu bazeje nefiguruotu grynas tekstas. Ji jau apsirese esam WebSecyruttConfiguration
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        // kadangi esam bCryptPasswordEncoder apsirase WebSecyruttConfiguration klaseje, kuris priima user, tai uzkoduojame
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleRep.findAll()));  // duok visus ir prisiskiriam i HashSet
        // kadangi jau @autowirine esamt UserRep, tai ten yra spring JPA implementuoti, kuris duosa savo metoda save()
        userRep.save(user);
    }

    // sio metodo reikia, kad gautumem user pagal jo varda, naudojama UserValidator klaseje
    @Override
    public User findByUsername(String username) {
        return userRep.findByUsername(username);
    }
}
