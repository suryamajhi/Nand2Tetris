import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class JackTokenizer {
    File[] files;
    Scanner[] scanners;
    Scanner scanner;
    TokenizerXMLWriter writer;

    ArrayList<String> keyWords;
    ArrayList<Character> symbols;

    public JackTokenizer(File file) throws FileNotFoundException {

        symbols = new ArrayList<>();
        keyWords = new ArrayList<>();
        initializeSymbolSet();
        initializeKeyWordSet();

        retrieveFiles(file);
        produceScanners();


        for (int i = 0; i < scanners.length; i++) {
            scanner = scanners[i];
            writer = new TokenizerXMLWriter(files[i]);
            advance();
            writer.close();

            new CompilationEngine(new File(writer.xmlFile));
        }

    }
    private void advance(){
        String line, tokenType;
        StringBuilder token;
        char ch;
        while(scanner.hasNextLine()){
            line = scanner.nextLine();
            line = line.trim();
            if(line.startsWith("//")) continue;
            if(line.startsWith("/**") && line.endsWith("*/")) continue;
            if(line.startsWith("/**")){
                while(!(line = scanner.nextLine()).endsWith("*/")){

                }
                continue;
            }

            if(line.contains("//")){
                line = line.substring(0,line.indexOf("//"));
            }
            token = new StringBuilder();
            for (int i = 0; i < line.length(); i++) {

                //This part is for handling string constant
                if((line.charAt(i)) == '\"'){
                    for (int j = i + 1; (ch = line.charAt(j)) != '\"' ; j++,i++) {
                        token.append(ch);
                    }
                    writer.writeString(token.toString());
                    token.delete(0,token.length());
                    i = i + 2;
                }

                if(!((ch = line.charAt(i)) == ' ' || symbols.contains(ch))){
                    token.append(ch);
                }else{
                    tokenType = tokenType(token);
                    switch (tokenType){
                        case "KEYWORD": writer.writeKeyWord(token.toString());break;
                        case "IDENTIFIER": writer.writeIdentifier(token.toString());break;
                        case "INT_CONST" : writer.writeIntVal(Integer.parseInt(token.toString()));break;
                    }
                    if(symbols.contains(ch)) {
                        if(ch == '<') writer.writeSymbol("&lt;");
                        else if(ch == '>') writer.writeSymbol("&gt;");
                        else if(ch == '&') writer.writeSymbol("&amp;");
                        else writer.writeSymbol(ch);
                    }
                    token.delete(0,token.length());
                }
            }
        }
    }
    private String tokenType(StringBuilder token){
        if(keyWords.contains(token.toString())) return "KEYWORD";
        if(token.toString().matches("\\d+")){
            return "INT_CONST";
        }
        if(token.toString().matches("\\D\\w*")) return "IDENTIFIER";
        return "";
    }


    /**
     * These are array lists containing all the symbols
     */

    private void initializeSymbolSet(){
        symbols.add('}');
        symbols.add('{');
        symbols.add('(');
        symbols.add(')');
        symbols.add('[');
        symbols.add(']');
        symbols.add('.');
        symbols.add(',');
        symbols.add(';');
        symbols.add('+');
        symbols.add('-');
        symbols.add('*');
        symbols.add('/');
        symbols.add('&');
        symbols.add('|');
        symbols.add('<');
        symbols.add('>');
        symbols.add('=');
        symbols.add('~');
    }
    private void initializeKeyWordSet(){
        keyWords.add("class");
        keyWords.add("constructor");
        keyWords.add("function");
        keyWords.add("method");
        keyWords.add("field");
        keyWords.add("static");
        keyWords.add("var");
        keyWords.add("int");
        keyWords.add("char");
        keyWords.add("boolean");
        keyWords.add("void");
        keyWords.add("true");
        keyWords.add("false");
        keyWords.add("null");
        keyWords.add("this");
        keyWords.add("let");
        keyWords.add("do");
        keyWords.add("if");
        keyWords.add("else");
        keyWords.add("while");
        keyWords.add("return");
    }




    /**
     * These are just the helper functions used in the program
     */
    private void retrieveFiles(File file){
        if(file.isDirectory()){
            files = file.listFiles(pathname -> pathname.getName().contains(".jack"));
        }else{
            files = new File[1];
            files[0] = file;
        }
    }
    private void produceScanners() throws FileNotFoundException {
        scanners = new Scanner[files.length];
        for (int i = 0; i < scanners.length; i++) {
            scanners[i] = new Scanner(files[i]);
        }
    }

}

//Another class for writing tokens in files
class TokenizerXMLWriter{
    private final PrintWriter pw;
    public String xmlFile;

    public TokenizerXMLWriter(File file) throws FileNotFoundException {
        String fileName = file.getName();
        fileName = fileName.substring(0, fileName.lastIndexOf("."));
        xmlFile = file.getParent() + "/" + fileName + "T.xml";
        System.out.println("File created : " + xmlFile);
        pw = new PrintWriter(new File(xmlFile));
        pw.println("<tokens>");

    }
    public void writeSymbol(char ch){
        pw.println("<symbol> "+ch+" </symbol>");
    }
    public void writeKeyWord(String keyword){
        pw.println("<keyword> " + keyword + " </keyword>");
    }
    public void writeIdentifier(String identifier){
        pw.println("<identifier> " + identifier + " </identifier>");
    }
    public void writeIntVal(int integer){
        pw.println("<integerConstant> "+ integer + " </integerConstant>");
    }
    public void writeString(String token){
        pw.println("<stringConstant> "+ token + " </stringConstant>");
    }
    public void writeSymbol(String ch){
        pw.println("<symbol> "+ch+" </symbol>");
    }

    public void close(){
        pw.println("</tokens>");
        pw.close();
    }

}
