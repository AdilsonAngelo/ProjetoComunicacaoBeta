package br.ufpe.cin.chat.cliente.controle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Vector;

import br.ufpe.cin.chat.cliente.dados.Cliente;

public class ReceptorCliente implements Runnable {

	private Cliente cliente;
	private ObjectInputStream entradaObjetos;

	public ReceptorCliente(ObjectInputStream entradaObjetos){
		this.entradaObjetos = entradaObjetos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		try{
			while(true){
				Object objetoRecebido = entradaObjetos.readObject();
				if (objetoRecebido instanceof Vector<?>){
					cliente.setListaUsuarios((Vector<String>) objetoRecebido);
				}
				else{
					(new Thread(new Encaminhamento(cliente, objetoRecebido))).start();
				}
			}

		}
		catch(IOException e){
			// perdeu conexao com servidor
		} 
		catch (ClassNotFoundException e) {

		}
	}
}
