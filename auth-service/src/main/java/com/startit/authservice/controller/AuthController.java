package com.startit.authservice.controller;

import com.startit.authservice.exception.BadRegistrationDataException;
import com.startit.authservice.exception.UserExistsException;
import com.startit.authservice.service.AuthService;
import com.startit.authservice.transfer.AuthResponse;
import com.startit.authservice.transfer.Credentials;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Object> registration(@RequestBody Credentials request) {
        try {
            AuthResponse answer = authService.register(request);
            return ResponseEntity.ok(answer);
        } catch (BadRegistrationDataException e) {
            return ResponseEntity.badRequest().body("Данные для регистрации неправильны");
        } catch (UserExistsException e) {
            return ResponseEntity.status(409).body("Пользователь существует");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Credentials request) {
        try {
            return ResponseEntity.ok(authService.login(request));
        } catch (BadRegistrationDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (UserExistsException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
