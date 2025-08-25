package com.antonimo.bankapp.repository;

import java.util.Map;
import java.util.HashMap;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;

import com.antonimo.bankapp.model.BankAccount;

public class FileBankRepository implements BankRepository {
	private final File file;
	private Map<String, BankAccount> repository = new HashMap<>();

	public FileBankRepository(String filePath) {
		file = new File(filePath);
		if (file.exists())
			loadFromFile();
	}

	@Override
	public void save(BankAccount account) {
		repository.put(account.getHolder(), account);
		writeAllToFile();
	}

	@Override
    public BankAccount	findByHolder(String holder) {
		return repository.get(holder);
	}

	@Override
	public Map<String, BankAccount>	findAll() {
		if (repository.isEmpty())
			System.out.println("There are not any account");
		return (repository);
	}

	@Override
	public void	deposit(String holder, double amount) {
		BankAccount account = repository.get(holder);
		if (account == null) {
			throw new IllegalArgumentException("Account not found: " + holder);
		}
		account.deposit(amount);
		writeAllToFile();
	}

	@Override
	public void	withdraw(String holder, double amount) {
		BankAccount account = repository.get(holder);
		if (account == null) {
			throw new IllegalArgumentException("Account not found: " + holder);
		}
		account.withdraw(amount);
		writeAllToFile();
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
		writeAllToFile();
	}
	// PRIVATE METHODS

	private void	loadFromFile() {
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				String holder = parts[0];
				double balance = Double.parseDouble(parts[1]);
				repository.put(holder, new BankAccount(holder, balance));
			}
		} catch (Exception e) {
			System.err.println("\nError loading file: " + e.getMessage());
		}
	}

	private void	writeAllToFile() {
		StringBuilder allContent = new StringBuilder();

		repository.forEach((holder, acc) -> {
			allContent
			.append(holder)
			.append(",")
			.append(acc.getBalance())
			.append("\n");
		});
		try (FileWriter writer = new FileWriter(file, false)) {
			writer.write(allContent.toString());
		} catch (Exception e) {
			System.err.println("\nError writing to file: " + e.getMessage());
		}
	}
}
