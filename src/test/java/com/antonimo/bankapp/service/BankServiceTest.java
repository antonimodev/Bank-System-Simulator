package com.antonimo.bankapp.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.antonimo.bankapp.model.BankAccount;
import com.antonimo.bankapp.repository.*;

public class BankServiceTest {
	//Transfer
	@Test
	void	transferValid() {
		BankRepository	repository = new InMemoryBankRepository();
		BankService	service = new BankService(repository);

		BankAccount account = service.createAccount("X", 100);
		BankAccount account2 = service.createAccount("Y", 100);

		service.transferAmount(account.getHolder(), account2.getHolder(), 20);

		assertEquals(80.0, account.getBalance(), 1e-9);
		assertEquals(120.0, account2.getBalance(), 1e-9);
	}

	@Test
	void	transferNegative() {
		BankRepository	repository = new InMemoryBankRepository();
		BankService	service = new BankService(repository);

		BankAccount	account = service.createAccount("X", 100);
		BankAccount	account2 = service.createAccount("Y", 100);

		assertThrows(IllegalArgumentException.class,
		() -> service.transferAmount(account.getHolder(), account2.getHolder(), -20));
	}

	@Test
	void	transferInsufficientFunds() {
		BankRepository	repository = new InMemoryBankRepository();
		BankService	service = new BankService(repository);

		BankAccount	account = service.createAccount("X", 100);
		BankAccount	account2 = service.createAccount("Y", 100);

		assertThrows(IllegalArgumentException.class,
		() -> service.transferAmount(account.getHolder(), account2.getHolder(), 150));
	}

	@Test
	void	transferToSelf() {
		BankRepository	repository = new InMemoryBankRepository();
		BankService	service = new BankService(repository);

		BankAccount	account = service.createAccount("X", 100);

		assertThrows(IllegalArgumentException.class,
		() -> service.transferAmount(account.getHolder(), account.getHolder(), 20));
	}

	@Test
	void	transferToNull() {
		BankRepository	repository = new InMemoryBankRepository();
		BankService	service = new BankService(repository);

		BankAccount	account = service.createAccount("X", 100);

		assertThrows(NullPointerException.class,
		() -> service.transferAmount(account.getHolder(), "null", 20));
	}
}
