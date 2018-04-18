package localCoin.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import localCoin.model.Account;
import localCoin.repository.AccountRepository;

public class AccountConverter implements Converter<String, Account> {
	@Autowired
	AccountRepository accountRepository;

	public Account convert(String address) {
		return accountRepository.findOneByAddress(address);
	}

}
