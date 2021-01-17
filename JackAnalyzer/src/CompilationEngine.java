import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class CompilationEngine {
    PrintWriter writer;
    String fileName,pathName;
    Scanner scanner;
    String line;
    ArrayList<String> operators;
    public CompilationEngine(File file) throws FileNotFoundException {
        fileName = file.getName();
        fileName = fileName.substring(0, fileName.lastIndexOf("T.xml"));
        pathName = file.getParent() + "/" + fileName + ".xml";
        writer = new PrintWriter(new File(pathName));
        System.out.println(pathName);
        scanner = new Scanner(file);
        initializeOperator();
        scanner.nextLine();
        compileClass();
        close();
    }

    private void compileClass(){
        writer.println("<class>");
        writer.println(scanner.nextLine());
        writer.println(scanner.nextLine());
        writer.println(scanner.nextLine());

        line = scanner.nextLine();
        while (line.contains("static") || line.contains("field"))
            compileClassVarDec();


        while(line.contains("constructor") || line.contains("function") || line.contains("method"))
            compileSubroutineDec();

        //Remain to think
        writer.println(line);
        writer.println("</class>");
    }
    private void compileClassVarDec(){
        writer.println("<classVarDec>");
        writer.println(line);               //static/field
        writer.println(scanner.nextLine()); // type
        writer.println(scanner.nextLine()); //varName
        line = scanner.nextLine();
        while(line.contains(",")){
            writer.println(line);             // ,
            writer.println(scanner.nextLine()); // varName;
            line = scanner.nextLine();
        }
        writer.println(line);                    //  ;
        line = scanner.nextLine();           //classvardec or constructor
        writer.println("</classVarDec>");
    }
    private void compileSubroutineDec(){            //need line and throw line

        writer.println("<subroutineDec>");
        writer.println(line);                  // constructor/function/method
        writer.println(scanner.nextLine());     // void/type
        writer.println(scanner.nextLine());     // subroutine name
        writer.println(scanner.nextLine());      //    (
        compileParameterList();
        writer.println(line);      //      )
        compileSubroutineBody();
        line = scanner.nextLine();
        writer.println("</subroutineDec>");

    }

    private void compileParameterList(){

        writer.println("<parameterList>");
        while(!(line = scanner.nextLine()).contains(")")){
            writer.println(line);
        }
        writer.println("</parameterList>");
    }

    private void compileSubroutineBody(){

        writer.println("<subroutineBody>");
        writer.println(scanner.nextLine());   //  {
        line = scanner.nextLine();
        while(line.contains("var")){
            compileVarDec();
            line = scanner.nextLine();
        }
        compileStatements();
        //remain to think
        writer.println(line);   //   }



        writer.println("</subroutineBody>");

    }

    private void compileVarDec(){

        writer.println("<varDec>");
        writer.println(line);                   //var
        writer.println(scanner.nextLine());     //   type
        writer.println(scanner.nextLine());      //varName
        line = scanner.nextLine();
        while(!line.contains(";")){
            writer.println(line);
            line = scanner.nextLine();
        }
        writer.println(line);                     //  ;
        writer.println("</varDec>");
    }

    private void compileStatements(){//need line   throw line

        writer.println("<statements>");
        while( !line.contains("}")){
            if(line.contains("let")) compileLet();
            if(line.contains("if")) compileIf();
            if(line.contains("while")) compileWhile();
            if(line.contains("do")) compileDo();
            if(line.contains("return")) compileReturn();
        }
        writer.println("</statements>");

    }
    private void compileLet(){            //need line  and does not throw a line

        writer.println("<letStatement>");
        writer.println(line);                    // let
        writer.println(scanner.nextLine());       //     var Name
        line = scanner.nextLine();
        if(line.contains("[")){
            writer.println(line);                  //            [
            line = scanner.nextLine();
            compileExpression();
            writer.println(line);                   // ]
            line = scanner.nextLine();
        }
        writer.println(line);       // =
        line = scanner.nextLine();
        compileExpression();
        writer.println(line);                    // ;
        line = scanner.nextLine();
        writer.println("</letStatement>");
    }

    private void compileIf(){

        writer.println("<ifStatement>");
        writer.println(line);                       //if
        writer.println(scanner.nextLine());          // (
        line = scanner.nextLine();
        compileExpression();
        writer.println(line);                      //  )
        writer.println(scanner.nextLine());         //{
        line = scanner.nextLine();
        compileStatements();
        writer.println(line);                        //}
        line = scanner.nextLine();
        if(line.contains("else")){
            writer.println(line);                  //else

            writer.println(scanner.nextLine());          // {
            line = scanner.nextLine();
            compileStatements();
            writer.println(line);             // }
            line = scanner.nextLine();

        }

        writer.println("</ifStatement>");
    }

    private void compileWhile(){

        writer.println("<whileStatement>");
        writer.println(line);
        writer.println(scanner.nextLine());
        line = scanner.nextLine();
        compileExpression();
        writer.println(line);                      //   )
        writer.println(scanner.nextLine());        // {
        line = scanner.nextLine();
        compileStatements();
        writer.println(line);                        //   }
        line = scanner.nextLine();
        writer.println("</whileStatement>");
    }

    private void compileDo(){

        writer.println("<doStatement>");
        writer.println(line);
        writer.println(scanner.nextLine());         //identifier
        line = scanner.nextLine();
        if(line.contains(".")){
            writer.println(line);                      // .
            writer.println(scanner.nextLine());         // identifier
            line = scanner.nextLine();
        }

        writer.println(line);         // (
        compileExpressionList();
        writer.println(line);                       //  )
        writer.println(scanner.nextLine());         //  ;
        line = scanner.nextLine();
        writer.println("</doStatement>");
    }

    private void compileReturn(){

        writer.println("<returnStatement>");
        writer.println(line);                               //return
        line = scanner.nextLine();
        if(!line.contains(";")){
            compileExpression();
        }
        writer.println(line);                  // ;
        line = scanner.nextLine();
        writer.println("</returnStatement>");
    }

    private void compileExpression(){              // need line      and throw line

        writer.println("<expression>");
        compileTerm();
        String op = line.substring(line.indexOf('>') + 1,line.lastIndexOf('<') - 1);
        op = op.trim();

        while(operators.contains(op)){
            writer.println(line);                      //  op
            line = scanner.nextLine();

            compileTerm();                             // term

            op = line.substring(line.indexOf('>') + 1,line.lastIndexOf('<') - 1);
            op = op.trim();
        }

        writer.println("</expression>");
    }

    private void compileTerm(){ //need line and throws a line
        writer.println("<term>");
        if(line.contains("-") || line.contains("~")){
            writer.println(line);               // -
            line = scanner.nextLine();
            compileTerm();
            writer.println("</term>");
            return;
        }
        if(line.contains("(")){
            writer.println(line);            //    (
            line = scanner.nextLine();
            compileExpression();

            writer.println(line);             // )
        }else{
            writer.println(line);//varname / constant
        }

        line = scanner.nextLine();
        if(line.contains(".")){
            writer.println(line);
            writer.println(scanner.nextLine());       // identifier
            writer.println(scanner.nextLine());       // (
            compileExpressionList();
            writer.println(line);
            line = scanner.nextLine();
        }

        if(line.contains("[")){
            writer.println(line);
            line = scanner.nextLine();
            compileExpression();
            writer.println(line);
            line = scanner.nextLine();
        }

        writer.println("</term>");


    }

    private void compileExpressionList(){           //does not need line and throws line
        writer.println("<expressionList>");
        line = scanner.nextLine();
        while(!line.contains(")")){
            compileExpression();
            if(line.contains(",")){
                writer.println(line);
                line = scanner.nextLine();
            }
        }
        writer.println("</expressionList>");
    }




    public void close(){
        writer.close();
    }



    private void initializeOperator(){
        operators = new ArrayList<>();
        operators.add("+");
        operators.add("-");
        operators.add("*");
        operators.add("/");
        operators.add("&lt;");
        operators.add("&gt;");
        operators.add("~");
        operators.add("&amp;");
        operators.add("|");
        operators.add("=");
    }
}
