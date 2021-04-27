package com.project.project.dtos;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.project.project.models.Account;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TOMUserDetails implements UserDetails {

    private final int id;
    private final String username;
    private final String password;
    private final List<GrantedAuthority> authorityList;
    private final boolean activated;

    public TOMUserDetails(Account account) {
        this.id = account.getId();
        this.username = account.getUsername();
        this.password = account.getPassword();
        authorityList = new ArrayList<>();
        this.activated = account.isActivated();
        authorityList.add(new SimpleGrantedAuthority(account.getEmployee().getDepartment().getName()));
        if(activated)
            authorityList.add(new SimpleGrantedAuthority("ACTIVATED"));
        if(account.getTeamLeader() == null)
            authorityList.add(new SimpleGrantedAuthority("ADMIN"));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

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

    public boolean isActivated() {
        return activated;
    }

    public int getId() {
        return id;
    }
}