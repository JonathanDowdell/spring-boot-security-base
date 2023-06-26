package dev.springbootsecuritybase.server.auth.service;

import java.time.Instant;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.springbootsecuritybase.server.auth.model.dto.AuthDto;
import dev.springbootsecuritybase.server.auth.model.dto.AuthResponseDto;
import dev.springbootsecuritybase.server.exception.model.GlobalException;
import dev.springbootsecuritybase.server.token.model.common.TokenType;
import dev.springbootsecuritybase.server.token.model.entity.TokenEntity;
import dev.springbootsecuritybase.server.token.repository.TokenRespository;
import dev.springbootsecuritybase.server.token.service.IJwtService;
import dev.springbootsecuritybase.server.user.model.entity.RoleEntity;
import dev.springbootsecuritybase.server.user.model.entity.UserEntity;
import dev.springbootsecuritybase.server.user.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService implements IAuthService {

    private final UserRepository userRepository;
    private final TokenRespository tokenRespository;

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final IJwtService jwtService;

    @Override
    public AuthResponseDto signUp(AuthDto authDto) {
        try {
            UserEntity userEntity = new UserEntity();
            authDto.getUsername().ifPresent(userEntity::setUsername);
            userEntity.setEmail(authDto.getEmail());
            userEntity.setEncryptedPassword(passwordEncoder.encode(authDto.getPassword()));
            userEntity.setCreatedAt(Instant.now());
            userEntity.setEnabled(true);
            userEntity.setRole(RoleEntity.USER);
            // TODO: Fix orElse(null) to throw an exception
            userEntity.setUsername(authDto.getUsername().orElse(null));

            UserEntity savedUser = userRepository.save(userEntity);
            Map<String, Object> email = Map.of("email", savedUser.getEmail());
            String jwtToken = jwtService.generateToken(email, savedUser);
            String refreshToken = jwtService.generateRefreshToken(email, savedUser);
            
            AuthResponseDto authResponseDto = new AuthResponseDto();
            authResponseDto.setAccessToken(jwtToken);
            authResponseDto.setRefreshToken(refreshToken);

            saveToken(savedUser, jwtToken);
            return authResponseDto;
        } catch (DataIntegrityViolationException e) {
            throw new GlobalException("Email or Username already exists", HttpStatus.CONFLICT);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new GlobalException("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public AuthResponseDto signIn(AuthDto authDto) {

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authDto.getEmail(),
                        authDto.getPassword()));

        UserEntity userEntity = (UserEntity) authenticate.getPrincipal();
        // Map with username as key and userEntity.getEmail() as value

        Map<String, Object> email = Map.of("email", userEntity.getEmail());
        String jwtToken = jwtService.generateToken(email, userEntity);
        String refreshToken = jwtService.generateRefreshToken(email, userEntity);

        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setAccessToken(jwtToken);
        authResponseDto.setRefreshToken(refreshToken);

        saveToken(userEntity, jwtToken);
        return authResponseDto;
    }

    private void saveToken(UserEntity user, String token) {
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setToken(token);
        tokenEntity.setExpired(false);
        tokenEntity.setRevoked(false);
        tokenEntity.setType(TokenType.BEARER);
        tokenEntity.setUser(user);

        tokenRespository.save(tokenEntity);
    }

}
