package com.jwt.Security;

import com.jwt.Models.AppRole;
import com.jwt.Models.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails {
    private MyUserDetails userDetails;
    private AppUser user=new AppUser();
    public MyUserDetails(){}
    public MyUserDetails (MyUserDetails userDetails){
        this.userDetails=userDetails;
    }
    public MyUserDetails(AppUser appUser){
        this.user=appUser;
    }
    public String getUserName(){
        return user.getUsername();
    }
    public Collection<AppRole>getUserRoles(){
        return user.getRoles();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<AppRole>roles=new ArrayList<>();
        for(AppRole role:user.getRoles()){
            roles.add(role);
        }
        List<SimpleGrantedAuthority> authorities=new ArrayList<>();
        for(AppRole role:roles){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
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
}
