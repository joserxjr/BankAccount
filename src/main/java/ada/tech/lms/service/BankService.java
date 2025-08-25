package ada.tech.lms.service;

import ada.tech.lms.Persistence.AccountPersistence;
import ada.tech.lms.Persistence.UserPersistence;
import ada.tech.lms.domain.BankAccount;
import ada.tech.lms.domain.TransactionType;
import ada.tech.lms.domain.User;

import java.util.ArrayList;
import java.util.List;

public class BankService {

    private List<BankAccount> accounts;
    private AccountPersistence accountPersistence = new AccountPersistence();
    private UserPersistence userPersistence = new UserPersistence();

    public BankService() {
        this.accounts = accountPersistence.loadAll(); // construtor lê arquivo assim que programa é iniciado

    }

    public void addAccount(BankAccount account) {
        accounts.add(account);
        accountPersistence.saveAllAsync(accounts).join();//apaga todas contas e salva novamente com nova conta
        User userNew = new User(account.getOwner().getCpf(),account.getOwner().getName());
        userPersistence.saveUserAsync(userNew);
    }

    public void deposit(String accountNumber, double amount) {
        findAccount(accountNumber).deposit(amount);
        accountPersistence.saveAllAsync(accounts).join(); //apaga todas contas e salva novamente com novo saldo
    }

    public void withdraw(String accountNumber, double amount) {
        findAccount(accountNumber).withdraw(amount);
        accountPersistence.saveAllAsync(accounts).join(); //apaga todas contas e salva novamente com novo saldo
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