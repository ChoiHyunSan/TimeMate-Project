package com.ll.timemateproject.domain.user;

import com.ll.timemateproject.api.v1.dto.response.login.SignupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public SignupResponse signUp(String username, String email, String password, String passwordCheck) {
        if(userRepository.findByUsername(username).isPresent()) {
            throw new BadCredentialsException("Username is already in use");
        };
        if(!password.equals(passwordCheck)) {
            throw new BadCredentialsException("Passwords do not match");
        }

        User user = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();
        userRepository.save(user);

        return SignupResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
