package ru.pcs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pcs.model.Token;
import ru.pcs.repository.RedisTokenReposotory;

@RestController
@RequiredArgsConstructor
public class RedisController {

    private final RedisTokenReposotory redisTokenReposotory;

    @GetMapping("/token")
    public ResponseEntity getSucces(Token token) {
        int k = 0;
        redisTokenReposotory.save(token);
        return ResponseEntity.ok().build();
    }
}
