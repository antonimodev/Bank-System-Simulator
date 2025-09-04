package com.antonimo.bankapp.service;

import com.antonimo.bankapp.model.BankAccount;
import com.antonimo.bankapp.repository.BankRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class BankServiceTest {

	private BankRepository	mockRepo;
	private BankService		service;

	@BeforeEach
	void setUp() {
		mockRepo = mock(BankRepository.class);
		service = new BankService(mockRepo);
	}

	@Test
	void createAccountValid() {
		doNothing()
			.when(mockRepo)
				.save(any(BankAccount.class));

		BankAccount account = service.createAccount("X", 100);

		verify(mockRepo).save(any(BankAccount.class));
		assertEquals("X", account.getHolder());
		assertEquals(100, account.getBalance(), 1e-9);
	}

	@Test
	void transferValid() {
		BankAccount accountA = new BankAccount("X", 100);
		BankAccount accountB = new BankAccount("Y", 50);

		when(mockRepo.findByHolder("X"))
			.thenReturn(accountA);
		when(mockRepo.findByHolder("Y"))
			.thenReturn(accountB);

		service.transferAmount("X", "Y", 30);

		verify(mockRepo).findByHolder("X");
		verify(mockRepo).findByHolder("Y");

		assertEquals(70, accountA.getBalance(), 1e-9);
		assertEquals(80, accountB.getBalance(), 1e-9);
	}

	@Test
	void transferNegativeAmount() {
		assertThrows(IllegalArgumentException.class, () ->
				service.transferAmount("X", "Y", -10));
	}

	@Test
	void transferInsufficientFunds() {
		BankAccount accountA = new BankAccount("X", 50);
		BankAccount accountB = new BankAccount("Y", 50);

		when(mockRepo.findByHolder("X"))
			.thenReturn(accountA);
		when(mockRepo.findByHolder("Y"))
			.thenReturn(accountB);

		assertThrows(IllegalArgumentException.class, () ->
				service.transferAmount("X", "Y", 100));
	}

	@Test
	void transferToSelf() {
		BankAccount account = new BankAccount("X", 100);
		when(mockRepo.findByHolder("X"))
			.thenReturn(account);

		assertThrows(IllegalArgumentException.class, () ->
				service.transferAmount("X", "X", 20));
	}

	@Test
	void transferFromNonExistentAccount() {
		when(mockRepo.findByHolder("Null"))
			.thenReturn(null);
		when(mockRepo.findByHolder("Y"))
			.thenReturn(new BankAccount("Y", 50));

		assertThrows(NullPointerException.class, () ->
				service.transferAmount("Null", "Y", 20));
	}

	@Test
	void transferToNonExistentAccount() {
		when(mockRepo.findByHolder("X"))
			.thenReturn(new BankAccount("X", 100));
		when(mockRepo.findByHolder("Null"))
			.thenReturn(null);

		assertThrows(NullPointerException.class, () ->
				service.transferAmount("X", "Null", 20));
	}
}

