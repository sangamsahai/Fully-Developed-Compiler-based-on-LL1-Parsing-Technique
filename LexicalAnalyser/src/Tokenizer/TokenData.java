package Tokenizer;
import java.util.regex.*;

public class TokenData {

	
	/*Pattern class is a Java class which is a compiled representation of a regular expression*/
	private Pattern pattern;
	private TokenType type;//This is the token type
	
	//constructor
	public TokenData(Pattern pattern,TokenType type)
	{
		this.pattern=pattern;
		this.type=type;
	}
	
	//getters
	public Pattern getPattern()
	{
		return pattern;
	}
	
	public TokenType getType()
	{
		return type;
	}
	
}
