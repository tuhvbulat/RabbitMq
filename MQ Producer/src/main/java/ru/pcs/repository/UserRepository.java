package ru.pcs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pcs.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
