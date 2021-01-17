import java.io.*;
public class Code {
    private final PrintWriter pw;
    private final Table stForComp;
    private final Table stForDest;
    private final Table stForJump;
    private String machineLanguage;


    public Code(String fileName) throws IOException{
        pw = new PrintWriter(new FileWriter(new File(fileName+".hack")));

        stForComp = new Table();
        stForDest = new Table();
        stForJump = new Table();
        fillTables();
    }

    public void aInstruction(String command){
        String sixteenZeros = "000000000000000";
        String aValue = command.substring(1);
        int aValueInt = Integer.parseInt(aValue);
        String binary = Integer.toBinaryString(aValueInt);
        int len = binary.length();
        machineLanguage = sixteenZeros.substring(0,16-len).concat(binary);
        pw.println(machineLanguage);
    }

    public void cInstruction(String dest,String comp,String jump){
        if(stForComp.getValue(comp)==null) System.out.println(comp);
        machineLanguage = "111" + stForComp.getValue(comp) + stForDest.getValue(dest) + stForJump.getValue(jump);
        pw.println(machineLanguage);
    }

    private void fillTables(){
        stForDest.insert("null","000");
        stForDest.insert("M","001");
        stForDest.insert("D","010");
        stForDest.insert("MD","011");
        stForDest.insert("A","100");
        stForDest.insert("AM","101");
        stForDest.insert("AD","110");
        stForDest.insert("AMD","111");

        stForJump.insert("null","000");
        stForJump.insert("JGT","001");
        stForJump.insert("JEQ","010");
        stForJump.insert("JGE","011");
        stForJump.insert("JLT","100");
        stForJump.insert("JNE","101");
        stForJump.insert("JLE","110");
        stForJump.insert("JMP","111");

        stForComp.insert("0","0101010");
        stForComp.insert("1","0111111");
        stForComp.insert("-1","0111010");
        stForComp.insert("D","0001100");
        stForComp.insert("A","0110000");
        stForComp.insert("!D","0001101");
        stForComp.insert("!A","0110001");
        stForComp.insert("-D","0001111");
        stForComp.insert("-A","0110011");
        stForComp.insert("D+1","0011111");
        stForComp.insert("A+1","0110111");
        stForComp.insert("D-1","0001110");
        stForComp.insert("A-1","0110010");
        stForComp.insert("D+A","0000010");
        stForComp.insert("D-A","0010011");
        stForComp.insert("A-D","0000111");
        stForComp.insert("D&A","0000000");
        stForComp.insert("D|A","0010101");
        stForComp.insert("M","1110000");
        stForComp.insert("!M","1110001");
        stForComp.insert("-M","1110011");
        stForComp.insert("M+1","1110111");
        stForComp.insert("M-1","1110010");
        stForComp.insert("D+M","1000010");
        stForComp.insert("D-M","1010011");
        stForComp.insert("M-D","1000111");
        stForComp.insert("D&M","1000000");
        stForComp.insert("D|M","1010101");
    }

    public void close(){
        pw.close();
    }
}
