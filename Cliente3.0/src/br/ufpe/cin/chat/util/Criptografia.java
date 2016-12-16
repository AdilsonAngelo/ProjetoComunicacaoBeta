package br.ufpe.cin.chat.util;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Criptografia {
	
	static String IV ="ABC45FG8I1KLMZOV";
	static String chaveArquivo = "0123456789abcdef";
	static Cipher encript, decript;
	
	public Criptografia() throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, UnsupportedEncodingException{
		encript = Cipher.getInstance("AES/CBC/NoPadding","SunJCE");
		SecretKeySpec key = new SecretKeySpec(chaveArquivo.getBytes("UTF-8"),"AES");
		encript.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
		decript = Cipher.getInstance("AES/CBC/NoPadding","SunJCE");
		decript.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
		
	}
	
	private static int charToInt(char c){
		return (int) c;
	}
	
	private static char intToChar(int i){
		return (char) i;
	}
	
	private static int[] charArrayToInt(char[] cc){
		int[] j = new int[cc.length];
		for(int i = 0; i<cc.length; i++){
			j[i]= charToInt(cc[i]);
		}
		return j;
	}
	
	private static char[] intArrayToChar(int[] ii){
		char[] c = new char[ii.length];
		for(int i = 0; i<ii.length; i++){
			c[i]= intToChar(ii[i]);
		}
		return c;
	}
	// a chave de encriptação é o login do usuario que envia a mensagem
	public static String encripta(String mensagem,String chave){
		int[] message = charArrayToInt(mensagem.toCharArray());
		int[] key = charArrayToInt(chave.toCharArray());
		int[] encripted = new int[mensagem.length()];
		
		for(int i = 0;i<mensagem.length();i++){
			encripted[i] = message[i]+ key[i%key.length];
		}
		
		return new String(intArrayToChar(encripted));
	}
	// a chave de desencriptação é o login do usuario que enviou a mensagem;
	public static String decripta(String mensagem, String chave){
		int[] message = charArrayToInt(mensagem.toCharArray());
		int[] key = charArrayToInt(chave.toCharArray());
		int[] encripted = new int[mensagem.length()];
		
		for(int i = 0;i<mensagem.length();i++){
			encripted[i] = message[i] - key[i%key.length];
		}
		
		return new String(intArrayToChar(encripted));
		
	}
	
	public  Cipher getEncripta() {
		return encript;
	}
	
	public  Cipher getDecripta(){ 
		return decript;
	}
	

}