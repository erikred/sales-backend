package pe.arguz.sales.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.arguz.sales.model.User;

public interface IUserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
