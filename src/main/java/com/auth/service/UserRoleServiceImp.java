package com.auth.service;

import com.auth.model.User;
import com.auth.repository.RoleRep;
import com.auth.repository.UserRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

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
        userRep.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRep.findByUsername(username);
    }
}
