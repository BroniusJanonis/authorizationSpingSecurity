package com.auth.repository;

import com.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// User > klase, Long > kokis Id
public interface UserRep extends JpaRepository<User, Long>{
    // nes patys pasirasom, o nera tokio metodo Jpa
    User findByUsername(String username);
}

