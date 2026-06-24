package com.grupito.auth_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupito.auth_service.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public java.util.Map<String, String> login(@RequestBody java.util.Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");
        String token = userService.login(email, password);

        java.util.Map<String, String> resp = new java.util.HashMap<>();
        if (token == null) {
            resp.put("status", "error");
            resp.put("token", "");
        } else {
            resp.put("status", "ok");
            resp.put("token", token);
        }
        return resp;
    }

    @PostMapping("/register")
    public java.util.Map<String, String> register(@RequestBody java.util.Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");
        String resultado = userService.register(email, password);

        java.util.Map<String, String> resp = new java.util.HashMap<>();
        resp.put("message", resultado);
        return resp;
    }
}