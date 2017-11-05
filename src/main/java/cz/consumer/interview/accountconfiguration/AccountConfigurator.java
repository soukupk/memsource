package cz.consumer.interview.accountconfiguration;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountConfigurator {
	
	private AccountRepository accountRepository;
	
	@Autowired
	public AccountConfigurator(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
	
	@Transactional
	public void createOrUpdateConfiguration(Account account) {
		List<Account> accounts = accountRepository.findAll();
		
		if(accounts.size() == 0) {
			accountRepository.save(account);
		} else if (accounts.size() == 1) {
			Account accountInDb = accounts.get(0);
			
			accountInDb.setUsername(account.getUsername());
			accountInDb.setPassword(account.getPassword());
			accountRepository.save(accountInDb);
		} else {
			throw new IllegalStateException("There is more than one account configuration in DB.");			
		}
	}

	public Account getConfiguration() throws ConfigurationNotFound {
		List<Account> accounts = accountRepository.findAll();
		
		if(accounts.size() == 0) {
			throw new ConfigurationNotFound("There is not configuration yet.");
		} else if (accounts.size() == 1) {
			return accounts.get(0);
		} else {
			throw new IllegalStateException("There is more than one account configuration in DB.");	
		}
	}
}