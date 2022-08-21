package com.jwt;

import com.jwt.Models.AppRole;
import com.jwt.Models.AppUser;
import com.jwt.Services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class JwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtApplication.class, args);
    }
/*    @Bean
    CommandLineRunner run(UserService userService){
        return args -> {
          userService.saveRole(new AppRole(null,"ROLE_USER"));
          userService.saveRole(new AppRole(null,"ROLE_ADMIN"));
          userService.saveRole(new AppRole(null,"ROLE_MANAGER"));
          userService.saveUser(new AppUser(null,"mostafa","mostafamurad","1234",new ArrayList<>()));
          userService.saveUser(new AppUser(null,"gad","gadmohamed","1234",new ArrayList<>()));
          userService.saveUser(new AppUser(null,"radw","radwsayed","1234",new ArrayList<>()));
          userService.addRoleToUser("mostafamurad","ROLE_USER");
          userService.addRoleToUser("mostafamurad","ROLE_MANAGER");
          userService.addRoleToUser("gadmohamed","ROLE_USER");
          userService.addRoleToUser("radwsayed","ROLE_ADMIN");

        };
    }*/

}
