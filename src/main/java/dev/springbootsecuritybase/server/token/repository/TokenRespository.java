package dev.springbootsecuritybase.server.token.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.springbootsecuritybase.server.token.model.entity.TokenEntity;

@Repository
public interface TokenRespository extends JpaRepository<TokenEntity, Long> {
    Optional<TokenEntity> findByToken(String token);
}
