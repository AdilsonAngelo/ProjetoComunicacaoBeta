package br.ufpe.cin.chat.cliente.controle;

import java.util.LinkedList;
import java.util.List;

import br.ufpe.cin.chat.cliente.dados.Conversa;
import br.ufpe.cin.chat.cliente.dados.Mensagem;

public class Arauto implements Runnable {

	private Cliente cliente;

	public Arauto(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public void run() {
		while(true){
			List<Conversa> listaConversas = cliente.getConversas().getAllConversas();
			for(int i = listaConversas.size()-1; i >= 0; i--){
				Conversa conversa = listaConversas.get(i);
				LinkedList<Mensagem> listaMensagem = conversa.getMensagens();
				for(int j = listaMensagem.size(); j >= 0; j--){
					Mensagem mensagem = listaMensagem.peekLast();
					if (mensagem.getRemetente().equals(cliente.getSelfUser().getLogin()) && !mensagem.isSent()){
						cliente.addFilaEnvio(mensagem);
						break;
					}
				}
			}
		}
	}
}
