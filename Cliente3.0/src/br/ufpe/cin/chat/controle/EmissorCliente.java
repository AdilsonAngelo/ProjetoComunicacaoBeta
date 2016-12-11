package br.ufpe.cin.chat.controle;

import java.io.IOException;
import java.io.ObjectOutputStream;

import br.ufpe.cin.chat.dados.Cliente;

public class EmissorCliente implements Runnable {

	private Cliente cliente;
	private ObjectOutputStream saidaObjetos;

	public EmissorCliente(Cliente cliente, ObjectOutputStream saidaObjetos) {
		this.cliente = cliente;
		this.saidaObjetos = saidaObjetos;
	}

	@Override
	public void run() {
		Object objeto = null;
		while(true){
			try{
				if (Thread.currentThread().isInterrupted()){
					System.out.println("interrompeu emissor");
					return;
				}
				if (!cliente.getFilaEnvio().isEmpty()){
					objeto = cliente.getFilaEnvio().poll();
					saidaObjetos.writeObject(objeto);
				}
			}
			catch(IOException e){
				cliente.getFilaEnvio().add(objeto);
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
		}
	}
}
