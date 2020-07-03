package engine.controller;

import engine.db.User;
import engine.db.UserRepository;
import engine.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @RequestMapping("/register")
    public void registerUser(@Valid @RequestBody UserDto user) {
        if (repository.findByEmail(user.getEmail()).isPresent())
            throw new UserAlreadyExistsException(user.getEmail());
        repository.save(new User(null, user.getEmail(), passwordEncoder.encode(user.getPassword())));
    }
}
