package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public void delete(Long id) {
        this.userRepository.deleteById(id);
    }

    public void deleteAll(){
        this.userRepository.deleteAll();
    }

    public User findById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    @Transactional
    public User save(User user) {
        System.out.println("save service user : "+user);
        return userRepository.save(user);
    }

    @Transactional
    public User addUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        User user = userMapper.toEntity(userDto);
        return userRepository.save(user);
    }
}
