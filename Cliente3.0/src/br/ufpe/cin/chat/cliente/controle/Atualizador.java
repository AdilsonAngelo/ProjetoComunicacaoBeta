package br.ufpe.cin.chat.cliente.controle;

import java.util.Vector;

import javax.swing.JList;

import br.ufpe.cin.chat.cliente.dados.Cliente;

public class Atualizador implements Runnable {

	private Cliente cliente;
	private JList<String> listaUsuarios;
	private Vector<String> listaContatos;

	public Atualizador(Cliente cliente, JList<String> listaUsuarios) {
		this.cliente = cliente;
		this.listaUsuarios = listaUsuarios;
		this.listaContatos = new Vector<String>();
	}

	@Override
	public void run() {
		while(true){
			for(int i = 0; i < cliente.getListaUsuarios().size(); i++){
				if(!listaContatos.get(i).equals(cliente.getListaUsuarios().get(i))){
					this.listaContatos = cliente.getListaUsuarios();
					listaUsuarios.setListData(listaContatos);
					break;
				}
			}
		}
	}
}
