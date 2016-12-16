package br.ufpe.cin.chat.controle;

import br.ufpe.cin.chat.dados.ACK;
import br.ufpe.cin.chat.dados.Cliente;
import br.ufpe.cin.chat.dados.Mensagem;
import br.ufpe.cin.chat.util.Criptografia;

public class Encaminhamento implements Runnable {

	private Cliente cliente;
	private Object objeto;

	public Encaminhamento(Cliente cliente, Object objeto) {
		this.cliente = cliente;
		this.objeto = objeto;
	}

	@Override
	public void run() {
		if (objeto instanceof ACK){
			cliente.encaminharACK((ACK) objeto);
		}
		else if (objeto instanceof Mensagem){
			Mensagem mensagem = new Mensagem(((Mensagem) objeto).getToken(), Criptografia.decripta(((Mensagem) objeto).getContent(), ((Mensagem) objeto).getRemetente()));
			cliente.encaminharMsg(mensagem);
			cliente.gerarAckRecebido(mensagem);
		}
	}
}
