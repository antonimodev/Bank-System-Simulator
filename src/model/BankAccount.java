package model;

public class BankAccount {
	private String	holder;
	private double	balance;

	/* BUILDERS */
	public	BankAccount(String holder, double balance) {
		if (balance < 0)
			throw new IllegalArgumentException("Initial balance cannot be negative");
		this.holder = holder;
		this.balance = balance;
	}

	public	BankAccount(String holder) {
		this.holder = holder;
		this.balance = 0;
	}

	/* GENERAL METHODS */
	public void	deposit(double amount) {
		if (amount <= 0)
			throw new IllegalArgumentException("Deposit must be positive");
		balance += amount;
	}

	public void	withdraw(double amount) {
		if ((balance - amount) < 0)
			throw new IllegalArgumentException("Insufficient funds");
		balance -= amount;
	}

	/* GETTERS */
	public double	getBalance() {
		return balance;
	}

	public String	getHolder() {
		return holder;
	}

	/* ??? */
	@Override
	public String toString() {
		return String.format("Holder: %s has %.2f EUR in the account", holder, balance);
	}
}
