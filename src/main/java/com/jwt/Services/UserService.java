package com.jwt.Services;

import com.jwt.Models.AppRole;
import com.jwt.Models.AppUser;

import java.util.List;

public interface UserService {
    public abstract AppUser getUser(String userName);
    public  abstract AppUser saveUser(AppUser appUser);
    public abstract AppRole saveRole(AppRole appRole);
    public  abstract void addRoleToUser(String userName,String roleName);
    public abstract List<AppUser> getUsers();

}
