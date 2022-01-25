package ru.pcs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pcs.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User getUserById(Long id);
}
