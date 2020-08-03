package com.arczipt.teamup.security;

import com.arczipt.teamup.model.Privilege;
import com.arczipt.teamup.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TeamupUserDetails implements UserDetails {
    private User user;

    public TeamupUserDetails(User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for(Privilege privilege : user.getPrivileges()){
            authorities.add(new SimpleGrantedAuthority(privilege.getPrivilege()));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getHash();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public User getUser() {
        return user;
    }

    //TODO
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
