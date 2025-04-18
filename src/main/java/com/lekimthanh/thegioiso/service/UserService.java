package com.lekimthanh.thegioiso.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lekimthanh.thegioiso.domain.User;
import com.lekimthanh.thegioiso.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User creatUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email đã tồn tại");
        }
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(Long id, User updatedUser) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            user.setAddress(updatedUser.getAddress());
            user.setPhone(updatedUser.getPhone());
            user.setAvatar(updatedUser.getAvatar());
            user.setRole(updatedUser.getRole());
            return userRepository.save(user);
        } else {
            throw new RuntimeException("Người dùng không tồn tại");
        }
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Người dùng không tồn tại");
        }
        userRepository.deleteById(id);
    }
}
