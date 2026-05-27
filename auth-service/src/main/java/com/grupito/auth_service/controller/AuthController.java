package com.grupito.auth_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.grupito.auth_service.service.UserService;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");
        String token = userService.login(email, password);

        Map<String, String> resp = new HashMap<>();
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
    public Map<String, String> register(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");
        String resultado = userService.register(email, password);

        Map<String, String> resp = new HashMap<>();
        resp.put("message", resultado);
        return resp;
    }
}