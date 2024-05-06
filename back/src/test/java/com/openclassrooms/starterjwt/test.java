package com.openclassrooms.starterjwt;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class test {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;
    @Test
    public void contextLoads() {
    }

    @Test
    void userSave(){
        User newUser = new User()
                .setEmail("newuser@example.com")
                .setFirstName("New")
                .setLastName("User")
                .setPassword("newpassword")
                .setAdmin(false);

        System.out.println("newUser : " +newUser);
        User savedUser = userRepository.save(newUser);
        System.out.println("savedUser : " +savedUser);
    }

    @Test
    void deleteAllUser(){
        userService.deleteAll();
    }

}
