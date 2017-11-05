package cz.consumer.interview.accountconfiguration;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cz.consumer.interview.accountconfiguration.Account;
import cz.consumer.interview.accountconfiguration.AccountConfigurator;
import cz.consumer.interview.accountconfiguration.AccountRepository;
import cz.consumer.interview.accountconfiguration.ConfigurationNotFound;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountConfiguratorIT {
	
	@Autowired
	AccountConfigurator accountConfigurator;

	@Autowired
	AccountRepository accountRepository;
	
	@Before
	public void setUp() {
		
	}

	@Test
	public void testCreateOrUpdateConfiguration_shouldCreateConfiguration_WhenThereIsNotYet() {
		Account account = createAccount("karel.soukup", "password");
		
		accountConfigurator.createOrUpdateConfiguration(account);
		
		List<Account> accountsInDb = accountRepository.findAll();
		Assert.assertThat(accountsInDb.size(), CoreMatchers.is(1));
		
		Account accountInDb = accountsInDb.get(0);
		assertUsernameAndPassword(accountInDb, account);
	}
	
	@Test
	public void testCreateOrUpdateConfiguration_shouldUpdateConfiguration_WhenThereAlreadyIs() {
		Account account = createAccount("karel.soukup", "password");
		
		accountConfigurator.createOrUpdateConfiguration(account);
		
		Account accountChanged = createAccount("karel.soukup.changed", "password.changed");
		
		accountConfigurator.createOrUpdateConfiguration(accountChanged);
		
		List<Account> accountsInDb = accountRepository.findAll();		
		Assert.assertThat(accountsInDb.size(), CoreMatchers.is(1));
		
		Account accountInDb = accountsInDb.get(0);
		assertUsernameAndPassword(accountChanged, accountInDb);
	}
	
	@Test
	public void testGet_shouldRetunConfiguration_whenExists() throws ConfigurationNotFound {
		Account account = createAccount("karel.soukup", "password");
		
		accountConfigurator.createOrUpdateConfiguration(account);
		
		Account accountInDb = accountConfigurator.getConfiguration();
		assertUsernameAndPassword(account, accountInDb);
	}

	private Account createAccount(String username, String password) {
		Account account = new Account();
		account.setUsername(username);
		account.setPassword(password);
		return account;
	}
	
	private void assertUsernameAndPassword(Account expectedAccount, Account actualAccount) {
		Assert.assertThat(actualAccount.getUsername(), CoreMatchers.is(expectedAccount.getUsername()));
		Assert.assertThat(actualAccount.getPassword(), CoreMatchers.is(expectedAccount.getPassword()));
	}

}
