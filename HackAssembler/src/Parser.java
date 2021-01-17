import java.util.Scanner;

public class Parser {
    Scanner scanner;
    SymbolTable symbolTable;
    Code code;
    String currentCommand;
    int count = 16;
    public Parser(Scanner scanner,Code code,SymbolTable symbolTable){
        this.scanner = scanner;
        this.code = code;
        this.symbolTable = symbolTable;

        while(hasMoreCommand()){
            advance();
        }
        code.close();
    }

    private boolean hasMoreCommand(){
        return scanner.hasNextLine();
    }

    private void advance(){
        currentCommand = scanner.nextLine();
        if(comment() || currentCommand.isEmpty()) return;
        if(isLabel()) return;
        pureCommand(); //command free of any comments

        if(currentCommand.startsWith("@")){
            if(isVariable()){
                if(!symbolTable.isPresent(currentCommand.substring(1))) symbolTable.insert(currentCommand.substring(1),count++);
                currentCommand = "@" + symbolTable.getValue(currentCommand.substring(1));
            }
            code.aInstruction(currentCommand);
        } else{
            cInstruction();
        }
    }

    private void cInstruction(){
        code.cInstruction(dest(),comp(),jump());
    }
    private String comp(){
        if(currentCommand.contains("=")){
            if(currentCommand.contains(";")) return currentCommand.substring(currentCommand.indexOf("=")+1,currentCommand.indexOf(";")-1);
            return currentCommand.substring(currentCommand.indexOf("=")+1);
        }else return currentCommand.substring(0,currentCommand.indexOf(";"));
    }

    private String dest(){
        String dest="null";
        if(currentCommand.contains("=")) dest = currentCommand.substring(0,currentCommand.indexOf("="));
        return dest;
    }

    public String jump(){
        String jump = "null";
        if(currentCommand.contains(";")) jump = currentCommand.substring(currentCommand.indexOf(";") + 1);
        return jump;
    }

    //helper functions
    private boolean comment(){
        return currentCommand.startsWith("//");
    }
    private void pureCommand(){
        if(currentCommand.contains("//")) currentCommand = currentCommand.substring(0,currentCommand.indexOf("/") - 1);
        currentCommand = currentCommand.trim();
    }
    private boolean isLabel(){
        return currentCommand.startsWith("(");
    }
    private boolean isVariable(){
        Character ch = currentCommand.charAt(1);
        return !(currentCommand.startsWith("@") && (ch.equals('0')||ch.equals('1')||ch.equals('2')||ch.equals('3')||ch.equals('4')||ch.equals('5')||ch.equals('6')||ch.equals('7')||ch.equals('8')||ch.equals('9') ));
    }
}
