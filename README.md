# Fully-Developed-Compiler-based-on-LL1-Parsing-Technique
This is a fully developed Compiler which does Lexical Analysis , LL1 Parsing , Symbol Table Generation and Quads Generation.


This is a fully developed Compiler.

The context free language and the syntax is defined in the file Language_Syntax_LL1.pdf .
The Selection set is given in file Selection_Sets.pdf.
The Quads format is given in the file Quads.pdf .


This project uses LL1 technique to parse the code.
This parses uses 1 token of lookahead when parsing .
Based on the next token ,it predicts the production which will be used.

Hence it predicts and parses without backtracking.
The context free grammar given is LL1 grammar and has the following - 

1) Ambiguity has been eliminated
2) Left Recursion has been eliminated
3) Left Factorization has been performed

This project does the following things - 

1) Lexical Analysis
2) Syntax Analysis
3) Symbol Table Entry Generation
4) Quads Generation


The class which performs the Lexical Analysis is TokenGenarator.java
The class which performs LL1 Parsing , Symbol Table Generation and Quads Generation is LL1Parser.java.

For Lexical Analysis , the Java Libraries 'java.util.regex.Matcher' and 'util.regex.Pattern'
have been used.
In extracting the tokens  , the principle of longest match has been used.
For Arithmetic Quads generation, this code uses Semantic Action Stack.


Sample Input and Output is as follows - 

Input - 

recur(int X) -> <int>
{  int Y,int Z;
   if (X = 0) { return X; }
      else { write(X);
	         Y,Z := Z+1,X + 1 - 3;
	         return recur(Y); }
}			 

main( ) -> < >
{  int X;
   X := 1;
   write(X);
}




Output - 

 Following is the symbol table-
SymbolTableEntry [index=0, nameOfSymbol=recur, id=proc, type=null, block=null, offset=null, quadNumber=1]
SymbolTableEntry [index=1, nameOfSymbol=X, id=IP, type=int, block=0, offset=17, quadNumber=null]
SymbolTableEntry [index=2, nameOfSymbol=#returnVariable1, id=OP, type=>, block=0, offset=18, quadNumber=null]
SymbolTableEntry [index=3, nameOfSymbol=Y, id=var, type=int, block=0, offset=19, quadNumber=null]
SymbolTableEntry [index=4, nameOfSymbol=Z, id=var, type=int, block=0, offset=20, quadNumber=null]
SymbolTableEntry [index=5, nameOfSymbol=0, id=const, type=int, block=0, offset=null, quadNumber=null]
SymbolTableEntry [index=6, nameOfSymbol=1, id=const, type=int, block=0, offset=null, quadNumber=null]
SymbolTableEntry [index=7, nameOfSymbol=3, id=const, type=int, block=0, offset=null, quadNumber=null]
SymbolTableEntry [index=8, nameOfSymbol=main, id=proc, type=null, block=null, offset=null, quadNumber=12]
SymbolTableEntry [index=9, nameOfSymbol=X, id=var, type=int, block=8, offset=17, quadNumber=null]


 Following is the quad table-
QuadEntry [index=0, quad1=jump, quad2=null, quad3=null, quad4=null]
QuadEntry [index=1, quad1=procentry, quad2=0, quad3=null, quad4=null]
QuadEntry [index=2, quad1=add, quad2=6, quad3=4, quad4=#arithTempVariable1]
QuadEntry [index=3, quad1=add, quad2=6, quad3=1, quad4=#arithTempVariable2]
QuadEntry [index=4, quad1=sub, quad2=#arithTempVariable2, quad3=7, quad4=#arithTempVariable3]
QuadEntry [index=5, quad1=assign, quad2=#arithTempVariable3, quad3=null, quad4=4]
QuadEntry [index=6, quad1=assign, quad2=#arithTempVariable1, quad3=null, quad4=3]
QuadEntry [index=7, quad1=startcall, quad2=null, quad3=null, quad4=#arithTempVariable4]
QuadEntry [index=8, quad1=copyin, quad2=3, quad3=1, quad4=#arithTempVariable4]
QuadEntry [index=9, quad1=copyout, quad2=2, quad3=#arithTempVariable5, quad4=#arithTempVariable4]
QuadEntry [index=10, quad1=procjump, quad2=1, quad3=null, quad4=#arithTempVariable4]
QuadEntry [index=11, quad1=procexit, quad2=0, quad3=null, quad4=null]
QuadEntry [index=12, quad1=procentry, quad2=8, quad3=null, quad4=null]
QuadEntry [index=13, quad1=assign, quad2=6, quad3=null, quad4=1]
QuadEntry [index=14, quad1=procexit, quad2=8, quad3=null, quad4=null].........


 Result of LL1 Parsing is -  true


How to use this project ?

Run the file 'TokenGenarator.java' as a Java Application and give the path
of the txt file which contains the code.
After the code runs, you will get a similar output as shown above.


