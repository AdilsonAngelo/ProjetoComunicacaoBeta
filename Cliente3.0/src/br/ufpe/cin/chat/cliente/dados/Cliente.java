package br.ufpe.cin.chat.cliente.dados;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class Cliente {
	
	private Queue<Object> filaEnvio;
	private RepositorioConversas conversas;
	private Usuario selfUser;
	private Vector<String> listaUsuarios;

	public Cliente(String login, String senha, String IP){
		this.filaEnvio = new LinkedList<Object>();
		this.conversas = new RepositorioConversas();
		this.selfUser = new Usuario(login, senha, IP);
		this.setListaUsuarios(new Vector<String>());
	}

	public void encaminharMsg(Mensagem msg){
		if(msg.getDestinatario().equals(selfUser.getLogin())){
			conversas.addMsgRecebida(msg);
		}else if(msg.getRemetente().equals(selfUser.getLogin())){
			conversas.addMsgEnviada(msg);
		}
	}

	public void encaminharACK(ACK ack){
		conversas.tratarACK(ack, ack.getDestinatario());
	}

	public void iniciarConversa(String conversandoCom){
		conversas.criarConversa(selfUser.getLogin(), conversandoCom);
	}

	public void addFilaEnvio(Mensagem mensagem){
		filaEnvio.add(mensagem);
	}

	public RepositorioConversas getConversas() {
		return conversas;
	}

	public Usuario getSelfUser() {
		return selfUser;
	}

	public Queue<Object> getFilaEnvio() {
		return filaEnvio;
	}

	public Vector<String> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(Vector<String> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}
}
