package com.auth.service;

import com.auth.model.Role;
import com.auth.model.User;
import com.auth.repository.UserRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRep userRep;

    @Override
    // Transaction readOnly, kad nerakintumem tranasactcijos (nesupratau iki galo)
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRep.findByUsername(s);

        // Autorizacija
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        // pereinam per visas roles ir sudedam visas roles i sita seta. Pagal visu useriu roles
        for(Role role : user.getRoles()){
            // GrantedAuthority yra autorizacija, o SimpleGrantedAuthority yra vienas is autorizacijos tipu, mes tai pasirenkame
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        // new org.springframework.security.core.userdetails. User, tai, koki tipa pagrazinsiu ir User() viduje pagrazinam autorizacija
        // pagrazinam username, password ir autorizacija
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
