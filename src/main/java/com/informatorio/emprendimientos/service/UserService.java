package com.informatorio.emprendimientos.service;

import com.informatorio.emprendimientos.entity.User;
import com.informatorio.emprendimientos.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User update(User user, User user2){
        user.setName(user2.getName());
        user.setLastName(user2.getLastName());
        user.setCity(user2.getCity());
        user.setProvince(user2.getProvince());
        user.setCountry(user2.getCountry());
        user.setUserType(user.getUserType());
        return user;
    }
}