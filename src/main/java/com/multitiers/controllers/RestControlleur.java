package com.multitiers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.multitiers.domaine.User;
import com.multitiers.repository.UserRepository;
import com.multitiers.util.ConnectionUtils;


@RestController
public class RestControlleur {
	@Autowired
	private UserRepository userRepository;
	
    @GetMapping(value = "/getUserByName/{username}")
    public @ResponseBody User getUserByName(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }
    
    @GetMapping(value = "/attemptConnection/{username}/{password}")
    public @ResponseBody Boolean attemptConnection(@PathVariable String username, @PathVariable String password) {
        User user = userRepository.findByUsername(username);
        return ConnectionUtils.hashPassword(password).equals(user.getPasswordHash());
    }
    
}
