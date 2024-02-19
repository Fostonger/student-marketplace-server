package com.startit.authservice.controller;

import com.startit.authservice.exception.BadRegistrationDataException;
import com.startit.authservice.exception.UserExistsException;
import com.startit.authservice.service.AuthService;
import com.startit.authservice.transfer.Credentials;
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
            return ResponseEntity.ok(authService.register(request));
        } catch (BadRegistrationDataException e) {
            return ResponseEntity.badRequest().body("Данные для регистрации неправильны");
        } catch (UserExistsException e) {
            return ResponseEntity.status(409).body("Пользователь не существует");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Credentials request) {
        try {
            return ResponseEntity.ok(authService.login(request));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
