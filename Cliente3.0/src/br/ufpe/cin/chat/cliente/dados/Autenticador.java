package br.ufpe.cin.chat.cliente.dados;

import java.io.Serializable;

public class Autenticador implements Serializable{

	private static final long serialVersionUID = -7874913571675764892L;

	private String login;
	private String senha;

	public Autenticador(String login, String senha) {
		this.setLogin(login);
		this.setSenha(senha);
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

}
