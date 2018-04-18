package localCoin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import localCoin.model.Transfer;
import localCoin.model.User;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
	@Query("SELECT t FROM Transfer t JOIN Account a ON t.sender = a OR t.reciver = a WHERE a.user = ?1 ORDER BY t.created DESC")
	List<Transfer> fidByUser(User user);

}
