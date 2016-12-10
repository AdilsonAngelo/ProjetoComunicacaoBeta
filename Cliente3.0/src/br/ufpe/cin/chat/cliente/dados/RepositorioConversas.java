package br.ufpe.cin.chat.cliente.dados;

import java.util.HashMap;
import java.util.Map;

public class RepositorioConversas {
	
	private Map<String, Conversa> mapaConversas;
	
	public RepositorioConversas(){
		this.mapaConversas = new HashMap<String, Conversa>();
	}
	
	public void criarConversa(String meuLogin, String conversandoCom){
		mapaConversas.put(conversandoCom, new Conversa(meuLogin, conversandoCom));
	}
	
	public void addMsgRecebida(Mensagem msg){
		mapaConversas.get(msg.getRemetente()).inserirMensagem(msg);
	}
	
	public void addMsgEnviada(Mensagem msg){
		mapaConversas.get(msg.getDestinatario()).inserirMensagem(msg);
	}
	
	public Conversa procurarConversa(String conversandoCom){
		return mapaConversas.get(conversandoCom);
	}

	public void tratarACK(ACK ack, String conversandoCom) {
		mapaConversas.get(conversandoCom).tratarACK(ack);
	}
	
}
