package com.auth.service;

import com.auth.model.User;
import com.auth.repository.RoleRep;
import com.auth.repository.UserRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class UserRoleServiceImp implements UserRoleService {

    @Autowired
    UserRep userRep;
    @Autowired
    RoleRep roleRep;

    @Override
    public void save(User user) {
        user.setPassword(user.getPasswordconfirm()); // reikia sifruoti
        user.setRoles(new HashSet<>(roleRep.findAll()));  // duok visus ir prisiskiriam i HashSet
        userRep.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRep.findByUsername(username);
    }
}
