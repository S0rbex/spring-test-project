package vitalitus.springtestproject.repository.role;

import org.springframework.data.jpa.repository.JpaRepository;
import vitalitus.springtestproject.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(Role.RoleName name);
}
