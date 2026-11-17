package com.lima.ecommerce.auth.infrastructure.web;

import com.lima.ecommerce.auth.application.dto.request.RegisterRequest;
import com.lima.ecommerce.auth.application.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        var response = userService.register(request);

        return ResponseEntity
                .created(URI.create("/api/v1/auth/register"))
                .body(response);
    }
}
