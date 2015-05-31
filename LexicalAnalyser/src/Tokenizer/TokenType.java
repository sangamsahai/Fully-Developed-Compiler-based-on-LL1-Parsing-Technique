package Tokenizer;

public enum TokenType {

	
	/*This class holds the definition of different types of Tokens */
	
	
	//starts with any small letter and then any alphabet/number
	FUNCTION_IDENTIFIER,
	
	//starts with any capital letter and then any alphabet/number
	VARIABLE_IDENTIFIER,
	
	KEYWORD_IF,
	
	KEYWORD_FORWARD,
	
	KEYWORD_THEN,
	
	KEYWORD_WHILE,
	
	KEYWORD_WRITE,
	
	KEYWORD_READ,
	
	KEYWORD_RETURN,
	
	KEYWORD_ELSE,
	
	SEMICOLON,
	
	COMMA,
	
	//any number
	INTEGER_LITERAL,
	
	// anything string within  double quotes
	STRING_LITERAL,
	
	//any single character within single quotes
	CHAR_LITERAL,
	
	//'true' or 'false'
	BOOLEAN_LITERAL,
	
	ARROW_SYMBOL,
	
	LEFT_ANGULAR_BRACKET,
	
	RIGHT_ANGULAR_BRACKET,
	
	LEFT_CURLY_BRACKET,
	
	RIGHT_CURLY_BRACKET,
	
	//int
	INT_DATATYPE,
	
	//char
	CHAR_DATATYPE,
	
	//string
	STRING_DATATYPE,
	
	//bool
	BOOL_DATATYPE,
	
	LEFT_SQUARE_BRACKET,
	
	RIGHT_SQUARE_BRACKET,
	
	ASSIGNMENT_OPERATOR,
	
	PIPE_SYMBOL,
	
	AND_SYMBOL,
	
	//SYMBOL_LESS_THAN,
	
	//SYMBOL_MORE_THAN,
	
	
	SYMBOL_LESS_THAN_EQUAL_TO,
	
	SYMBOL_MORE_THAN_EQUAL_TO,
	
	SYMBOL_EQUAL_TO,
	
	SYMBOL_NOT_EQUAL_TO,
	
	SYMBOL_PLUS,
	
	SYMBOL_MINUS,
	
	SYMBOL_MULTIPLY,
	
	SYMBOL_DIVISION,
	
	SYMBOL_PERCENTAGE,
	
	SYMBOL_POWER,
	
	LEFT_ROUND_BRACKET,
	
	RIGHT_ROUND_BRACKET
	
	
	
	
	
}
