package br.ufpe.cin.chat.dados;

import java.io.Serializable;

public class Autenticador implements Serializable{

	private static final long serialVersionUID = -7874913571675764892L;

	private String login;
	private String senha;
	private String ip;

	public Autenticador(String login, String senha, String ip) {
		this.setLogin(login);
		this.setSenha(senha);
		this.setIp(ip);
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
