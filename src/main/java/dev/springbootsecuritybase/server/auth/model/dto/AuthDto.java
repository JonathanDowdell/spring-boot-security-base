package dev.springbootsecuritybase.server.auth.model.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthDto {
    private String email;
    private Optional<String> username;
    private String password;
}
