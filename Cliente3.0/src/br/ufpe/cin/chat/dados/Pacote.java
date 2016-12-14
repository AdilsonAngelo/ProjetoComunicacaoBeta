package br.ufpe.cin.chat.dados;

import java.io.Serializable;

public class Pacote implements Serializable {

	private static final long serialVersionUID = -6772119103344815113L;
	
	private int token;
	private String remetente;
	private String destinatario;
	private byte[] conteudo;
	private long offset;
	private boolean last;
	private int tamanho;
	private String fileName;
	
	public Pacote(int token, byte[] janela, long offset) {
		this.token = token;
		this.conteudo = janela;
		this.offset = offset;
	}
	
	public int getToken() {
		return token;
	}
	
	public void setToken(int token) {
		this.token = token;
	}
	
	public String getRemetente() {
		return remetente;
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
	
	public byte[] getConteudo() {
		return conteudo;
	}
	
	public void setConteudo(byte[] conteudo) {
		this.conteudo = conteudo;
	}

	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	public int getTamanho() {
		return tamanho;
	}

	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
