package localCoin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import localCoin.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findOneByEmail(String email);

	User findOneByUserName(String userName);
	
	@Query("SELECT u FROM User u WHERE email=?1 OR userName=?1")
	User findOneByUserNameOrEmai(String login);
}
