package localCoin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import localCoin.model.Account;
import localCoin.model.User;

public interface AccountRepository extends JpaRepository<Account, Long> {
	List<Account> findByUser(User user);
	Account findOneByAddress(String address);
	Account findOneById(long id);
	Account findOneByUserId(long id);
}
