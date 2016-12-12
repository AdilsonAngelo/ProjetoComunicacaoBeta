package br.ufpe.cin.chat.controle;

import br.ufpe.cin.chat.dados.ACK;
import br.ufpe.cin.chat.dados.Cliente;
import br.ufpe.cin.chat.dados.Mensagem;

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
			System.out.println("ACK recebido ("+((ACK) objeto).getTipo()+")");
		}
		else if (objeto instanceof Mensagem){
			cliente.encaminharMsg((Mensagem) objeto);
			cliente.gerarAckRecebido((Mensagem) objeto);
		}
	}

}
