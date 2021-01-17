import java.io.*;
import java.util.Scanner;

public class Assembler {
    public static void main(String[] args) throws IOException {
        String assemblyFile = args[0];
        String fileName = assemblyFile.substring(0,assemblyFile.indexOf("."));
        Scanner scanner = new Scanner(new File(assemblyFile));
        Scanner scannerForFirstPass = new Scanner(new File(assemblyFile));
        SymbolTable symbolTable = new SymbolTable();
        Code code = new Code(fileName);

        firstPass(scannerForFirstPass, symbolTable);

        secondPass(scanner, code, symbolTable);

    }


    private static void firstPass(Scanner scanner, SymbolTable symbolTable){
        String command, label;
        int count = 0;

        while(scanner.hasNextLine()){
            command = scanner.nextLine();
            command = command.trim();
            if(command.startsWith("//")||command.isEmpty()) continue;
            if(command.startsWith("(")){
                label = command.substring(1,command.indexOf(")"));
                symbolTable.insert(label, count);
                continue;
            }
            count++;
        }
    }

    private static void secondPass(Scanner scanner,Code code,SymbolTable symbolTable){
        new Parser(scanner, code, symbolTable);
    }

}
