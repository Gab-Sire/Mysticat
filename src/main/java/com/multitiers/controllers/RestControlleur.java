package com.multitiers.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.multitiers.domaine.User;
import com.multitiers.repository.UserRepository;


@RestController
public class RestControlleur {
	@Autowired
	private UserRepository userRepository;
	
    @GetMapping(value = "/monurl/{username}")
    public @ResponseBody User monUrl(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }
}
