package com.sayuru.Bodima_backend.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private int id;
    private String username;
    private String mobile;
    private boolean isUser;

    public UserDTO(int id, String username, String mobile) {
    }
}
