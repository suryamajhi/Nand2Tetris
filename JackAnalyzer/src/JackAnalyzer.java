import java.io.File;
import java.io.FileNotFoundException;

public class JackAnalyzer {
    public static void main(String[] args) throws FileNotFoundException {
        File file =new File(args[0]);
        System.out.println("The path is " + file.getPath());
        new JackTokenizer(file);
    }
}