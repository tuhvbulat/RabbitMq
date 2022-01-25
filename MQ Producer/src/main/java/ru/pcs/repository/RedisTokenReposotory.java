package ru.pcs.repository;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import ru.pcs.model.Token;

public interface RedisTokenReposotory extends KeyValueRepository<Token,Long> {
}
