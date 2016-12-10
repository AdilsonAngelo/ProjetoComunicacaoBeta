package br.ufpe.cin.chat.cliente.controle;

import br.ufpe.cin.chat.cliente.dados.ACK;
import br.ufpe.cin.chat.cliente.dados.Cliente;
import br.ufpe.cin.chat.cliente.dados.Mensagem;

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
			cliente.encaminharMsg((Mensagem) objeto);
		}
	}

}
