package repository;

import model.BankAccount;
import java.util.Map;
import java.util.HashMap;

public class InMemoryBankRepository implements BankRepository {
	private	Map<String, BankAccount> repository;

	public InMemoryBankRepository() {
		repository = new HashMap<>();
	}

	@Override
	public void	save(BankAccount account) {
		repository.put(account.getHolder(), account);
	}

	@Override
	public BankAccount	findByHolder(String holder) {
		return repository.get(holder);
	}

	@Override
	public Map<String, BankAccount>	findAll() {
		if (repository.isEmpty())
			System.out.println("\nNo accounts available");
		return repository;
	}

	@Override
	public void deposit(String holder, double amount) {
		BankAccount account = repository.get(holder);
		if (account != null) {
			account.deposit(amount);
			save(account);
		} else
			System.err.println("\nError: Account not found: " + holder);
	}

	@Override
	public void withdraw(String holder, double amount) {
		BankAccount account = repository.get(holder);
		if (account != null) {
			account.withdraw(amount);
			save(account);
		} else
			System.err.println("\nError: Account not found: " + holder);
	}

	@Override
	public void	transfer(String src, String dst, double amount) {
		BankAccount srcAccount = repository.get(src);
		BankAccount dstAccount = repository.get(dst);

		if (srcAccount == null || dstAccount == null) {
			throw new IllegalArgumentException("\nError: one or both accounts not found.");
		}
		if (srcAccount == dstAccount) {
			throw new IllegalArgumentException("\nError: can't transfer to the same account.");
		}
		if (amount <= 0) {
			throw new IllegalArgumentException("\nError: amount cannot be 0 or negative.");
		}

		srcAccount.withdraw(amount);
		dstAccount.deposit(amount);
	}
}
