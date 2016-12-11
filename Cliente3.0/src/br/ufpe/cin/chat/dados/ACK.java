package br.ufpe.cin.chat.dados;

import java.io.Serializable;

public class ACK implements Serializable{
	
	private static final long serialVersionUID = -272043294328013341L;
	
	private int token;
	private int tipo;
	private String remetente;
	private String destinatario;
	private String fileName;
	
	/**
	 * TIPO 0:	ENVIADO
	 * TIPO 1:	RECEBIDO
	 * TIPO 2:	LIDO
	 * TIPO 3:	CONEXAO BEM SUCEDIDA
	 * TIPO 4:	SENHA INCORRETA
	 * TIPO 5:	MULTIACESSO
	 * TIPO 6: 	HANDSHAKE INICIO CONVERSA
	 * TIPO 7:	FORCAR CONVERSA
	 * TIPO 8:	ATUALIZAR STATUS DE CONEXAO DO USUARIO
	 * TIPO 9:  ENVIO DE ARQUIVO PARA O SERVIDOR
	 */
	
	public ACK(int token, int tipo){
		this.setToken(token);
		this.setTipo(tipo);
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getRemetente() {
		return remetente;
	}

	public void setRemetente(String remetente) {
		this.remetente = remetente;
	}

	public int getToken() {
		return token;
	}

	public void setToken(int token) {
		this.token = token;
	}
	
	public void setDestinatario(String destinatario){
		this.destinatario = destinatario;
	}
	
	public String getDestinatario(){
		return this.destinatario;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}