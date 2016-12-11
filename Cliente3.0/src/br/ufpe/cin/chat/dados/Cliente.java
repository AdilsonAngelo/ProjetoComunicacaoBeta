package br.ufpe.cin.chat.dados;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import br.ufpe.cin.chat.view.FrameConversa;

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
		try{
			if(msg.getDestinatario().equals(selfUser.getLogin())){
				conversas.addMsgRecebida(msg);
			}else if(msg.getRemetente().equals(selfUser.getLogin())){
				conversas.addMsgEnviada(msg);
			}
		}
		catch (NullPointerException e){
			iniciarConversa(msg.getRemetente());
			encaminharMsg(msg);
		}
	}

	public void encaminharACK(ACK ack){
		conversas.tratarACK(ack, ack.getDestinatario());
	}

	public void iniciarConversa(String conversandoCom){
		conversas.criarConversa(selfUser.getLogin(), conversandoCom);
		FrameConversa frame = new FrameConversa(this, conversandoCom);
		frame.setVisible(true);
	}

	public void addFilaEnvio(Mensagem mensagem){
		synchronized (filaEnvio) {
			filaEnvio.add(mensagem);
		}
	}

	public RepositorioConversas getConversas() {
		return conversas;
	}

	public Usuario getSelfUser() {
		return selfUser;
	}

	public Queue<Object> getFilaEnvio() {
		synchronized (filaEnvio) {
			return filaEnvio;
		}
	}

	public Vector<String> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(Vector<String> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}
}
