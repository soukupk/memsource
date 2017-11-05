package cz.consumer.interview.accountconfiguration;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import cz.consumer.interview.accountconfiguration.Account;
import cz.consumer.interview.accountconfiguration.AccountConfigurator;
import cz.consumer.interview.accountconfiguration.AccountRepository;
import cz.consumer.interview.accountconfiguration.ConfigurationNotFound;

@RunWith(MockitoJUnitRunner.class)
public class AccountConfiguratorTest {

	AccountConfigurator accountConfigurator;

	AccountRepository accountRepository;
	
	@Before
	public void setUp() {
		accountRepository = new FakeAccountRepository();
		accountConfigurator = new AccountConfigurator(accountRepository);
	}

	@Test
	public void testCreateOrUpdateConfiguration_shouldCreateNewConfiguration_whenConfigurationNotExists() {
		Account account = Account.create("karel.soukup", "password");
		
		accountConfigurator.createOrUpdateConfiguration(account);
		
		List<Account> accountsInDb = accountRepository.findAll();
		Assert.assertThat(accountsInDb.size(), CoreMatchers.is(1));
		
		Account accountInDb = accountsInDb.get(0);
		assertUsernameAndPassword(accountInDb, account);
	}
	
	@Test
	public void testCreateOrUpdateConfiguration_shouldOnlyUpdateConfiguration_WhenConfigurationExists() {
		Account account = Account.create("karel.soukup", "password");
		
		accountConfigurator.createOrUpdateConfiguration(account);
		
		Account accountChanged = Account.create("karel.soukup.changed", "password.changed");
		
		accountConfigurator.createOrUpdateConfiguration(accountChanged);
		
		List<Account> accountsInDb = accountRepository.findAll();		
		Assert.assertThat(accountsInDb.size(), CoreMatchers.is(1));
		
		Account accountInDb = accountsInDb.get(0);
		assertUsernameAndPassword(accountChanged, accountInDb);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testCreateOrUpdateConfiguration_shouldThrowIllegalStateExceptionWhenThereIsMoreThanOneAccountInDb() {
		Account accountOne = Account.create("karel.soukup", "password");
		Account accountTwo = Account.create("karel.soukup.two", "password.two");
		accountRepository.save(accountOne);
		accountRepository.save(accountTwo);
		accountConfigurator.createOrUpdateConfiguration(accountOne);
	}
	
	@Test
	public void testGet_shouldReturnConfiguration_whenConfigurationExists() throws ConfigurationNotFound {
		Account account = Account.create("karel.soukup", "password");
		
		accountConfigurator.createOrUpdateConfiguration(account);
		
		Account accountInDb = accountConfigurator.getConfiguration();
		assertUsernameAndPassword(account, accountInDb);
	}
	
	@Test(expected = ConfigurationNotFound.class)
	public void testGetConfiguration_shouldThrowConfigurationNotFound_whenNotExists() throws ConfigurationNotFound {
		accountConfigurator.getConfiguration();
	}
	
	private void assertUsernameAndPassword(Account expectedAccount, Account actualAccount) {
		Assert.assertThat(actualAccount.getUsername(), CoreMatchers.is(expectedAccount.getUsername()));
		Assert.assertThat(actualAccount.getPassword(), CoreMatchers.is(expectedAccount.getPassword()));
	}
	
	@Test(expected = IllegalStateException.class)
	public void testGetConfiguration_shouldThrowIllegalStateExceptionWhenThereIsMoreThanOneAccountInDb() throws ConfigurationNotFound {
		Account accountOne = Account.create("karel.soukup", "password");
		Account accountTwo = Account.create("karel.soukup.two", "password.two");
		accountRepository.save(accountOne);
		accountRepository.save(accountTwo);
		
		accountConfigurator.getConfiguration();
	}

}
