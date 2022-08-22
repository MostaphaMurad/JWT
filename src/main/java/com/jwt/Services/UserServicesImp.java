package com.jwt.Services;

import com.jwt.Models.AppRole;
import com.jwt.Models.AppUser;
import com.jwt.Models.AppUser$;
import com.jwt.Models.Product;
import com.jwt.Repositories.RoleRepository;
import com.jwt.Repositories.UserRepository;
import com.speedment.jpastreamer.application.JPAStreamer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service @Transactional
public class UserServicesImp  implements UserService {
    @Autowired private JPAStreamer jpaStreamer;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    public UserServicesImp(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public AppUser getUser(String userName) {
        return (AppUser) jpaStreamer.stream(AppUser.class).filter(AppUser$.username.equal(userName));
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
    @Transactional(readOnly = true)
    public List<AppUser> getUsers() {
        System.out.println("Processing stream");
        List<AppUser>userList =new ArrayList<>();
        try (Stream<AppUser> user = userRepository.Users()) {
            user.forEach(item -> {
                System.out.println(item.getName());
                userList.add(item);
            });
        }
        return userList;
    }


}
