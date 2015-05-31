package Tokenizer;

import java.util.ArrayList;
import java.util.Stack;

public class LL1Parser {



public Token curentToken=null;
public int tokenNumber=0;
public ArrayList<Token> tokenList=new ArrayList<Token>();
public Boolean parseResult=null;
public int blockNumber=-1;
public int offset=-1;
public boolean leftRoundBracketBeforeInputParametersSeen= false;
public boolean leftAngularBracketBeforeOutputParametersSeen= false;
public int returnVariableNumber=1;
public int arithmaticTemporaryNumber=1;
public Stack arithmaticStack=new Stack();
public Stack variableStack=new Stack();
public boolean assignmentStatementStarts=false;
        boolean insideFunctionCall=false;
        boolean insideReturnStatement=false;
//array list will be the symbol table
private ArrayList <SymbolTableEntry> symbolTable=new ArrayList <SymbolTableEntry>(); 
//starts with index 0
public int symbolTableCounter=0;//next available position for quad
//getter and setter for symbol table
public ArrayList<SymbolTableEntry> getSymbolTable() {
return symbolTable;
}

public void setSymbolTable(ArrayList<SymbolTableEntry> symbolTable) {
this.symbolTable = symbolTable;
}

   //array list will be the symbol table
private ArrayList <QuadEntry> quadTable=new ArrayList <QuadEntry>();
//starts with index 0
public int quadCounter=0;//next available position for quad
public ArrayList<QuadEntry> getQuadTable() {
return quadTable;
}

public void setQuadTable(ArrayList<QuadEntry> quadTable) {
this.quadTable = quadTable;
}

public void printSymbolTable()
{
System.out.println("\n\n following is the symbol table");
for(int i=0;i<symbolTable.size();i++)
{
//System.out.println(i+"- ");
System.out.println(symbolTable.get(i).toString());// - uncomment to see the tokens
}
}
public void printQuadTable()
{
System.out.println("\n\n following is the quad table");
for(int i=0;i<quadTable.size();i++)
{
//
//System.out.println(i+"- ");
System.out.println(quadTable.get(i).toString());
}
}
//main parser function
public  boolean parser(ArrayList<Token> tokenListInp)
{
Token tokenLast=new Token("$","$");
tokenListInp.add(tokenLast);



tokenList=tokenListInp;


parseResult=program();
this.printSymbolTable();
this.printQuadTable();
return parseResult;

}

 
// <program>
    public boolean program() 
    {
   
    //inserting the first quad starts
    QuadEntry quadEntryInitial=new QuadEntry(quadCounter,"jump", null, null, null);
quadTable.add(quadEntryInitial);
quadCounter++;
    //inserting the first quad ends
   
        if ((tokenList.get(tokenNumber).getTokenType()
                .equals("FUNCTION_IDENTIFIER"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("KEYWORD_FORWARD")))
        {
            if (func() == true)
            {
                if (func_list() == true)
                {
                    return true;
                } else 
                {
                    return false;
                }
            } else
            {
                return false;
            }
        } else
        {
            return false;
        }
    }// end of function

    // <func_list>
    public boolean func_list() {
        if ((tokenList.get(tokenNumber).getTokenType()
                .equals("FUNCTION_IDENTIFIER"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("KEYWORD_FORWARD")))
        {
            if (func() == true) 
            {
                if (func_list() == true)
                {
                    return true;
                } else
                {
                    return false;
                }
            } 
            else 
            {
                return false;
            }
        }

        // epsilon move
        else if ((tokenList.get(tokenNumber).getTokenType().equals("$"))) {
            return true;
        } else {
            return false;
        }
    }// end of function

    // <func>
    public boolean func() {
        if ((tokenList.get(tokenNumber).getTokenType()
                .equals("FUNCTION_IDENTIFIER")))// first rule
        {
         int procEntryExitSymbolTableIndex=-1;
        //entry in symbol table for the function name starts
        SymbolTableEntry symbolTableEntry=new 
        SymbolTableEntry(symbolTableCounter,tokenList.get(tokenNumber).getToken(), "proc",
        null, null, null, quadCounter);
        symbolTable.add(symbolTableEntry);
        blockNumber=symbolTableCounter;//setting the block number
        offset=17;//setting the offset
       
        symbolTableCounter++;
        //entry in symbol table for the function name starts
       
        //entry in quad table for the function name starts
        QuadEntry quadEntry=new QuadEntry(quadCounter,"procentry",""+(symbolTable.size()-1) , null, null);
        procEntryExitSymbolTableIndex=symbolTable.size()-1;
        quadTable.add(quadEntry);
        quadCounter++;
        //entry in quad table for the function name ends
       
       
            tokenNumber++;
            if ((tokenList.get(tokenNumber).getTokenType()
                    .equals("LEFT_ROUND_BRACKET"))) {
            //I have seen a left round bracket
            leftRoundBracketBeforeInputParametersSeen=true;
           
                tokenNumber++;
                if (vars() == true) {
                    if ((tokenList.get(tokenNumber).getTokenType()
                            .equals("RIGHT_ROUND_BRACKET"))) {
                    //again setting left round bracket seen to false
                    leftRoundBracketBeforeInputParametersSeen=false;
                   
                   
                        tokenNumber++;
                        if ((tokenList.get(tokenNumber).getTokenType()
                                .equals("ARROW_SYMBOL"))) {
                            tokenNumber++;
                            if ((tokenList.get(tokenNumber).getTokenType()
                                    .equals("LEFT_ANGULAR_BRACKET"))) {
                            
leftAngularBracketBeforeOutputParametersSeen=true;
                                tokenNumber++;
                                if (return_list() == true) {
                                    if ((tokenList.get(tokenNumber).getTokenType()
                                            .equals("RIGHT_ANGULAR_BRACKET"))) {
                                    
leftAngularBracketBeforeOutputParametersSeen=false;
                                        tokenNumber++;
                                        if ((tokenList.get(tokenNumber)
                                                .getTokenType()
                                                .equals("LEFT_CURLY_BRACKET"))) {
                                            tokenNumber++;
                                            if (var_decs() == true) {
                                                if (block() == true) {
                                                    if ((tokenList.get(tokenNumber)
                                                            .getTokenType()
                                                            .equals("RIGHT_CURLY_BRACKET"))) {
                                                    	
                                                    	
                                                    	 //entry in quad table for proc Exit starts
                                                        QuadEntry quadEntry2=new QuadEntry(quadCounter,"procexit",""+procEntryExitSymbolTableIndex , null, null);
                                                        procEntryExitSymbolTableIndex=-1;
                                                        quadTable.add(quadEntry2);
                                                        quadCounter++;
                                                        //entry in quad table for proc Exit starts
                                                    	
                                                    
//setting back the block number and offset
                                                    
offset=-1;
                                                    
blockNumber=-1;
                                                    
                                                    
                                                        tokenNumber++;
                                                        return true;
                                                    } else {
                                                        return false;
                                                    }
                                                } else {
                                                    return false;
                                                }
                                            } else {
                                                return false;
                                            }
                                        } else {
                                            return false;
                                        }
                                    } else {
                                        return false;
                                    }
                                } else {
                                    return false;
                                }

                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } else if ((tokenList.get(tokenNumber).getTokenType()
                .equals("KEYWORD_FORWARD"))) {
            tokenNumber++;
            if ((tokenList.get(tokenNumber).getTokenType()
                    .equals("FUNCTION_IDENTIFIER"))) {
                tokenNumber++;
                if ((tokenList.get(tokenNumber).getTokenType()
                        .equals("LEFT_ROUND_BRACKET"))) {
                    tokenNumber++;
                    if (vars() == true) {
                        if ((tokenList.get(tokenNumber).getTokenType()
                                .equals("RIGHT_ROUND_BRACKET"))) {
                            tokenNumber++;
                            if ((tokenList.get(tokenNumber).getTokenType()
                                    .equals("ARROW_SYMBOL"))) {
                                tokenNumber++;
                                if ((tokenList.get(tokenNumber).getTokenType()
                                        .equals("LEFT_ANGULAR_BRACKET"))) {
                                    tokenNumber++;
                                    if (return_list() == true) {
                                        if ((tokenList.get(tokenNumber)
                                                .getTokenType()
                                                .equals("RIGHT_ANGULAR_BRACKET"))) {
                                            tokenNumber++;
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    } else {
                                        return false;
                                    }
                                } else {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }// end of function

    // < vars>
    public boolean vars() {

        if (((tokenList.get(tokenNumber).getTokenType().equals("INT_DATATYPE")))
                || ((tokenList.get(tokenNumber).getTokenType()
                        .equals("CHAR_DATATYPE")))
                || ((tokenList.get(tokenNumber).getTokenType()
                        .equals("BOOL_DATATYPE")))) {

            if (var_dec() == true) {
                if (vars_tail() == true) {
                    return true;
                }

                else {
                    return false;
                }
            } else {
                return false;
            }
        }
        // epsilon move

        else if (((tokenList.get(tokenNumber).getTokenType()
                .equals("RIGHT_ROUND_BRACKET")))
                || ((tokenList.get(tokenNumber).getTokenType().equals("SEMICOLON")))) {
            return true;

        } else {
            return false;
        }

    }// end of function

    // <var_dec>
    public boolean var_dec() {
   
        if (((tokenList.get(tokenNumber).getTokenType().equals("INT_DATATYPE")))
                || ((tokenList.get(tokenNumber).getTokenType()
                        .equals("CHAR_DATATYPE")))
                || ((tokenList.get(tokenNumber).getTokenType()
                        .equals("BOOL_DATATYPE")))) {
        String dataType=tokenList.get(tokenNumber).getToken();
       
            if (type() == true)

            {
                if ((tokenList.get(tokenNumber).getTokenType()
                        .equals("VARIABLE_IDENTIFIER"))) {
                String var_name=tokenList.get(tokenNumber).getToken();
               
                //making entry in symbol table for input variable starts
                if(leftRoundBracketBeforeInputParametersSeen==true)
                {
                SymbolTableEntry symbolTableEntry=new SymbolTableEntry
                (symbolTableCounter, var_name, "IP", dataType, blockNumber, offset, null);
                symbolTable.add(symbolTableEntry);
                offset++;//to increase the offset
                symbolTableCounter++;
                }
                //making entry in symbol table for input variable ends
               
               
                //making entry in symbol table for local variable starts
                if(leftRoundBracketBeforeInputParametersSeen==false)
                {
                SymbolTableEntry symbolTableEntry=new SymbolTableEntry
                (symbolTableCounter, var_name, "var", dataType, blockNumber, offset, null);
                symbolTable.add(symbolTableEntry);
                offset++;//to increase the offset
                symbolTableCounter++;
                }
                //making entry in symbol table for local variable ends
               
                    
                tokenNumber++;
                    if (array_dec() == true) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }

    }// end of function

    // <var_tail>
    public boolean vars_tail() {
        if ((tokenList.get(tokenNumber).getTokenType().equals("COMMA"))) {
            tokenNumber++;
            if (var_dec() == true) {
                if (vars_tail() == true) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        // epsilon move
        else if (((tokenList.get(tokenNumber).getTokenType()
                .equals("RIGHT_ROUND_BRACKET")))
                || ((tokenList.get(tokenNumber).getTokenType().equals("SEMICOLON"))))

        {
            return true;
        } else// no match
        {
            return false;
        }

    }// end of function

    // <var_decs>
    public boolean var_decs() {
        if (((tokenList.get(tokenNumber).getTokenType().equals("INT_DATATYPE")))
                || ((tokenList.get(tokenNumber).getTokenType()
                        .equals("CHAR_DATATYPE")))
                || ((tokenList.get(tokenNumber).getTokenType()
                        .equals("BOOL_DATATYPE")))) {
            if (vars() == true) {
                if ((tokenList.get(tokenNumber).getTokenType().equals("SEMICOLON"))) {
                    tokenNumber++;
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        // epsilon move
        else if (((tokenList.get(tokenNumber).getTokenType()
                .equals("VARIABLE_IDENTIFIER"))
                || (tokenList.get(tokenNumber).getTokenType().equals("KEYWORD_IF"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("KEYWORD_WHILE"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("KEYWORD_WRITE"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("KEYWORD_READ"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("KEYWORD_RETURN")) || (tokenList.get(tokenNumber)
                .getTokenType().equals("FUNCTION_IDENTIFIER")))) {
            return true;
        } else {
            return false;
        }

    }// end of function

    // <array_dec>
    public boolean array_dec() {

        if (((tokenList.get(tokenNumber).getTokenType()
                .equals("LEFT_SQUARE_BRACKET"))))

        {
            tokenNumber++;
            if (((tokenList.get(tokenNumber).getTokenType()
                    .equals("INTEGER_LITERAL")))) {
                tokenNumber++;
                if (((tokenList.get(tokenNumber).getTokenType()
                        .equals("RIGHT_SQUARE_BRACKET")))) {
                    tokenNumber++;
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        // epsilon move
        else if (((tokenList.get(tokenNumber).getTokenType()
                .equals("RIGHT_ROUND_BRACKET")))
                || ((tokenList.get(tokenNumber).getTokenType().equals("COMMA")))
                || ((tokenList.get(tokenNumber).getTokenType().equals("SEMICOLON"))))

        {
            return true;
        } else//
        {
            return false;
        }

    }// end of function

    // <return_list>
    public boolean return_list() {
        if (((tokenList.get(tokenNumber).getTokenType().equals("INT_DATATYPE")))
                || ((tokenList.get(tokenNumber).getTokenType()
                        .equals("CHAR_DATATYPE")))
                || ((tokenList.get(tokenNumber).getTokenType()
                        .equals("BOOL_DATATYPE")))) {
            if (type() == true) {
                if (return_tail() == true) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else if (((tokenList.get(tokenNumber).getTokenType()
                .equals("RIGHT_ANGULAR_BRACKET"))))// epsilon move
        {
            return true;
        } else {
            return false;
        }

    }// end of function

    // <return_tail>
    public boolean return_tail() {
        if (((tokenList.get(tokenNumber).getTokenType().equals("COMMA")))) {
            tokenNumber++;
            if (type() == true) {
                if (return_tail() == true) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else if (((tokenList.get(tokenNumber).getTokenType()
                .equals("RIGHT_ANGULAR_BRACKET"))))// epsilon move
        {
            return true;
        } else {
            return false;
        }
    }// end of function

    // < type>
    public boolean type() {
        if (((tokenList.get(tokenNumber).getTokenType().equals("INT_DATATYPE")))
                || ((tokenList.get(tokenNumber).getTokenType()
                        .equals("CHAR_DATATYPE")))
                || ((tokenList.get(tokenNumber).getTokenType()
                        .equals("BOOL_DATATYPE")))) {
            tokenNumber++;
            //symbol table entry for return type starts
           
        if(leftAngularBracketBeforeOutputParametersSeen==true)
        {
        String var_type=tokenList.get(tokenNumber).getToken();
                 String var_name="#returnVariable"+returnVariableNumber;
        SymbolTableEntry symbolTableEntry=new SymbolTableEntry
        (symbolTableCounter, var_name, "OP", var_type, blockNumber, offset, null);
        symbolTable.add(symbolTableEntry);
        offset++;//to increase the offset
        symbolTableCounter++;
        returnVariableNumber++;
        }
       
            //symbol table entry for return type ends
            return true;
        } else {
            return false;
        }

    }// end of function

    // <block>
    public boolean block() {
        if ((tokenList.get(tokenNumber).getTokenType()
                .equals("VARIABLE_IDENTIFIER"))
                || (tokenList.get(tokenNumber).getTokenType().equals("KEYWORD_IF"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("KEYWORD_WHILE"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("KEYWORD_WRITE"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("KEYWORD_READ"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("KEYWORD_RETURN"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("FUNCTION_IDENTIFIER")))

        {
            if (stmt() == true) {
                if (block() == true) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else if (((tokenList.get(tokenNumber).getTokenType()
                .equals("RIGHT_CURLY_BRACKET"))))// epsilon
        {
            return true;
        } else {
            return false;
        }
    }// end of function

    public boolean stmt() // <var_list> := <arg_list>;
    {
        if(tokenList.get(tokenNumber).getTokenType().equals("VARIABLE_IDENTIFIER"))
        {
        assignmentStatementStarts=true;
        if (var_list() == true) {
            if (tokenList.get(tokenNumber).getTokenType()
                    .equals("ASSIGNMENT_OPERATOR")) {
                tokenNumber++;
                if (arg_list() == true) {
                    if (tokenList.get(tokenNumber).getTokenType()
                            .equals("SEMICOLON")) {
                    assignmentStatementStarts=false;
                    //test code starts
                    /*while(!arithmaticStack.isEmpty())
                    {
                    System.out.println("value in arithmatic stack is -"+arithmaticStack.pop());
                    }
                    while(!variableStack.isEmpty())
                    {
                    System.out.println("value in variable stack is -"+variableStack.pop());
                    }*/
                    //test code ends
                   
                    //code for assignment quads starts
                    while(!arithmaticStack.isEmpty()  &&  !variableStack.isEmpty())
                    {
                    String variable=(String) variableStack.pop();
                    String value= (String) arithmaticStack.pop();
                    QuadEntry quadEntry=new QuadEntry(quadCounter, "assign", value, null, variable);
                        
quadTable.add(quadEntry);
                        
quadCounter++;
                   
                    }
                   
                    //code for assignment quads ends
                        tokenNumber++;
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        else
        {
            return false;
        }
        }
        // if (<expr>){<block>}<else tail>

        else if (tokenList.get(tokenNumber).getTokenType().equals("KEYWORD_IF")) {
            tokenNumber++;

            if (tokenList.get(tokenNumber).getTokenType()
                    .equals("LEFT_ROUND_BRACKET")) {
                tokenNumber++;
                if (expr() == true) {

                    if (tokenList.get(tokenNumber).getTokenType()
                            .equals("RIGHT_ROUND_BRACKET")) {
                        tokenNumber++;
                        if (tokenList.get(tokenNumber).getTokenType()
                                .equals("LEFT_CURLY_BRACKET")) {
                            tokenNumber++;
                            if (block() == true) {

                                if (tokenList.get(tokenNumber).getTokenType()
                                        .equals("RIGHT_CURLY_BRACKET")) {
                                    tokenNumber++;
                                    if (else_tail() == true) {
                                        return true;
                                    } else {
                                        return false;
                                    }
                                } else {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }

                }

                else {
                    return false;
                }
            } else {
                return false;
            }
        }

        // while(<expr>){<block>}

        else if (tokenList.get(tokenNumber).getTokenType().equals("KEYWORD_WHILE")) {
            tokenNumber++;
            if (tokenList.get(tokenNumber).getTokenType()
                    .equals("LEFT_ROUND_BRACKET")) {
                tokenNumber++;
                if (expr() == true) {
                    if (tokenList.get(tokenNumber).getTokenType()
                            .equals("RIGHT_ROUND_BRACKET")) {
                        tokenNumber++;
                        if (tokenList.get(tokenNumber).getTokenType()
                                .equals("LEFT_CURLY_BRACKET")) {
                            tokenNumber++;
                            if (block() == true) {

                                if (tokenList.get(tokenNumber).getTokenType()
                                        .equals("RIGHT_CURLY_BRACKET")) {
                                    tokenNumber++;
                                    return true;
                                } else {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }

                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }

        }
        // write(<write_list>);

        else if (tokenList.get(tokenNumber).getTokenType().equals("KEYWORD_WRITE")) {
            tokenNumber++;
            if (tokenList.get(tokenNumber).getTokenType()
                    .equals("LEFT_ROUND_BRACKET")) {
                tokenNumber++;
                if (write_list() == true) {
                    if (tokenList.get(tokenNumber).getTokenType()
                            .equals("RIGHT_ROUND_BRACKET")) {
                        tokenNumber++;
                        if (tokenList.get(tokenNumber).getTokenType()
                                .equals("SEMICOLON")) {
                            tokenNumber++;
                            return true;

                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }

                } else {
                    return false;
                }
            } else {
                return false;
            }

        }

        // read(<var_list>);

        else if (tokenList.get(tokenNumber).getTokenType().equals("KEYWORD_READ")) {
            tokenNumber++;
            if (tokenList.get(tokenNumber).getTokenType()
                    .equals("LEFT_ROUND_BRACKET")) {
                tokenNumber++;
                if (var_list() == true) {
                    if (tokenList.get(tokenNumber).getTokenType()
                            .equals("RIGHT_ROUND_BRACKET")) {
                        tokenNumber++;
                        if (tokenList.get(tokenNumber).getTokenType()
                                .equals("SEMICOLON")) {
                            tokenNumber++;
                            return true;
                        } else {
                            return false;
                        }

                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        // return <arg_list> ;

        else if (tokenList.get(tokenNumber).getTokenType()
                .equals("KEYWORD_RETURN")) {
            insideReturnStatement=true;
            tokenNumber++;
            if (arg_list() == true) {
                if (tokenList.get(tokenNumber).getTokenType().equals("SEMICOLON")) {
                    insideReturnStatement=false;
                    tokenNumber++;
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        // <function_call>;

        else if (function_call() == true) {
            if (tokenList.get(tokenNumber).getTokenType().equals("SEMICOLON")) {
                tokenNumber++;
                return true;
            } else {
                return false;
            }
        }

        else {
            return false;
        }

    }// end of function

    public boolean var_list() // <var_ref><var_tail>
    {
        if (tokenList.get(tokenNumber).getTokenType().equals("VARIABLE_IDENTIFIER"))
        {
        if(assignmentStatementStarts==true)
        {
        //code to push the variable on the variableStack starts
        String variableName=tokenList.get(tokenNumber).getToken();
        String variableIndex=getIndexForSymbolFromSymbolTable(variableName);
        variableStack.push(variableIndex);
        //code to push the variable on the variableStack ends
        }
       
        if (var_ref() == true) {
            if (var_tail() == true) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
        }
        else
        {
            return false;
        }

    } // end of function

    public boolean var_tail() // ,<var_list>
    {
        if (tokenList.get(tokenNumber).getTokenType().equals("COMMA")) {
            tokenNumber++;
            if (var_list() == true) {
                return true;
            } else {
                return false;
            }
        }
        // epsilon: :=,)

        else if ((tokenList.get(tokenNumber).getTokenType()
                .equals("ASSIGNMENT_OPERATOR"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("RIGHT_ROUND_BRACKET")))

        {
            return true;
        } else {
            return false;
        }

    } // end of function

    public boolean var_ref() // <<var_id>><array_ref>
    {

        if (tokenList.get(tokenNumber).getTokenType()
                .equals("VARIABLE_IDENTIFIER")) {
            tokenNumber++;
            if (array_ref() == true) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    } // end of function

    public boolean array_ref() // [<expr>]
    {
        if (tokenList.get(tokenNumber).getTokenType()
                .equals("LEFT_SQUARE_BRACKET")) {
            tokenNumber++;
            if (expr() == true) {
                if (tokenList.get(tokenNumber).getTokenType()
                        .equals("RIGHT_SQUARE_BRACKET")) {
                    tokenNumber++;
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        // epsilon move ^, %, *, /, +, -, <, >, <=, >=, =, !=, |, &, ), ],
        // comma, ;, :=

        else if ((tokenList.get(tokenNumber).getTokenType().equals("SYMBOL_POWER"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_PERCENTAGE"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_MULTIPLY"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_DIVISION"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_PLUS"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_MINUS"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_LESS_THAN"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_GREATER_THAN"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_LESS_THAN_EQUAL_TO"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_GREATER_THAN_EQUAL_TO"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_EQUAL_TO"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_NOT_EQUAL_TO"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("PIPE_SYMBOL"))
                || (tokenList.get(tokenNumber).getTokenType().equals("AND_SYMBOL"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("RIGHT_ROUND_BRACKET"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("RIGHT_SQUARE_BRACKET"))
                || (tokenList.get(tokenNumber).getTokenType().equals("COMMA"))
                || (tokenList.get(tokenNumber).getTokenType().equals("SEMICOLON"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("ASSIGNMENT_OPERATOR"))) {
            return true;

        } else {
            return false;
        }

    } // end of function

    public boolean else_tail() // else{<block>}
    {

        if (tokenList.get(tokenNumber).getTokenType().equals("KEYWORD_ELSE")) {
            tokenNumber++;
            if (tokenList.get(tokenNumber).getTokenType()
                    .equals("LEFT_CURLY_BRACKET")) {
                tokenNumber++;
                if (block() == true) {
                    if (tokenList.get(tokenNumber).getTokenType()
                            .equals("RIGHT_CURLY_BRACKET")) {
                        tokenNumber++;
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }

        }

        // epsilon move << var_id>>, if, while, write, read, return, <<id>>, }
        else if ((tokenList.get(tokenNumber).getTokenType()
                .equals("VARIABLE_IDENTIFIER"))
                || (tokenList.get(tokenNumber).getTokenType().equals("KEYWORD_IF"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("KEYWORD_WHILE"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("KEYWORD_WRITE"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("KEYWORD_READ"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("KEYWORD_RETURN"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("FUNCTION_IDENTIFIER"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("RIGHT_CURLY_BRACKET"))) {
            return true;
        } else {
            return false;
        }

    }// end of function

    public boolean arg_list() // <arg><arg_tail>
    {
        if ((tokenList.get(tokenNumber).getTokenType().equals("CHAR_LITERAL"))||
                (tokenList.get(tokenNumber).getTokenType().equals("VARIABLE_IDENTIFIER"))||
                (tokenList.get(tokenNumber).getTokenType().equals("INTEGER_LITERAL"))||
                (tokenList.get(tokenNumber).getTokenType().equals("BOOL_TRUE"))||
                (tokenList.get(tokenNumber).getTokenType().equals("BOOL_FALSE"))||
                (tokenList.get(tokenNumber).getTokenType().equals("SYMBOL_NOT_EQUAL_TO"))||
                (tokenList.get(tokenNumber).getTokenType().equals("LEFT_ROUND_BRACKET"))||
                (tokenList.get(tokenNumber).getTokenType().equals("FUNCTION_IDENTIFIER")))
        {
        if (arg() == true) {
            if (arg_tail() == true) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
        }
        else
        {
            return false;
        }
    } // end of function

    // <<char>>
    public boolean arg() {
        if (tokenList.get(tokenNumber).getTokenType().equals("CHAR_LITERAL")) {
            tokenNumber++;
            return true;
        }

        // <expr>
        else if ((tokenList.get(tokenNumber).getTokenType().equals("VARIABLE_IDENTIFIER"))||
                (tokenList.get(tokenNumber).getTokenType().equals("INTEGER_LITERAL"))||
                (tokenList.get(tokenNumber).getTokenType().equals("BOOL_TRUE"))||
                (tokenList.get(tokenNumber).getTokenType().equals("BOOL_FALSE"))||
                (tokenList.get(tokenNumber).getTokenType().equals("SYMBOL_NOT_EQUAL_TO"))||
                (tokenList.get(tokenNumber).getTokenType().equals("LEFT_ROUND_BRACKET"))||
                (tokenList.get(tokenNumber).getTokenType().equals("FUNCTION_IDENTIFIER")))
        
        {
         if (expr() == true) {
             
             if(insideFunctionCall==true)
             {
                // System.out.println("checking 11"+arithmaticStack.toString());
             }
             
            return true;
        } else {
            return false;
        }
        }
        else
        {
            return false;
        }

    }// end of function

    public boolean arg_tail() // ,<arg_list>
    {
        if (tokenList.get(tokenNumber).getTokenType().equals("COMMA")) {
            tokenNumber++;
            if (arg_list() == true) {
                return true;
            } else {
                return false;
            }
        }
        // epsilon: ;,)

        else if ((tokenList.get(tokenNumber).getTokenType().equals("SEMICOLON"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("RIGHT_ROUND_BRACKET"))) {
            return true;
        } else {
            return false;
        }

    } // end of function.

    public boolean write_list() // <write_item><write_tail>
    {
        if ((tokenList.get(tokenNumber).getTokenType().equals("CHAR_LITERAL"))||
                (tokenList.get(tokenNumber).getTokenType().equals("VARIABLE_IDENTIFIER"))||
                (tokenList.get(tokenNumber).getTokenType().equals("INTEGER_LITERAL"))||
                (tokenList.get(tokenNumber).getTokenType().equals("BOOL_TRUE"))||
                (tokenList.get(tokenNumber).getTokenType().equals("BOOL_FALSE"))||
                (tokenList.get(tokenNumber).getTokenType().equals("SYMBOL_NOT_EQUAL_TO"))||
                (tokenList.get(tokenNumber).getTokenType().equals("LEFT_ROUND_BRACKET"))||
                (tokenList.get(tokenNumber).getTokenType().equals("STRING_LITERAL"))||
                (tokenList.get(tokenNumber).getTokenType().equals("FUNCTION_IDENTIFIER")))
            
        {
        
        if (write_item() == true) {
            if (write_tail() == true) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
        }
        else
        {
            return false;
        }
            
    } // end of function

    public boolean write_item() // doubt. pls check it. <<char>>
    {
        if (tokenList.get(tokenNumber).getTokenType().equals("CHAR_LITERAL")) {
            tokenNumber++;
            return true;
        }
        // else if // <<string>>

        else if (tokenList.get(tokenNumber).getTokenType()
                .equals("STRING_LITERAL")) {
            tokenNumber++;
            return true;
        }
        // <expr>

        else if ((tokenList.get(tokenNumber).getTokenType().equals("VARIABLE_IDENTIFIER"))||
                (tokenList.get(tokenNumber).getTokenType().equals("INTEGER_LITERAL"))||
                (tokenList.get(tokenNumber).getTokenType().equals("BOOL_TRUE"))||
                (tokenList.get(tokenNumber).getTokenType().equals("BOOL_FALSE"))||
                (tokenList.get(tokenNumber).getTokenType().equals("SYMBOL_NOT_EQUAL_TO"))||
                (tokenList.get(tokenNumber).getTokenType().equals("LEFT_ROUND_BRACKET"))||
                (tokenList.get(tokenNumber).getTokenType().equals("FUNCTION_IDENTIFIER")))
        
        {
         if (expr() == true) {
            return true;
        } else {
            return false;
        }
        }
        else 
        {
            return false;
        }

    } // end of function.

    public boolean write_tail() // ,<write_list>
    {
        if (tokenList.get(tokenNumber).getTokenType().equals("COMMA")) {
            tokenNumber++;
            if (write_list() == true) {
                return true;
            } else {
                return false;
            }
        }

        // epsilon: )

        else if (tokenList.get(tokenNumber).getTokenType()
                .equals("RIGHT_ROUND_BRACKET")) {
            return true;
        } else {
            return false;
        }

    } // end of function.

    public boolean expr() // <b_expr><expr_1>
    {
        if ((tokenList.get(tokenNumber).getTokenType().equals("VARIABLE_IDENTIFIER"))||
                (tokenList.get(tokenNumber).getTokenType().equals("INTEGER_LITERAL"))||
                (tokenList.get(tokenNumber).getTokenType().equals("BOOL_TRUE"))||
                (tokenList.get(tokenNumber).getTokenType().equals("BOOL_FALSE"))||
                (tokenList.get(tokenNumber).getTokenType().equals("SYMBOL_NOT_EQUAL_TO"))||
                (tokenList.get(tokenNumber).getTokenType().equals("LEFT_ROUND_BRACKET"))||
                (tokenList.get(tokenNumber).getTokenType().equals("FUNCTION_IDENTIFIER")))
        {
        
        if (b_expr() == true) {
            if (expr_1() == true) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
        }
        else
        {
            return false;
        }
    } // end of function.

    public boolean expr_1() // |<b_expr><expr_1>
    {
        if (tokenList.get(tokenNumber).getTokenType().equals("PIPE_SYMBOL")) {
            tokenNumber++;
            if (b_expr() == true) {
                if (expr_1() == true) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        // &<b_expr><expr_1>

        else if (tokenList.get(tokenNumber).getTokenType().equals("AND_SYMBOL")) {
            tokenNumber++;
            if (b_expr() == true) {
                if (expr_1() == true) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        // epsilon move ), ], comma, ;

        else if ((tokenList.get(tokenNumber).getTokenType()
                .equals("RIGHT_ROUND_BRACKET"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("RIGHT_SQUARE_BRACKET"))
                || (tokenList.get(tokenNumber).getTokenType().equals("COMMA"))
                || (tokenList.get(tokenNumber).getTokenType().equals("SEMICOLON"))) {
            return true;
        } else {
            return false;
        }

    } // / end of function.

    public boolean b_expr() { // <n_expr><b_expr_1>
        
        if ((tokenList.get(tokenNumber).getTokenType().equals("VARIABLE_IDENTIFIER"))||
                (tokenList.get(tokenNumber).getTokenType().equals("INTEGER_LITERAL"))||
                (tokenList.get(tokenNumber).getTokenType().equals("BOOL_TRUE"))||
                (tokenList.get(tokenNumber).getTokenType().equals("BOOL_FALSE"))||
                (tokenList.get(tokenNumber).getTokenType().equals("SYMBOL_NOT_EQUAL_TO"))||
                (tokenList.get(tokenNumber).getTokenType().equals("LEFT_ROUND_BRACKET"))||
                (tokenList.get(tokenNumber).getTokenType().equals("FUNCTION_IDENTIFIER")))
        {
        
        
        if (n_expr() == true) {
            if (b_expr_1() == true) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
        }
        else
        {
            return false;
        }
    } // end of function.

    public boolean b_expr_1() // < <n_expr>
    {
        if (tokenList.get(tokenNumber).getTokenType()
                .equals("LEFT_ANGULAR_BRACKET")) {
            tokenNumber++;
            if (n_expr() == true) {
                return true;
            } else {
                return false;
            }
        }

        // > <n_expr>

        else if (tokenList.get(tokenNumber).getTokenType()
                .equals("RIGHT_ANGULAR_BRACKET")) {
            tokenNumber++;
            if (n_expr() == true) {
                return true;
            } else {
                return false;
            }
        }
        // <= <n_expr>

        else if (tokenList.get(tokenNumber).getTokenType()
                .equals("SYMBOL_LESS_THAN_EQUAL_TO")) {
            tokenNumber++;
            if (n_expr() == true) {
                return true;
            } else {
                return false;
            }
        }

        // >= <n_expr>

        else if (tokenList.get(tokenNumber).getTokenType()
                .equals("SYMBOL_MORE_THAN_EQUAL_TO")) {
            tokenNumber++;
            if (n_expr() == true) {
                return true;
            } else {
                return false;
            }
        }

        // = <n_expr>

        else if (tokenList.get(tokenNumber).getTokenType()
                .equals("SYMBOL_EQUAL_TO")) {
            tokenNumber++;
            if (n_expr() == true) {
                return true;
            } else {
                return false;
            }
        }

        // != <n_expr>

        else if (tokenList.get(tokenNumber).getTokenType()
                .equals("SYMBOL_NOT_EQUAL_TO")) {
            tokenNumber++;
            if (n_expr() == true) {
                return true;
            } else {
                return false;
            }
        }

        // epsilon move |, &, ), ], comma, ;

        else if ((tokenList.get(tokenNumber).getTokenType().equals("PIPE_SYMBOL"))
                || (tokenList.get(tokenNumber).getTokenType().equals("AND_SYMBOL"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("RIGHT_ROUND_BRACKET"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("RIGHT_SQUARE_BRACKET"))
                || (tokenList.get(tokenNumber).getTokenType().equals("COMMA"))
                || (tokenList.get(tokenNumber).getTokenType().equals("SEMICOLON"))) {
            return true;
        } else {
            return false;
        }

    }// end of function.

    public boolean n_expr() // <term><n_expr_1>
    {
        if ((tokenList.get(tokenNumber).getTokenType().equals("VARIABLE_IDENTIFIER"))||
                (tokenList.get(tokenNumber).getTokenType().equals("INTEGER_LITERAL"))||
                (tokenList.get(tokenNumber).getTokenType().equals("BOOL_TRUE"))||
                (tokenList.get(tokenNumber).getTokenType().equals("BOOL_FALSE"))||
                (tokenList.get(tokenNumber).getTokenType().equals("SYMBOL_NOT_EQUAL_TO"))||
                (tokenList.get(tokenNumber).getTokenType().equals("LEFT_ROUND_BRACKET"))||
                (tokenList.get(tokenNumber).getTokenType().equals("FUNCTION_IDENTIFIER")))
        {
        String arithmaticTerm=tokenList.get(tokenNumber).getToken();
       
       
       
        if (term() == true) {
        String symbolTableIndexOfTerm=getIndexForSymbolFromSymbolTable(arithmaticTerm);
        //stack manipulation starts
        if(assignmentStatementStarts==true  ||  insideFunctionCall==true)
        {
        //System.out.println("check 3 - pushin "+arithmaticTerm+symbolTableIndexOfTerm+assignmentStatementStarts+insideFunctionCall);
            arithmaticStack.push(symbolTableIndexOfTerm);
        }
            //stack manipulation ends
       
       
            if (n_expr_1() == true) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
        }
        else
        {
            return false;
        }
    } // end of function.

    public boolean n_expr_1() // +<term><n_expr_1>
    {
        if (tokenList.get(tokenNumber).getTokenType().equals("SYMBOL_PLUS")) {
            tokenNumber++;
            String arithmaticTerm=tokenList.get(tokenNumber).getToken();
            if (term() == true) {
           
            if(assignmentStatementStarts==true  || insideFunctionCall==true)
            {
            //stack manipulation and quad code starts
            arithmaticStack.push(getIndexForSymbolFromSymbolTable(arithmaticTerm));
            //quad code
            String temporaryArithmaticVariable="#arithTempVariable"+arithmaticTemporaryNumber;
            arithmaticTemporaryNumber++;
            String operand1=(String) arithmaticStack.pop();
            String operand2=(String) arithmaticStack.pop();
            QuadEntry quadEntry=new QuadEntry(quadCounter, "add", operand1, operand2, temporaryArithmaticVariable);
            quadTable.add(quadEntry);
            quadCounter++;
            arithmaticStack.push(temporaryArithmaticVariable);
           
            //stack manipulation and quad code ends
            }
                if (n_expr_1() == true) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        // -<term><n_expr_1>

        else if (tokenList.get(tokenNumber).getTokenType().equals("SYMBOL_MINUS")) {
            tokenNumber++;
            String arithmaticTerm=tokenList.get(tokenNumber).getToken();
            if (term() == true) {
           
            if(assignmentStatementStarts==true  || insideFunctionCall==true)
            {
            //stack manipulation and quad code starts
            arithmaticStack.push(getIndexForSymbolFromSymbolTable(arithmaticTerm));
            //quad code
            String temporaryArithmaticVariable="#arithTempVariable"+arithmaticTemporaryNumber;
            arithmaticTemporaryNumber++;
            String operand2=(String) arithmaticStack.pop();
            String operand1=(String) arithmaticStack.pop();
            QuadEntry quadEntry=new QuadEntry(quadCounter, "sub", operand1, operand2, temporaryArithmaticVariable);
            quadTable.add(quadEntry);
            quadCounter++;
            arithmaticStack.push(temporaryArithmaticVariable);
           
            //stack manipulation and quad code ends
            }
           
                if (n_expr_1() == true) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        // epsilon move <, >, <=, >=, =, !=, |, &, ), ], comma, ;

        else if ((tokenList.get(tokenNumber).getTokenType()
                .equals("SYMBOL_LESS_THAN"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_GREATER_THAN"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_LESS_THAN_EQUAL_TO"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_MORE_THAN_EQUAL_TO"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_EQUAL_TO"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_NOT_EQUAL_TO"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("PIPE_SYMBOL"))
                || (tokenList.get(tokenNumber).getTokenType().equals("AND_SYMBOL"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("RIGHT_ROUND_BRACKET"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("RIGHT_SQUARE_BRACKET"))
                || (tokenList.get(tokenNumber).getTokenType().equals("COMMA"))
                || (tokenList.get(tokenNumber).getTokenType().equals("SEMICOLON"))) {
            return true;
        } else {
            return false;
        }

    }// end of function.

    // <factor><term__1>

    public boolean term() {
        if ((tokenList.get(tokenNumber).getTokenType().equals("VARIABLE_IDENTIFIER"))||
                (tokenList.get(tokenNumber).getTokenType().equals("INTEGER_LITERAL"))||
                (tokenList.get(tokenNumber).getTokenType().equals("BOOL_TRUE"))||
                (tokenList.get(tokenNumber).getTokenType().equals("BOOL_FALSE"))||
                (tokenList.get(tokenNumber).getTokenType().equals("SYMBOL_NOT_EQUAL_TO"))||
                (tokenList.get(tokenNumber).getTokenType().equals("LEFT_ROUND_BRACKET"))||
                (tokenList.get(tokenNumber).getTokenType().equals("FUNCTION_IDENTIFIER")))
        {
        
        
        if (factor() == true) {
            if (term_1() == true) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
        }
        else
        {
            return false;
        }
    }// end of function.

    // *<factor><term__1>

    public boolean term_1() {
        if (tokenList.get(tokenNumber).getTokenType().equals("SYMBOL_MULTIPLY")) {
            tokenNumber++;
            if (factor() == true) {
                if (term_1() == true) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        // /<factor><term__1>

        else if (tokenList.get(tokenNumber).getTokenType()
                .equals("SYMBOL_DIVISION")) {
            tokenNumber++;
            if (factor() == true) {
                if (term_1() == true) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        // epsilon move +, -, <, >, <=, >=, =, !=, |, &, ), ], comma, ;

        else if ((tokenList.get(tokenNumber).getTokenType().equals("SYMBOL_PLUS"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_MINUS"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_LESS_THAN"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_GREATER_THAN"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_LESS_THAN_EQUAL_TO"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_MORE_THAN_EQUAL_TO"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_EQUAL_TO"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_NOT_EQUAL_TO"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("PIPE_SYMBOL"))
                || (tokenList.get(tokenNumber).getTokenType().equals("AND_SYMBOL"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("RIGHT_ROUND_BRACKET"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("RIGHT_SQUARE_BRACKET"))
                || (tokenList.get(tokenNumber).getTokenType().equals("COMMA"))
                || (tokenList.get(tokenNumber).getTokenType().equals("SEMICOLON"))) {
            return true;
        } else {
            return false;
        }

    } // end of function.

    public boolean factor() // <sub_factor><factor_1>
    {
        if ((tokenList.get(tokenNumber).getTokenType().equals("VARIABLE_IDENTIFIER"))||
                (tokenList.get(tokenNumber).getTokenType().equals("INTEGER_LITERAL"))||
                (tokenList.get(tokenNumber).getTokenType().equals("BOOL_TRUE"))||
                (tokenList.get(tokenNumber).getTokenType().equals("BOOL_FALSE"))||
                (tokenList.get(tokenNumber).getTokenType().equals("SYMBOL_NOT_EQUAL_TO"))||
                (tokenList.get(tokenNumber).getTokenType().equals("LEFT_ROUND_BRACKET"))||
                (tokenList.get(tokenNumber).getTokenType().equals("FUNCTION_IDENTIFIER")))
        {
        
        
        if (sub_factor() == true) {
            if (factor_1() == true) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
        }
        else
        {
            return false;
        }
    } // end of function.

    // %<sub_factor><factor_1>
    public boolean factor_1() {
        if (tokenList.get(tokenNumber).getTokenType().equals("SYMBOL_PERCENTAGE")) {
            tokenNumber++;
            if (sub_factor() == true) {
                if (factor_1() == true) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        // epsilon move *, /, +, -, <, >, <=, >=, =, !=, |, &, ), ], comma, ;

        else if ((tokenList.get(tokenNumber).getTokenType()
                .equals("SYMBOL_MULTIPLY"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_DIVISION"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_PLUS"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_MINUS"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_LESS_THAN"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_GREATER_THAN"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_LESS_THAN_EQUAL_TO"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_MORE_THAN_EQUAL_TO"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_EQUAL_TO"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_NOT_EQUAL_TO"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("PIPE_SYMBOL"))
                || (tokenList.get(tokenNumber).getTokenType().equals("AND_SYMBOL"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("RIGHT_ROUND_BRACKET"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("RIGHT_SQUARE_BRACKET"))
                || (tokenList.get(tokenNumber).getTokenType().equals("COMMA"))
                || (tokenList.get(tokenNumber).getTokenType().equals("SEMICOLON"))) {
            return true;
        } else {
            return false;
        }

    } // end of function.

    // <base><sub_factor_1>

    public boolean sub_factor() {
        if ((tokenList.get(tokenNumber).getTokenType().equals("VARIABLE_IDENTIFIER"))||
                (tokenList.get(tokenNumber).getTokenType().equals("INTEGER_LITERAL"))||
                (tokenList.get(tokenNumber).getTokenType().equals("BOOL_TRUE"))||
                (tokenList.get(tokenNumber).getTokenType().equals("BOOL_FALSE"))||
                (tokenList.get(tokenNumber).getTokenType().equals("SYMBOL_NOT_EQUAL_TO"))||
                (tokenList.get(tokenNumber).getTokenType().equals("LEFT_ROUND_BRACKET"))||
                (tokenList.get(tokenNumber).getTokenType().equals("FUNCTION_IDENTIFIER")))
        {
            
        
        
        
        if (base() == true) {
            if (sub_factor_1() == true) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
        }
        else
        {
            return false;
        }
    } // end of function.

    // ^<sub_factor>
    public boolean sub_factor_1() {
        if (tokenList.get(tokenNumber).getTokenType().equals("SYMBOL_POWER")) {
            tokenNumber++;
            if (sub_factor() == true) {
                return true;
            } else {
                return false;
            }
        }

        // epsilon move %, *, /, +, -, <, >, <=, >=, =, !=, |, &, ), ], comma, ;

        else if ((tokenList.get(tokenNumber).getTokenType()
                .equals("SYMBOL_PERCENTAGE"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_MULTIPLY"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_DIVISION"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_PLUS"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_MINUS"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_LESS_THAN"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_GREATER_THAN"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_LESS_THAN_EQUAL_TO"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_MORE_THAN_EQUAL_TO"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_EQUAL_TO"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("SYMBOL_NOT_EQUAL_TO"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("PIPE_SYMBOL"))
                || (tokenList.get(tokenNumber).getTokenType().equals("AND_SYMBOL"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("RIGHT_ROUND_BRACKET"))
                || (tokenList.get(tokenNumber).getTokenType()
                        .equals("RIGHT_SQUARE_BRACKET"))
                || (tokenList.get(tokenNumber).getTokenType().equals("COMMA"))
                || (tokenList.get(tokenNumber).getTokenType().equals("SEMICOLON"))) {
            return true;
        } else {
            return false;
        }

    } // end of function.

    public boolean base() // <var_ref>
    {
        if (tokenList.get(tokenNumber).getTokenType()
                .equals("VARIABLE_IDENTIFIER")) {
            //tokenNumber++;
            
            if (var_ref() == true) {
                return true;
            } else {
                return false;
            }
        }
        // <<int>>

        else if (tokenList.get(tokenNumber).getTokenType()
                .equals("INTEGER_LITERAL")) {
        //code for putting entry in the symbol table for integer constant starts
         
             String constant_name=tokenList.get(tokenNumber).getToken();
    boolean isConstantAlreadyPresent=checkIfConstantAlreadyPresent(constant_name);
             SymbolTableEntry symbolTableEntry=new SymbolTableEntry
    (symbolTableCounter, constant_name, "const", "int", blockNumber, null, null);
    if(isConstantAlreadyPresent==false)
    {
             symbolTable.add(symbolTableEntry);
    symbolTableCounter++;
    }
        //code for putting entry in the symbol table for integer constant ends
            
    tokenNumber++;
            return true;
        }

        // <<bool>>

        else if ((tokenList.get(tokenNumber).getTokenType()
                .equals("BOOL_TRUE"))||(tokenList.get(tokenNumber).getTokenType()
                        .equals("BOOL_FALSE"))) {
        //code for putting entry in the symbol table for boolean constant starts
         
            String constant_name=tokenList.get(tokenNumber).getToken();
    SymbolTableEntry symbolTableEntry=new SymbolTableEntry
    (symbolTableCounter, constant_name, "const", "boolean", blockNumber, null, null);
    symbolTable.add(symbolTableEntry);
    symbolTableCounter++;
        //code for putting entry in the symbol table for boolean constant ends
       
            tokenNumber++;
            return true;
        }

        // !<base>

        else if (tokenList.get(tokenNumber).getTokenType()
                .equals("SYMBOL_NOT_EQUAL_TO")) {
            tokenNumber++;
            if (base() == true) {
                return true;
            } else {
                return false;
            }
        }

        // (<expr>)

        else if (tokenList.get(tokenNumber).getTokenType()
                .equals("LEFT_ROUND_BRACKET")) {
            tokenNumber++;
            if (expr() == true) {
                if (tokenList.get(tokenNumber).getTokenType()
                        .equals("RIGHT_ROUND_BRACKET")) {
                    tokenNumber++;
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        // <function_call>
        
        else if(tokenList.get(tokenNumber).getTokenType()
                .equals("FUNCTION_IDENTIFIER"))
        {
        
        if (function_call() == true) {
            return true;
        } else {
            return false;
        }
        }
        else
        {
            return false;
        }

    } // end of function.

    public boolean function_call() // <<id>>(<call_list>)
    {
        if (tokenList.get(tokenNumber).getTokenType()
                .equals("FUNCTION_IDENTIFIER")) {
            insideFunctionCall=true;
            String nameOfFunction=tokenList.get(tokenNumber).getToken();
            String nameOfFunction_indexInSymbolTable=getIndexForSymbolFromSymbolTable(nameOfFunction);
            //finding the index of input parameters for this function starts
           Stack functionCallInpurParametersStack=new Stack();
            for (int i=0;i<symbolTable.size();i++)
            {
                SymbolTableEntry temp=symbolTable.get(i);
                if(nameOfFunction_indexInSymbolTable!=null &&temp.getBlock()!=null &&  temp.getBlock()==Integer.parseInt(nameOfFunction_indexInSymbolTable) && temp.getId().equals("IP"))
                {
                   functionCallInpurParametersStack.push((String)(""+temp.getIndex()));
                     
                }
            }
            
             //finding the index of input parameters for this function ends
            //System.out.println("Name of function is  is "+nameOfFunction);
            String temporaryArithmaticVariable="#arithTempVariable"+arithmaticTemporaryNumber;
            arithmaticTemporaryNumber++;
                //genarating quads for inside call starts
                QuadEntry quadEntryInitial=new QuadEntry(quadCounter,"startcall", null, null,temporaryArithmaticVariable );
quadTable.add(quadEntryInitial);
quadCounter++;
                
                //genarating quads for inside call ends
                
            tokenNumber++;
            if (tokenList.get(tokenNumber).getTokenType()
                    .equals("LEFT_ROUND_BRACKET")) {
                tokenNumber++;
                if (call_list() == true) {
                    
                    //System.out.println("checkkkk 22222 "+arithmaticStack.toString());
                    //by this time we have all the input parameters of fucntion call in the stack
                    while(!functionCallInpurParametersStack.isEmpty()  &&  !arithmaticStack.isEmpty())
                    {
                        String value=(String)arithmaticStack.pop();
                        String variable=(String)functionCallInpurParametersStack.pop();
                        QuadEntry quadEntry=new QuadEntry(quadCounter, "copyin", value, variable, temporaryArithmaticVariable);
                        quadTable.add(quadEntry);
                        quadCounter++;
                    }
                    
                    //genarating quads for copy out starts
                     Stack functionCallOutputParametersStack=new Stack();
            for (int i=0;i<symbolTable.size();i++)
            {
                SymbolTableEntry temp=symbolTable.get(i);
                if(nameOfFunction_indexInSymbolTable!=null &&temp.getBlock()!=null &&  temp.getBlock()==Integer.parseInt(nameOfFunction_indexInSymbolTable) && temp.getId().equals("OP"))
                {
                   functionCallOutputParametersStack.push((String)(""+temp.getIndex()));
                     
                }
            }
            while(!functionCallOutputParametersStack.isEmpty())
            {
             String temporaryArithmaticVariable2="#arithTempVariable"+arithmaticTemporaryNumber;
            arithmaticTemporaryNumber++;
                 QuadEntry quadEntry=new QuadEntry(quadCounter, "copyout",(String) functionCallOutputParametersStack.pop(), temporaryArithmaticVariable2, temporaryArithmaticVariable);
                        quadTable.add(quadEntry);
                        quadCounter++;
            }
                    //genarating quads for copy out ends
                    if (tokenList.get(tokenNumber).getTokenType()
                            .equals("RIGHT_ROUND_BRACKET")) {
                        //genarating quad for procjump starts
                       // nameOfFunction_indexInSymbolTable
                          for(int i=0;i<quadTable.size();i++)
                          {
                          QuadEntry temp=quadTable.get(i);
                          if(temp.getQuad1().equals("procentry") && temp.getQuad2().equals(nameOfFunction_indexInSymbolTable))
                          {
                        	  QuadEntry quadEntry=new QuadEntry(quadCounter, "procjump",""+temp.getIndex(), null, temporaryArithmaticVariable);
                              quadTable.add(quadEntry);
                              quadCounter++;  
                          }
                          }
                        //genarating quad for procjump ends
                                
                        insideFunctionCall=false;
                        tokenNumber++;
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    } // end of function.

    public boolean call_list() // <arg_list>
    {
        if ((tokenList.get(tokenNumber).getTokenType().equals("CHAR_LITERAL"))||
                (tokenList.get(tokenNumber).getTokenType().equals("VARIABLE_IDENTIFIER"))||
                (tokenList.get(tokenNumber).getTokenType().equals("INTEGER_LITERAL"))||
                (tokenList.get(tokenNumber).getTokenType().equals("BOOL_TRUE"))||
                (tokenList.get(tokenNumber).getTokenType().equals("BOOL_FALSE"))||
                (tokenList.get(tokenNumber).getTokenType().equals("SYMBOL_NOT_EQUAL_TO"))||
                (tokenList.get(tokenNumber).getTokenType().equals("LEFT_ROUND_BRACKET"))||
                (tokenList.get(tokenNumber).getTokenType().equals("FUNCTION_IDENTIFIER")))
        
        {
        
        if (arg_list() == true) {
            return true;
        }
        else
        {
            return false;
        }
        }
    
        // epsilon move : )

        else if (tokenList.get(tokenNumber).getTokenType()
                .equals("RIGHT_ROUND_BRACKET")) {
            return true;
        } else {
            return false;
        }

    } // end of function.
    
    
    public String getIndexForSymbolFromSymbolTable(String symbol)
    {
   
    for(int i=0;i<symbolTable.size();i++)
    {
    if(symbolTable.get(i).getNameOfSymbol().equals(symbol))
    {
    return (""+symbolTable.get(i).getIndex());
    }
   
    }
    return null;
    }
    
    public boolean checkIfConstantAlreadyPresent(String constant)
    {
    boolean flag=false;
    for(int i=0;i<symbolTable.size();i++)
    {
    if(symbolTable.get(i).getNameOfSymbol().equals(constant))
    {
    flag=true;
    }
    }
    return flag;
    }
    

}