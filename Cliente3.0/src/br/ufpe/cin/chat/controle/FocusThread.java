package br.ufpe.cin.chat.controle;

import javax.swing.JTextField;

import br.ufpe.cin.chat.dados.Cliente;

public class FocusThread implements Runnable {

	private JTextField campoMensagem;
	private Cliente cliente;
	private String conversandoCom;

	public FocusThread(JTextField campoMensagem, Cliente cliente, String conversandoCom) {
		this.campoMensagem = campoMensagem;
		this.cliente = cliente;
		this.conversandoCom = conversandoCom;
	}


	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(0);
				if (campoMensagem.isFocusOwner()){
					cliente.gerarAckLido(conversandoCom);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
