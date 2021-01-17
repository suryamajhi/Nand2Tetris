import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;


public class Parser {
    private String currentCommand;
    private final CodeWriter cw;
    private ArrayList<String> arithmeticCommands;
    private String commandType;
    private String[] commandSplits = new String[3];
    Scanner[] scanners;
    File[] files;

    public Parser(File file,CodeWriter cw) throws IOException {
        this.cw = cw;
        arithmeticCommands();

        if(file.isDirectory()){
            files = file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.getName().contains(".vm");
                }
            });
            assert files != null;
            scanners = new Scanner[files.length];
            for (int i = 0; i < files.length; i++) {
                scanners[i] = new Scanner(files[i]);
            }

        }else{
            scanners = new Scanner[1];
            scanners[0] = new Scanner(file);
        }
        for (int i = 0; i < scanners.length; i++) {
            if(file.isDirectory()){
                String f = files[i].getName();
                cw.fileName = f.substring(0,f.lastIndexOf("."));
            }

            while(hasMoreCommand(scanners[i]))
                advance(scanners[i]);
        }
        cw.close();
    }


    private boolean hasMoreCommand(Scanner scanner){
        return scanner.hasNextLine();
    }

    private void advance(Scanner scanner){
        currentCommand  = scanner.nextLine();
        if (currentCommand.startsWith("//")|| currentCommand.isEmpty()) return;
        pureCommand();
        commandSplits = currentCommand.split(" ");
        commandType = commandType();
        switch (Objects.requireNonNull(commandType)){
            case "C_ARITHMETIC": cw.writeArithmetic(arg1());break;
            case "C_PUSH" :
            case "C_POP" :       cw.writePushPop(commandType,arg1(),arg2());break;
            case "C_LABEL":      cw.writeLabel(arg1());break;
            case "C_GOTO":       cw.writeGoto(arg1());break;
            case "C_IF" :        cw.writeIf(arg1());break;
            case "C_FUNCTION":   cw.writeFunction(arg1(),arg2());break;
            case "C_RETURN":     cw.writeReturn();break;
            case "C_CALL":       cw.writeCall(arg1(),arg2()); break;
        }

    }

    private String commandType(){
        if(arithmeticCommands.contains(currentCommand))
            return "C_ARITHMETIC";
        else{
            switch (commandSplits[0]){
                case "push":return "C_PUSH";
                case "pop": return "C_POP";
                case "label": return "C_LABEL";
                case "goto": return "C_GOTO";
                case "if-goto": return "C_IF";
                case "function": return "C_FUNCTION";
                case "call": return "C_CALL";
                case "return": return "C_RETURN";
            }
        }
        return null;
    }

    private String arg1(){
        switch (commandType){
            case "C_ARITHMETIC" : return commandSplits[0];
            case "C_PUSH" :
            case "C_POP" :
            case "C_LABEL":
            case "C_GOTO":
            case "C_IF":
            case "C_FUNCTION":
            case "C_CALL":
                return commandSplits[1];
        }
        return commandSplits[0];
    }
    private int arg2(){
        return Integer.parseInt(commandSplits[2]);
    }


    private void pureCommand(){
        if(currentCommand.contains("//")){
            currentCommand = currentCommand.substring(0,currentCommand.indexOf("/")).trim();
        }else currentCommand = currentCommand.trim();
    }

    private void arithmeticCommands(){
        arithmeticCommands = new ArrayList<>();
        arithmeticCommands.add("add");
        arithmeticCommands.add("sub");
        arithmeticCommands.add("neg");
        arithmeticCommands.add("eq");
        arithmeticCommands.add("gt");
        arithmeticCommands.add("lt");
        arithmeticCommands.add("and");
        arithmeticCommands.add("or");
        arithmeticCommands.add("not");
    }
}
