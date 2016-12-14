package br.ufpe.cin.chat.dados;

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

	public synchronized void addMsgRecebida(Mensagem msg){
		synchronized (mapaConversas) {
			mapaConversas.get(msg.getRemetente()).inserirMensagem(msg);	
		}
	}

	public synchronized void addMsgEnviada(Mensagem msg){
		synchronized (mapaConversas){
			mapaConversas.get(msg.getDestinatario()).inserirMensagem(msg);
		}
	}

	public synchronized Conversa procurarConversa(String conversandoCom){
		synchronized (mapaConversas) {
			return mapaConversas.get(conversandoCom);
		}
	}

	public synchronized void tratarACK(ACK ack, String conversandoCom) {
		synchronized (mapaConversas) {
			mapaConversas.get(conversandoCom).tratarACK(ack);
		}
	}
	
	public synchronized boolean contemConversa(String conversandoCom){
		return this.mapaConversas.containsKey(conversandoCom);
	}

	/**
	 * rip arauto
	 */
}
