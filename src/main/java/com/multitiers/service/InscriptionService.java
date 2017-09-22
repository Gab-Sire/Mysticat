package com.multitiers.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multitiers.ProjetMultitiersApplication;
import com.multitiers.domaine.User;
import com.multitiers.repository.UserRepository;
import com.multitiers.util.ConnectionUtils;

@Service
public class InscriptionService {
    @Autowired
    private UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(ProjetMultitiersApplication.class);
    
    @Transactional
    public void inscription(String username, String password) {
    	String salt = ConnectionUtils.generateSalt();
    	String hashedPassword = ConnectionUtils.hashPassword(password, salt);
        userRepository.save(new User(username, hashedPassword, salt));
    }
    
    @Transactional
    public void peuplement() {
    	String salt = "salt";
    	String password = "myboy";
    	String hashedPassword = ConnectionUtils.hashPassword(password, salt);
        userRepository.save(new User("Chat1", hashedPassword, salt));
    }
}
