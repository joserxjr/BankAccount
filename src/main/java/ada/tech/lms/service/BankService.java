package ada.tech.lms.service;

import ada.tech.lms.domain.BankAccount;
import ada.tech.lms.domain.User;

import java.util.ArrayList;
import java.util.List;

public class BankService {
    private List<BankAccount> accounts = new ArrayList<>();

    public void addAccount(BankAccount account) {
        accounts.add(account);
    }

    public void deposit(String accountNumber, double amount) {
        findAccount(accountNumber).deposit(amount);
    }

    public void withdraw(String accountNumber, double amount) {
        findAccount(accountNumber).withdraw(amount);
    }


    public double checkBalance(String accountNumber) {
        return findAccount(accountNumber).getBalance();
    }

    public BankAccount findAccount(String accountNumber) {
        return accounts.stream()
            .filter(a -> a.getAccountNumber().equals(accountNumber))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }

    public User findUser(String documentNumber){
       for (BankAccount account : accounts){
           if(account.getOwner().getCpf().equals(documentNumber)){
               return account.getOwner();
           }
       }
       throw new IllegalArgumentException("There is no owner");
    }

    public BankAccount findAccountByUser(User user) {
        for (BankAccount account : accounts){
            if(account.getOwner().getCpf().equals(user.getCpf())){
                return account;
            }
        }
        throw new IllegalArgumentException("There is no owner");

    }
}