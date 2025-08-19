package service;

import repository.BankRepository;

public class BankService {
	private	BankRepository repository;

	public BankService(BankRepository repository) {
		this.repository = repository;
	}

	public void	transferAmount(String src, String dst, double amount) {
		if (src == null || dst == null)
			throw new IllegalArgumentException("One of the accounts doesn't exist");
		if (src.equals(dst))
			throw new IllegalArgumentException("Cannot transfer to the same account");
		if (amount <= 0)
			throw new IllegalArgumentException("Transfer amount must be positive");

		repository.findByHolder(src).withdraw(amount);
		repository.findByHolder(dst).deposit(amount);
	}
}
