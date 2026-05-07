package com.example.essayfeedback.admin.dto;

import com.example.essayfeedback.auth.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String username;
    private String role;
    
    @JsonProperty("isFrozen")
    private boolean isFrozen;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.role = user.getRole();
        this.isFrozen = user.isFrozen();
    }
}
