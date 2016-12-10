package br.ufpe.cin.chat.cliente.controle;

import java.util.LinkedList;
import java.util.Queue;

import br.ufpe.cin.chat.cliente.dados.ACK;
import br.ufpe.cin.chat.cliente.dados.Mensagem;
import br.ufpe.cin.chat.cliente.dados.RepositorioConversas;
import br.ufpe.cin.chat.cliente.dados.Usuario;

public class Cliente {
	
	private Queue<Object> filaTransm;
	private RepositorioConversas conversas;
	private Usuario selfUser;
	
	public Cliente(String login, String senha, String IP){
		this.filaTransm = new LinkedList<Object>();
		this.conversas = new RepositorioConversas();
		this.selfUser = new Usuario(login, senha, IP);
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
}
