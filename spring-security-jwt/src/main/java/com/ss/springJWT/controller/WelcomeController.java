package com.ss.springJWT.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ss.springJWT.entity.AuthRequest;
import com.ss.springJWT.entity.User;
import com.ss.springJWT.repository.UserRepository;
import com.ss.springJWT.util.JwtUtil;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class WelcomeController {

	@Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
	
    @GetMapping("/hello")
    public String welcome() {
        return "Welcome to java JWT !!";
    }
    
    @GetMapping("/allowme")
    public String allowedMethod() {
    	System.out.println("Heloooooooooo");
        return "This is allowed !!";
    }
    
    @PostMapping("/userDetails")
    public User getDetailsFromUserName(@RequestBody User userdetails) {
    	System.out.println("Incoming user:"+userdetails.getUserName());
    	User user = repository.findByUserName(userdetails.getUserName());
    	System.out.println("The UserName:"+user.getUserName()+" Email:"+user.getEmail());
    	return user;
    }
    
    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("inavalid username/password");
        }
        return jwtUtil.generateToken(authRequest.getUserName());
    }

}
