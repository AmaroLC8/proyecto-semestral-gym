package com.grupito.auth_service.dto;

import com.grupito.auth_service.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private Long id;
    private String email;
    private String role;

    public static UserDTO fromModel(User user) {
        if (user == null) return null;
        return new UserDTO(user.getId(), user.getEmail(), user.getRole());
    }

    public User toModel() {
        User user = new User();
        user.setId(this.id);
        user.setEmail(this.email);
        user.setRole(this.role);
        return user;
    }
}
