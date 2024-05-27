package com.example.restapinote.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    public User findByUsername(String name) {
        Optional<User> user = repository.findById(name);
        if (user.isPresent()) {
            return user.get();
        }
            return null;
    }
    public void saveUser(User user) {
        repository.save(user);
    }
}
