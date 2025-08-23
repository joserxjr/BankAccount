package ada.tech.lms.screen;

import ada.tech.lms.domain.User;
import ada.tech.lms.service.BankService;

import java.util.Scanner;

public class DepositExecutedOption implements ExecutedOption {
	private final BankService bankService;
	private final Scanner scanner;
	private final User user;
	public DepositExecutedOption(BankService bankService, Scanner scanner, User user) {
		this.bankService = bankService;
		this.scanner = scanner;
		this.user = user;
	}

	@Override
	public void execute() {
		System.out.println("Qual o valor que deseja depositar?");
		var value = scanner.nextDouble();
        bankService.deposit(bankService.findAccountByUser(user).getAccountNumber(), value);
		System.out.println("deposito realizado com sucesso");
	}
}
