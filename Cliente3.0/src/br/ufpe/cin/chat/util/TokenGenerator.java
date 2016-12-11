package br.ufpe.cin.chat.util;

public class TokenGenerator {
	
	private static int token = 0;
	
	public static int generateToken(){
		token++;
		return token;
	}

}
