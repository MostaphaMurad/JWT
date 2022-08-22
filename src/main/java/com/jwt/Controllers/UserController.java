package com.jwt.Controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jwt.Models.AppRole;
import com.jwt.Models.AppUser;
import com.jwt.Security.MyUserDetails;
import com.jwt.Services.UserServicesImp;
import com.speedment.jpastreamer.application.JPAStreamer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserServicesImp userServicesImp;
    public UserController(UserServicesImp userServicesImp) {
        this.userServicesImp = userServicesImp;
    }
    @GetMapping("/users")
    public ResponseEntity<List<AppUser>>getUsers(){
        return new ResponseEntity<>(userServicesImp.getUsers(), HttpStatus.OK);
    }
    @PostMapping("/user/save")
    public ResponseEntity<AppUser>saveUser(@RequestBody AppUser user){
        URI uri=URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userServicesImp.saveUser(user));
    }
    @PostMapping("/role/save")
    public ResponseEntity<AppRole>saveUser(@RequestBody AppRole role){
        URI uri=URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userServicesImp.saveRole(role));
    }
    @PostMapping("/role/addtouser")
    public ResponseEntity<?>addRoleToUser(@RequestBody addRoleToUserForm form){
        userServicesImp.addRoleToUser(form.getUsername(),form.getRoleName());
        return ResponseEntity.ok().build();
    }
    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String authHeader=request.getHeader(AUTHORIZATION);
        if(authHeader!=null&&authHeader.startsWith("Mord "))
        {
            try{
                String refresh_token=authHeader.substring("Mord ".length());
                Algorithm algorithm=Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier= JWT.require(algorithm).build();
                DecodedJWT decodedJWT=verifier.verify(refresh_token);
                String username=decodedJWT.getSubject();
                AppUser user=userServicesImp.getUser(username);
                String access_token= JWT.create()
                        .withSubject(user.getUsername())//anything about logged used which must be unique
                        .withExpiresAt(new Date(System.currentTimeMillis()+10*60*1000))
                        .withIssuer(request.getRequestURI().toString())
                        .withClaim("roles",user.getRoles().stream().map(AppRole::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                response.setHeader("access_token",access_token);
                response.setHeader("refresh_token",refresh_token);
            }catch (Exception e){
                e.getMessage();
                response.setHeader("error",e.getMessage());
                response.sendError(FORBIDDEN.value());
            }
        }
        else
        {
            throw new RuntimeException("Refresh Token Is Missing");
        }
    }
}
class addRoleToUserForm{
    private String username;
    private String roleName;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
