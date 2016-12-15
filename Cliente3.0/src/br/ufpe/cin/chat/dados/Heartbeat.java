package br.ufpe.cin.chat.dados;

import java.io.IOException;

public class Heartbeat implements Runnable {

	private Cliente cliente;

	public Heartbeat(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public void run() {
		String user = "";
		while (true){
			try{
				Thread.sleep(3000);
				cliente.addFilaEnvio(new ACK(0, -1));
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
