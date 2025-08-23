package ada.tech.lms.Persistence;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TransactionPersistence {

    private Path caminhoArquivo;

    public TransactionPersistence(){
        try {
            caminhoArquivo = getPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private Path getPath() throws IOException{
        Path caminho = Paths.get("src","main", "java", "ada", "tech", "lms", "Transaction.txt");
        if (!caminho.toFile().exists()){
            caminho.toFile().createNewFile();
        }
        return caminho;
    }



}
