package dev.springbootsecuritybase.server.auth.service;

import dev.springbootsecuritybase.server.auth.model.dto.AuthDto;
import dev.springbootsecuritybase.server.auth.model.dto.AuthResponseDto;

public interface IAuthService {
    public AuthResponseDto signUp(AuthDto authenticationDto);
    public AuthResponseDto signIn(AuthDto authenticationDto);
}
