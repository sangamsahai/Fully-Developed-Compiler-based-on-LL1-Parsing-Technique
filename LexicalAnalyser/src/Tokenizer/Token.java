package Tokenizer;

public class Token {
	
	private String tokenType;
	private  String token;
	
	public Token(String tokenType,String token)
	{
		this.tokenType=tokenType;
		this.token=token;
	}
	
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	//getters and setters
	
}
