package com.antonimo.bankapp.app;

import com.antonimo.bankapp.model.BankAccount;
import com.antonimo.bankapp.repository.*;
import java.util.Scanner;

public class Main {
	private static final String CREATE_ACCOUNT = "1";
	private static final String DEPOSIT = "2";
	private static final String WITHDRAW = "3";
	private static final String TRANSFER = "4";
	private static final String LIST_ACCOUNTS = "5";
	private static final String EXIT = "6";

	public static void main(String[] args) {
		BankRepository	repository = new InMemoryBankRepository();
		Scanner	scan = new Scanner(System.in);

		System.out.println("Welcome to Piggy's bank!");

		while (true) {
			printMenu();
			String	option = scan.nextLine();
			switch (option) {
				case CREATE_ACCOUNT:
					createAccount(scan, repository);
					break;
				case DEPOSIT:
					deposit(scan, repository);
					break;
				case WITHDRAW:
					withdraw(scan, repository);
					break;
				case TRANSFER:
					transfer(scan, repository);
					break;
				case LIST_ACCOUNTS:
					listAccounts(repository);
					break;
				case EXIT:
					scan.close();
					System.err.println("Exiting...");
					return;
				default:
					System.out.println("Select a valid option.");
					break;
				}
			}
		}

	private static void createAccount(Scanner scan, BankRepository repository) {
		System.out.print("Introduce holder: ");
		String holder = scan.nextLine();
		double balance = readDouble(scan, "Introduce balance: ");

		try {
			repository.save(new BankAccount(holder, balance));
		} catch (IllegalArgumentException e) {
			System.out.println("\nError: " + e.getMessage());
		}
	}

	private static void	deposit(Scanner scan, BankRepository repository) {
		System.out.print("Introduce holder: ");
		String	holder = scan.nextLine();
		double	amount = readDouble(scan, "Introduce amount: ");

		try {
			repository.deposit(holder, amount);
			System.out.println("Deposit successfull");
		} catch (IllegalArgumentException e) {
			System.err.println("\nError: " + e.getMessage());
		}
	}

	private static void withdraw(Scanner scan, BankRepository repository) {
		System.out.print("Introduce holder: ");
		String	holder = scan.nextLine();
		double	amount = readDouble(scan, "Introduce amount: ");
		
		try {
			repository.withdraw(holder, amount);
			System.out.println("Withdraw successfull");
		} catch (IllegalArgumentException e) {
			System.err.println("\nError: " + e.getMessage());
		}
	}

	private static void transfer(Scanner scan, BankRepository repository) {
		System.out.print("Introduce holder of source account: ");
		String	src = scan.nextLine();
		System.out.print("Introduce holder of destination account: ");
		String	dst = scan.nextLine();
		double amount = readDouble(scan, "Introduce amount to transfer: ");

		try {
			repository.transfer(src, dst, amount);
			System.out.println(amount + " was transfered to " + dst + "'s account successfully");
		} catch (IllegalArgumentException e) {
			System.err.println("\nError: " + e.getMessage());
		}
	}

	private static void listAccounts(BankRepository repository) {
		repository.findAll().forEach((holder, account) -> {
		System.out.println(
			"Holder: " + holder
			+ ", Balance: " + account.getBalance()
			+ " EUR");
		});
	}

	private static double readDouble(Scanner scan, String prompt) {
		while (true) {
			System.out.print(prompt);
			String input = scan.nextLine();
			try {
				return Double.parseDouble(input);
			} catch (NumberFormatException e) {
				System.out.println("\nError: Introduce valid number.");
			}
		}
	}

	private static void printMenu() {
		System.out.println(
			"\n--- Select one option ---\n"
			+ "1.- Create bank account.\n"
			+ "2.- Deposit. \n"
			+ "3.- Withdraw. \n"
			+ "4.- Transfer. \n"
			+ "5.- List all accounts. \n"
			+ "6.- Exit.\n"
			+ "-------------------------"
		);
	}
}