package br.ufpe.cin.chat.controle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Vector;

import br.ufpe.cin.chat.dados.Cliente;

public class ReceptorCliente implements Runnable {

	private Cliente cliente;
	private ObjectInputStream entradaObjetos;

	public ReceptorCliente(Cliente cliente, ObjectInputStream entradaObjetos){
		this.cliente = cliente;
		this.entradaObjetos = entradaObjetos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		try{
			while(true){
				if (Thread.currentThread().isInterrupted()){
					System.out.println("interrompeu receptor");
					return;
				}
				Object objetoRecebido = entradaObjetos.readObject();
				if (objetoRecebido instanceof Vector<?>){
					Vector<String> lista = (Vector<String>) objetoRecebido;
					lista.remove(cliente.getSelfUser().getLogin());
					cliente.setListaUsuarios(lista);
				}
				else{
					(new Thread(new Encaminhamento(cliente, objetoRecebido))).start();
				}
			}
		}
		catch(IOException e){
			System.out.println("perdeu conexao com servidor");
			(new Thread(new Reconector(cliente))).start();
			Thread.currentThread().interrupt();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
