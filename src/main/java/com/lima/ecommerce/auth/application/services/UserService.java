package com.lima.ecommerce.auth.application.services;

import com.lima.ecommerce.auth.application.dto.request.RegisterRequest;
import com.lima.ecommerce.auth.application.dto.response.UserResponse;
import com.lima.ecommerce.auth.domain.entities.User;
import com.lima.ecommerce.auth.domain.exceptions.EmailAlreadyRegisteredException;
import com.lima.ecommerce.auth.infrastructure.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse register(RegisterRequest request) {
        var normalizedEmail = request.email().strip().toLowerCase(Locale.ROOT);

        if(userRepository.existsByEmailIgnoreCase(normalizedEmail)) {
            throw new EmailAlreadyRegisteredException(normalizedEmail);
        }

        var passwordHash = passwordEncoder.encode(request.password());
        var user = User.newCustomer(request.name(), normalizedEmail, passwordHash);

        var savedUser = userRepository.save(user);
        return UserResponse.from(savedUser);
    }
}
