package br.ufpe.cin.chat.cliente.controle;

import java.io.IOException;
import java.io.ObjectOutputStream;

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
		try{
			if (!cliente.getFilaEnvio().isEmpty()){
				objeto = cliente.getFilaEnvio().poll();
				saidaObjetos.writeObject(objeto);
			}
		}
		catch(IOException e){
			cliente.getFilaEnvio().add(objeto);
		}
	}
}
