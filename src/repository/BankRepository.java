package repository;

import model.BankAccount;
import java.util.Map;

public interface BankRepository {
	void						save(BankAccount account);
	BankAccount					findByHolder(String holder);
	Map<String, BankAccount>	findAll();
	void						deposit(String holder, double amount);
	void						withdraw(String holder, double amount);
	void						transfer(String src, String dst, double amount);
}
