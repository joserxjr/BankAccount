package ada.tech.lms.Persistence;

import ada.tech.lms.domain.User;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CompletableFuture;

public class UserPersistence {

    private Path caminhoArquivo;

    public UserPersistence() {
        try {
            caminhoArquivo = getPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Path getPath() throws IOException {
        Path caminho = Paths.get("src", "main", "java", "ada", "tech", "lms", "Users.txt");
        if (!caminho.toFile().exists()) {
            caminho.toFile().createNewFile();
        }
        return caminho;
    }

    public CompletableFuture<Void> saveUserAsync(User user) {
        return CompletableFuture.runAsync(() -> {
            try (BufferedWriter writer = Files.newBufferedWriter(caminhoArquivo, StandardOpenOption.APPEND)) {
                String line = user.getCpf() + "," + user.getName();
                writer.write(line);
                writer.newLine();
            } catch (IOException ioException) {
                throw new RuntimeException("Error saving user to file.", ioException);
            }
        });
    }



}
