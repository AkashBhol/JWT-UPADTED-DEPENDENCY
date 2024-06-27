package com.example.demo.serviceImpl;

import java.util.List;
import java.util.Optional;

import com.example.demo.Domain.User;

public interface UserService {

    User save(User user);

    List<User> findAll();

   String findById(Long id);
}
