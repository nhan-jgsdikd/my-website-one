package assgiment.ass.DAO;

import assgiment.ass.Model.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, Long> {
    User findByEmail(String email);
    Optional<User> findByUsername(String username);
    List<User> findByRole(String string);
}
