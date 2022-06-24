package fel.cvut.user.dao;

import fel.cvut.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    /**
     * Searching for a user by email
     * @param email A String representes user's email
     * @return User
     */
    User findByEmail(String email);
}
