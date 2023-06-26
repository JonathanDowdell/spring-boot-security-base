package dev.springbootsecuritybase.server.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.springbootsecuritybase.server.auth.model.dto.AuthDto;
import dev.springbootsecuritybase.server.auth.model.dto.AuthResponseDto;
import dev.springbootsecuritybase.server.auth.service.IAuthService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
    
    private final IAuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDto> signup(@RequestBody AuthDto authenticationDto) {
        AuthResponseDto signUp = authService.signUp(authenticationDto);
        return ResponseEntity.ok(signUp);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponseDto> signin(@RequestBody AuthDto authenticationDto) {
        AuthResponseDto signIn = authService.signIn(authenticationDto);
        return ResponseEntity.ok(signIn);
    }

}
