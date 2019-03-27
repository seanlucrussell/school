import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;

public class EquationDriver {
	public static void main(String[] args) throws FileNotFoundException, Exception, ParseException{
		Scanner scan = new Scanner( new File (args[0]));
        String line;
        boolean debug = false;
        if(args.length > 1)
        	debug = true;
        // initialize symTab
        Hashtable<String,Boolean> symbolTable = new Hashtable<String,Boolean>();
        // loop through lines in input file
        while ( scan.hasNextLine()){
        	line = scan.nextLine();
        	System.out.println("next line: " + line);
        	try{
        	TokenIter tokIt = new TokenIter(line);
        	Equation nextEq = new Equation(tokIt, debug);
    		String res = nextEq.line(symbolTable);
    	        System.out.println("result: " + symbolTable.get(res));
        	} catch (ParseException PE) {
        		System.out.println("Parse Exception caught: " + PE.getMessage());
        	} catch (Exception PE) {
    		System.out.println("Exception caught: " + PE.getMessage());
    	    } 
	
    		if(debug){
    			System.out.println("SYMBOL TABLE");
    			for(String key:symbolTable.keySet()){
    				System.out.println(key+" = "+symbolTable.get(key));
    			}
    			System.out.println("END SYMBOL TABLE");
    		}
        }
	}
}
