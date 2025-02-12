package com.blazemhan.taskmanagerapp.controller;

import com.blazemhan.taskmanagerapp.model.User;
import com.blazemhan.taskmanagerapp.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    public final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
        @PostMapping("/register")
        public ResponseEntity<String> register(@RequestBody User user){
            return ResponseEntity.ok(authService.register(user));
        }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User request){
        return ResponseEntity.ok(authService.verify(request));
    }



}
