package anonapp.data.repo;

import anonapp.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Orlov Diga
 */
@Repository
public interface UserRepo extends CrudRepository<User, Long> {

    public User findByUsername(String username);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN 'true' ELSE 'false' END FROM User u WHERE u.login = ?1")
    public boolean existsByLogin(String login);
}
