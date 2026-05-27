package com.grupito.auth_service.service;

import com.grupito.auth_service.model.User;
import com.grupito.auth_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private HashService hashService;
    
    public String login(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user == null) return null;
        
        String hashedInput = hashService.sha1(password);
        if (!hashedInput.equals(user.getPassword())) return null;

        return jwtService.generateToken(email);
    }

    public String getRole(String email){
        User user = userRepository.findByEmail(email);
        return user != null ? user.getRole() : null;
    }

    public String register(String email, String password) {
        User existing = userRepository.findByEmail(email);
        if (existing != null) {
            return "Usuario ya existe!";
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(hashService.sha1(password));
        user.setRole("USER"); // Rol asignado por defecto

        userRepository.save(user);

        return "Usuario creado exitosamente!";
    }
}
