package br.ufpe.cin.chat.dados;

import java.io.Serializable;

public class Usuario implements Serializable{

	private static final long serialVersionUID = -3086484111919071245L;
	
	private String login;
	private String senha;
	private boolean logado;
	private String IP;
	
	public Usuario(String login, String senha, String IP){
		this.login = login;
		this.senha = senha;
		this.IP = IP;
	}
	
	public String getLogin() {
		return login;
	}
	public String getSenha() {
		return senha;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isLogado() {
		return logado;
	}

	public void setLogado(boolean logado) {
		this.logado = logado;
	}

	public String getIP() {
		return IP;
	}
	
}