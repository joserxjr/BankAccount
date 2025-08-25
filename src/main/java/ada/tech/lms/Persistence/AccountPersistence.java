package ada.tech.lms.Persistence;

import ada.tech.lms.domain.BankAccount;
import ada.tech.lms.domain.SimpleAccount;
import ada.tech.lms.domain.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;



public class AccountPersistence {

    private Path caminhoArquivo;

    public AccountPersistence(){
        try {
            caminhoArquivo = getPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Path getPath() throws IOException {
        Path caminho = Paths.get("src","main", "java", "ada", "tech", "lms", "Account.txt");
        if (!caminho.toFile().exists()){
            caminho.toFile().createNewFile();
        }
        return caminho;
    }

    public List<BankAccount> loadAll(){
        List<BankAccount> accounts = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(caminhoArquivo)){
            accounts = reader.lines().map(this::parseLineToAccount).collect(Collectors.toList());
        }
        catch (IOException e){
            System.err.println("Error loading accounts from file: " + e.getMessage());
        }
        return accounts;
    }

    private BankAccount parseLineToAccount(String line){
        String[] parts = line.split(",");
        String accountNumber = parts[0].trim();
        String cpf = parts[1].trim();
        String name = parts[2].trim();
        double balance = Double.parseDouble(parts[3].trim());
        String accountType = parts[4].trim();

        User ower = new User(cpf, name);

        return new SimpleAccount(accountNumber, ower, balance);
    }

    public CompletableFuture<Void> saveAllAsync(List<BankAccount> accounts) {
        return CompletableFuture.runAsync(() -> {
            try (BufferedWriter writer = Files.newBufferedWriter(caminhoArquivo, StandardOpenOption.TRUNCATE_EXISTING)) {
                for (BankAccount account : accounts) {
                    String line = account.getAccountNumber() + ","
                            + account.getOwner().getCpf() + ","
                            + account.getOwner().getName() + ","
                            + account.getBalance() + ","
                            + account.getClass().getSimpleName();
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException ioException) {
                throw new RuntimeException("Error saving accounts to file.", ioException);
            }
        });
    }
}


