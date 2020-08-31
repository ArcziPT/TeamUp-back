package com.arczipt.teamup.service;

import com.arczipt.teamup.model.Privilege;
import com.arczipt.teamup.model.User;
import com.arczipt.teamup.repo.UserRepository;
import com.arczipt.teamup.security.TeamupUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class TeamupUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public TeamupUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public TeamupUserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(s);

        //prefetch authorities to avoid later lazily initialization error
        List<GrantedAuthority> authorities = buildUserAuthority(user);

        return new TeamupUserDetails(user);
    }

    private List<GrantedAuthority> buildUserAuthority(User user) {

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for(Privilege privilege : user.getPrivileges()){
            authorities.add(new SimpleGrantedAuthority(privilege.getPrivilege()));
        }

        return authorities;
    }
}
