import java.util.TreeMap;
public class SymbolTable {
    private final TreeMap<String,Integer> symbolTable;

    public SymbolTable(){
        symbolTable = new TreeMap<>();
        defaultValue();
    }

    public void insert(String key,Integer value){
        symbolTable.put(key, value);
        System.out.println(key+" "+ value);
    }
    public Integer getValue(String key){
        return symbolTable.get(key);
    }
    public boolean isPresent(String key){
        return symbolTable.containsKey(key);
    }

    public void defaultValue(){
        insert("R0",0);
        insert("R1",1);
        insert("R2",2);
        insert("R3",3);
        insert("R4",4);
        insert("R5",5);
        insert("R6",6);
        insert("R8",7);
        insert("R7",8);
        insert("R9",9);
        insert("R10",10);
        insert("R11",11);
        insert("R12",12);
        insert("R13",13);
        insert("R14",14);
        insert("R15",15);
        insert("SCREEN",16384);
        insert("KBD",24576);
        insert("SP",0);
        insert("LCL",1);
        insert("ARG",2);
        insert("THIS",3);
        insert("THAT",4);
    }
}
class Table{
    private final TreeMap<String,String> table;

    public Table(){
        table = new TreeMap<>();
    }
    public void insert(String key,String value){
        table.put(key,value);
    }
    public String getValue(String key){
        return table.get(key);
    }
}
