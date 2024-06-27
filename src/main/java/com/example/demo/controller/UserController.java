package com.example.demo.controller;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Domain.User;
import com.example.demo.serviceImpl.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<User> create(@RequestBody User user) throws URISyntaxException {
        log.info("Rest request to create user");
        User result = userService.save(user);
        return ResponseEntity
                .created(new URI("/api/users" + result.getId()))
                .body(result);
    }

    @GetMapping("/users-id/{id}")
    public ResponseEntity<String> getUser(@PathVariable Long id) {
        log.info("Rest request to get user : {}", id);
        String byId = userService.findById(id);
        log.info("result {}",byId);
        return ResponseEntity
                .ok()
                .body(byId);
    }


}
