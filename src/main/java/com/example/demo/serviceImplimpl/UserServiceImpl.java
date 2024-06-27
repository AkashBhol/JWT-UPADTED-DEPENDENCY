package com.example.demo.serviceImplimpl;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Domain.User;
import com.example.demo.repo.UserRepository;
import com.example.demo.serviceImpl.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        log.info("Request to save user");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User result = userRepository.save(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public String findById(Long id) {
//        Authentication securityContext = SecurityContextHolder.getContext().getAuthentication();
//        String email  = securityContext.getName();
        User loggedInUser = getLoggedInUser();

        log.info("Request to find user:{}", id);
        Optional<User> result = userRepository.findById(id);
        if(loggedInUser.getEmail().equals(result.get().getEmail())){
            return result.toString();
        }
        log.info("result::");
        return "No value found";
    }

    private User getLoggedInUser() {
        Authentication securityContext = SecurityContextHolder.getContext().getAuthentication();
        String email  = securityContext.getName();
      return userRepository.findByEmail(email);
    }
}
