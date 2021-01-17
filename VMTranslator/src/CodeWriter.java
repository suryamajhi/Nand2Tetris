import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CodeWriter {
    private int label = 0;
    public  String fileName, filePath;
    private final PrintWriter pw;
    private String assembly = "";
    private int i = 0,j = 0;

    public CodeWriter(File file) throws IOException {
        this.fileName = file.getName();
        filePath = file.getPath();
        if(file.isDirectory()) {
            pw = new PrintWriter(new FileWriter(new File(file.getPath() + "/" + fileName + ".asm")));
            bootStrapCode();
        }else{
            filePath = filePath.substring(0,filePath.lastIndexOf("."));
            System.out.println(filePath);
            pw = new PrintWriter(new FileWriter(new File(filePath+".asm")));
        }
    }

    private void bootStrapCode(){
        pw.println("//Bootstrap code");
        pw.println("@256");
        pw.println("D=A");
        pw.println("@0");
        pw.println("M=D");
        writeCall("Sys.init", 0);
    }

    public void writeArithmetic(String command){
        pw.println("//"+command);
        switch (command) {
            case "add": assembly = "@SP\nAM=M-1\nD=M\nA=A-1\nMD=D+M";break;
            case "sub": assembly = "@SP\nAM=M-1\nD=M\nA=A-1\nMD=M-D";break;
            case "neg": assembly = "@SP\nA=M-1\nD=M\nM=-D";break;
            case "eq" : assembly = "@SP\nAM=M-1\nD=M\nA=A-1\nD=M-D\n@LABEL"+ label+ "\nD;JEQ\n@SP\nA=M-1\nM=0\n@LABEL"+label+"\nD=A\n@3\nA=D+A\n0;JMP\n(LABEL"+label+")\n@SP\nA=M-1\nM=-1"; label++;break;
            case "gt" : assembly = "@SP\nAM=M-1\nD=M\nA=A-1\nD=M-D\n@LABEL"+ label+ "\nD;JGT\n@SP\nA=M-1\nM=0\n@LABEL"+label+"\nD=A\n@3\nA=D+A\n0;JMP\n(LABEL"+label+")\n@SP\nA=M-1\nM=-1"; label++;break;
            case "lt" : assembly = "@SP\nAM=M-1\nD=M\nA=A-1\nD=M-D\n@LABEL"+ label+ "\nD;JLT\n@SP\nA=M-1\nM=0\n@LABEL"+label+"\nD=A\n@3\nA=D+A\n0;JMP\n(LABEL"+label+")\n@SP\nA=M-1\nM=-1"; label++;break;
            case "or" : assembly = "@SP\nAM=M-1\nD=M\nA=A-1\nMD=D|M";break;
            case "and": assembly = "@SP\nAM=M-1\nD=M\nA=A-1\nMD=D&M";break;
            case "not": assembly = "@SP\nA=M-1\nD=M\nM=!D";break;
        }
        pw.println(assembly);
    }

    public void writePushPop(String commandType,String segment,int index){
        if(commandType.equals("C_PUSH")){
            switch (segment){
                case "constant": assembly = "@"+index+"\nD=A\n@SP\nA=M\nM=D\n@SP\nM=M+1";break;
                case "local"   : assembly = "@"+index+"\nD=A\n@LCL\nD=M+D\n@R13\nM=D\n@R13\nA=M\nD=M\n@SP\nA=M\nM=D\n@SP\nD=M\nM=D+1";break;
                case "argument": assembly = "@"+index+"\nD=A\n@ARG\nD=M+D\n@R13\nM=D\n@R13\nA=M\nD=M\n@SP\nA=M\nM=D\n@SP\nD=M\nM=D+1";break;
                case "static"  : assembly = "@"+fileName+"."+index+"\nD=M\n@SP\nA=M\nM=D\n@SP\nD=M\nM=D+1";break;
                case "temp"    : assembly = "@"+index+"\nD=A\n@5\nD=D+A\n@R13\nM=D\n@R13\nA=M\nD=M\n@SP\nA=M\nM=D\n@SP\nD=M\nM=D+1";break;
                case "this"    : assembly = "@"+index+"\nD=A\n@THIS\nD=M+D\n@R13\nM=D\n@R13\nA=M\nD=M\n@SP\nA=M\nM=D\n@SP\nD=M\nM=D+1";break;
                case "that"    : assembly = "@"+index+"\nD=A\n@THAT\nD=M+D\n@R13\nM=D\n@R13\nA=M\nD=M\n@SP\nA=M\nM=D\n@SP\nD=M\nM=D+1";break;
                case "pointer":{
                    if(index == 0){
                        assembly = "@THIS\nD=M\n@SP\nA=M\nM=D\n@SP\nD=M\nM=D+1";
                    }else{
                        assembly = "@THAT\nD=M\n@SP\nA=M\nM=D\n@SP\nD=M\nM=D+1";
                    }
                    break;
                }
            }
        }
        if(commandType.equals("C_POP")){
            switch (segment){
                case "local"   : assembly = "@"+index+"\nD=A\n@LCL\nD=M+D\n@R13\nM=D\n@SP\nAM=M-1\nD=M\n@R13\nA=M\nM=D";break;
                case "argument": assembly = "@"+index+"\nD=A\n@ARG\nD=M+D\n@R13\nM=D\n@SP\nAM=M-1\nD=M\n@R13\nA=M\nM=D";break;
                case "static"  : assembly = "@SP\nAM=M-1\nD=M\n@"+fileName+"."+index +"\nM=D";break;
                case "temp"    : assembly = "@"+index+"\nD=A\n@5\nD=D+A\n@R13\nM=D\n@SP\nAM=M-1\nD=M\n@R13\nA=M\nM=D";break;
                case "this"    : assembly = "@"+index+"\nD=A\n@THIS\nD=M+D\n@R13\nM=D\n@SP\nAM=M-1\nD=M\n@R13\nA=M\nM=D";break;
                case "that"    : assembly = "@"+index+"\nD=A\n@THAT\nD=M+D\n@R13\nM=D\n@SP\nAM=M-1\nD=M\n@R13\nA=M\nM=D";break;
                case "pointer":{
                    if(index==0){
                        assembly = "@SP\nM=M-1\nA=M\nD=M\n@THIS\nM=D";
                    }else{
                        assembly = "@SP\nM=M-1\nA=M\nD=M\n@THAT\nM=D";
                    }
                }
            }
        }
        pw.println("//"+commandType+" "+segment+ " "+ index);
        pw.println(assembly);
    }

    public void writeLabel(String label){
        pw.println("// Label "+label);
        pw.println("(" + label + ")");
    }
    public void writeGoto(String label){
        pw.println("// goto "+label);
        assembly = "@"+label+"\n0;JMP";
        pw.println(assembly);
    }
    public void writeIf(String label){
        pw.println("// If-goto "+ label);
        assembly = "@SP\nAM=M-1\nD=M\n@eq"+j+"\nD;JEQ\n@"+label+"\n0;JMP\n(eq"+j+++")";
        pw.println(assembly);
    }
    public void writeFunction(String functionName,int numVars){
        pw.println("//function "+functionName+" "+ numVars);
        pw.println("("+functionName+")");
        for (int k = 0; k < numVars; k++) {
            writePushPop("C_PUSH","constant",0);
        }
    }
    public void writeCall(String functionName, int numArgs){
       pw.println("//call "+functionName+ " " + numArgs);
       assembly = "@"+fileName+"$ret."+ i +"\nD=A\n@SP\nA=M\nM=D\n@SP\nM=M+1\n" + "@LCL\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n" + "@ARG\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n"+"@THIS\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n"+"@THAT\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n"+"@"+numArgs+"\nD=A\n@5\nD=D+A\n@SP\nD=M-D\n@ARG\nM=D\n" + "\n@SP\nD=M\n@LCL\nM=D\n"+"@"+functionName+"\n0;JMP\n("+fileName+"$ret."+i+++")";
       pw.println(assembly);
    }
    public void writeReturn(){
        pw.println("//return");
        assembly = "@LCL\nD=M\n@R13\nM=D\n@R13\nD=M\n@5\nA=D-A\nD=M\n@R14\nM=D\n@SP\nAM=M-1\nD=M\n@ARG\nA=M\nM=D\n"+"@ARG\nD=M\n@SP\nM=D+1\n@R13\nD=M\n@1\nA=D-A\nD=M\n@THAT\nM=D\n@R13\nD=M\n@2\nA=D-A\nD=M\n@THIS\nM=D\n@R13\nD=M\n@3\nA=D-A\nD=M\n@ARG\nM=D\n@R13\nD=M\n@4\nA=D-A\nD=M\n@LCL\nM=D\n@R14\nA=M\n0;JMP";
        pw.println(assembly);
    }


    public void close(){
        pw.close();
    }
}