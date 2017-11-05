package cz.consumer.interview.accountconfiguration;

import java.util.List;

import org.springframework.data.repository.Repository;

public interface AccountRepository extends Repository<Account, Long> {

	void save(Account account);
	
	List<Account> findAll();
	
}
