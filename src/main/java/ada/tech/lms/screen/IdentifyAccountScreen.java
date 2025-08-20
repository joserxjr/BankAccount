package ada.tech.lms.screen;

import ada.tech.lms.domain.BankAccount;
import ada.tech.lms.domain.User;
import ada.tech.lms.service.BankService;

import java.util.Scanner;

public class  IdentifyAccountScreen {
	private BankService bankService;

	public IdentifyAccountScreen(BankService bankService){
		this.bankService = bankService;
	}
	public User init(Scanner sc){
		System.out.println("Informe o cpf da sua conta");
		return bankService.findUser(sc.next());
	}
}
