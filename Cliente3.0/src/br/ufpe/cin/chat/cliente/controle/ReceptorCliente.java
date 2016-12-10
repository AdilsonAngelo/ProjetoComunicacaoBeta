package br.ufpe.cin.chat.cliente.controle;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ReceptorCliente implements Runnable {

	private Cliente cliente;
	private ObjectInputStream entradaObjetos;

	public ReceptorCliente(ObjectInputStream entradaObjetos){
		this.entradaObjetos = entradaObjetos;
	}

	@Override
	public void run() {
		try{
			while(true){
				Object objetoRecebido = entradaObjetos.readObject();
				(new Thread(new Encaminhamento(cliente, objetoRecebido))).start();
			}

		}
		catch(IOException e){
			// perdeu conexao com servidor
		} 
		catch (ClassNotFoundException e) {
			
		}
	}




}
