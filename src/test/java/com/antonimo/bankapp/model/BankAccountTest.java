package com.antonimo.bankapp.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {
	// Create Accounts
	@Test
	void	negativeBalance() {
		assertThrows(IllegalArgumentException.class, () -> new BankAccount("X", -1));
	}

	@Test
	void	zeroBalance() {
		assertDoesNotThrow(() -> new BankAccount("X", 0));
	}

	// Deposits
	@Test
	void	depositValid() {
		BankAccount account = new BankAccount("X", 100);
		account.deposit(20);
		assertEquals(120, account.getBalance(), 1e-9);
	}

	@Test
	void	depositZeroOrNegative() {
		BankAccount	account = new BankAccount("X", 0);
		assertThrows(IllegalArgumentException.class, () -> account.deposit(0));
		assertThrows(IllegalArgumentException.class, () -> account.deposit(-10));
	}

	@Test
	void	depositOverflow() {
		BankAccount	account = new BankAccount("X", Double.MAX_VALUE);
		assertThrows(ArithmeticException.class, () -> account.deposit(10));
	}

	// Withdraws
	@Test
	void	withdrawValid() {
		BankAccount	account = new BankAccount("X", 100);
		account.withdraw(20);
		assertEquals(80.0, account.getBalance(), 1e-9);
	}

	@Test
	void	withdrawZeroOrNegative() {
		BankAccount	account = new BankAccount("X", 100);
		assertThrows(IllegalArgumentException.class, () -> account.withdraw(-10));
		assertThrows(IllegalArgumentException.class, () -> account.withdraw(0));
	}

	@Test
	void	withdrawOverflow() {
		BankAccount	account = new BankAccount("X", 100);
		assertThrows(IllegalArgumentException.class, () -> account.withdraw(200));
	}
}
