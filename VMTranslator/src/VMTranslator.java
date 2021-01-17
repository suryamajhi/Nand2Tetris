import java.io.File;
import java.io.IOException;

public class VMTranslator {
    public static void main(String[] args) throws IOException {
        String vmFile = args[0];
        File file = new File(vmFile);

        CodeWriter cw = new CodeWriter(file);

        new Parser(file, cw);
    }
}