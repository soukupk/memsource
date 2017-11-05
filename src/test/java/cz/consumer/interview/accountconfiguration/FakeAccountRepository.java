package cz.consumer.interview.accountconfiguration;

import cz.consumer.interview.accountconfiguration.Account;
import cz.consumer.interview.accountconfiguration.AccountRepository;
import cz.consumer.interview.helper.FakeRepository;

public class FakeAccountRepository extends FakeRepository<Account> implements AccountRepository {

	@Override
	protected Long getId(Account entity) {
		return entity.getId();
	}

	@Override
	protected void setId(Account entity, Long id) {
		entity.setId(id);
	}

}
