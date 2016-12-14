package br.ufpe.cin.chat.controle;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import br.ufpe.cin.chat.dados.Cliente;

public class Reconector implements Runnable {

	private Cliente cliente;

	public Reconector(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public void run() {
		while(true){
			try {
				System.out.println("Tentando reconexao...");
				Socket socket = new Socket(cliente.getIpServer(), cliente.getPortaServer());
				cliente.atualizarConexao(socket);
				cliente.setTentandoReconexao(false);
				break;
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}
