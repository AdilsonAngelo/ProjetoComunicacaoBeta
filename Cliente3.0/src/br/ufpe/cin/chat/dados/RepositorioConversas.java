package br.ufpe.cin.chat.dados;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
		synchronized (mapaConversas) {
			mapaConversas.get(conversandoCom).tratarACK(ack);
		}
	}

	public List<Conversa> getAllConversas(){
		List<Conversa> retorno = new ArrayList<Conversa>();
		Set<String> allKeys = mapaConversas.keySet();
		for (String key : allKeys){
			retorno.add(mapaConversas.get(key));
		}
		return retorno;
	}

}
