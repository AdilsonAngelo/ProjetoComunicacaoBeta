package br.ufpe.cin.chat.servidor.dados;

import java.io.Serializable;

public class Mensagem implements Serializable{
	
	private static final long serialVersionUID = -2577728958530566464L;
	private int token;
	private String content;
	
	private String remetente;
	private String destinatario;
	
	private boolean sent;
	private boolean received;
	private boolean read;
	
	
	public Mensagem(int token, String content){
		this.token = token;
		this.content = content;
		this.sent = false;
		this.received = false;
		this.read = false;
	}

	public int getToken() {
		return token;
	}

	public void setToken(int token) {
		this.token = token;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setRemetente(String remetente) {
		this.remetente = remetente;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public boolean isSent() {
		return sent;
	}

	public void setSent(boolean sent) {
		this.sent = sent;
	}
	

	public boolean isReceived() {
		return received;
	}

	public void setReceived(boolean received) {
		this.received = received;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public String getRemetente() {
		// TODO Auto-generated method stub
		return this.remetente;
	}
}