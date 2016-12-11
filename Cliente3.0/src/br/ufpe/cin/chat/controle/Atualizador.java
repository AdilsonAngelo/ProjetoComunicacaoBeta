package br.ufpe.cin.chat.controle;

import javax.swing.JList;

import br.ufpe.cin.chat.dados.Cliente;

public class Atualizador implements Runnable {

	private Cliente cliente;
	private JList<String> listaUsuarios;

	public Atualizador(Cliente cliente, JList<String> listaUsuarios) {
		this.cliente = cliente;
		this.listaUsuarios = listaUsuarios;
	}

	@Override
	public void run() {
		while(true){
			listaUsuarios.setListData(cliente.getListaUsuarios());
			try {
				Thread.sleep(2500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
