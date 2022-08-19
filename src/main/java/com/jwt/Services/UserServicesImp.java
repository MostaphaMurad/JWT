package com.jwt.Services;

import com.jwt.Models.AppRole;
import com.jwt.Models.AppUser;
import com.jwt.Repositories.RoleRepository;
import com.jwt.Repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service @Transactional
public class UserServicesImp  implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    public UserServicesImp(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public AppUser getUser(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public AppUser saveUser(AppUser appUser) {
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        String PlainPassword=appUser.getPassword();
        String encodedPassword=encoder.encode(PlainPassword);
        appUser.setPassword(encodedPassword);
        return userRepository.save(appUser);
    }

    @Override
    public AppRole saveRole(AppRole appRole) {
        return roleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {
        AppUser appUser=userRepository.findByUsername(userName);
        AppRole appRole=roleRepository.findByName(roleName);
        appUser.getRoles().add(appRole);

    }

    @Override
    public List<AppUser> getUsers() {
        return userRepository.findAll();
    }


}
