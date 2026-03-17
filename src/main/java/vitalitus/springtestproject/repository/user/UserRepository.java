package vitalitus.springtestproject.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import vitalitus.springtestproject.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
