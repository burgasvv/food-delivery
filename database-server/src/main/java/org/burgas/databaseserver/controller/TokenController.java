package org.burgas.databaseserver.controller;

import org.burgas.databaseserver.entity.Token;
import org.burgas.databaseserver.repository.TokenRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/tokens")
public class TokenController {

    private final TokenRepository tokenRepository;

    public TokenController(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @GetMapping(value = "/by-id")
    public @ResponseBody ResponseEntity<Token> getTokenById(@RequestParam Long tokenId) {
        return ResponseEntity
                .ok(tokenRepository.findById(tokenId).orElseGet(Token::new));
    }

    @GetMapping(value = "/by-value")
    public @ResponseBody ResponseEntity<Token> getTokenByValue(@RequestParam String value) {
        return ResponseEntity
                .ok(tokenRepository.findTokenByValue(value).orElseGet(Token::new));
    }

    @PostMapping(value = "/create")
    public @ResponseBody ResponseEntity<Token> createToken(@RequestBody Token token) {
        return ResponseEntity.ok(tokenRepository.save(token));
    }
}
