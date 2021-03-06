package com.auth.service;

import com.auth.model.User;

public interface UserRoleService {
    // isaugom vartotoja (naudojant spring Jpa)
    void save(User user);
    // gaunam vartotoja pagal username (jau is musu pasikurto UserRep, nes Jpa tokio nera)
    User findByUsername(String username);
}
