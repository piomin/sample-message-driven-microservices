package pl.piomin.services.account.repository;

import pl.piomin.services.account.model.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountRepository {

	private List<Account> accounts = new ArrayList<>();
	
	public Account add(Account account) {
		account.setId((long) (accounts.size()+1));
		accounts.add(account);
		return account;
	}
	
	public Account update(Account account) {
		accounts.set(account.getId().intValue() - 1, account);
		return account;
	}
	
	public Account findById(Long id) {
		return accounts.stream()
				.filter(a -> a.getId().equals(id))
				.findFirst()
				.orElseThrow();
	}
	
	public void delete(Long id) {
		accounts.remove(id.intValue());
	}
	
	public List<Account> find(List<Long> ids) {
		return accounts.stream()
				.filter(a -> ids.contains(a.getId()))
				.toList();
	}
	
	public List<Account> findByCustomer(Long customerId) {
		return accounts.stream()
				.filter(a -> a.getCustomerId().equals(customerId))
				.toList();
	}
	
}
