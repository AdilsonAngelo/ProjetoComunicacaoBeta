package br.ufpe.cin.chat.controle;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Vector;

import br.ufpe.cin.chat.dados.Servidor;

public class Broadcast implements Runnable {

	private Servidor servidor;

	public Broadcast(Servidor servidor) {
		this.servidor = servidor;
	}

	@Override
	public void run() {
		try{
			Vector<String> usuarioOnline = servidor.getUsuariosOnline();
			Vector<ObjectOutputStream> saidas = servidor.getAllSaidas();
			Iterator<ObjectOutputStream> iterator = saidas.iterator();
			while (iterator.hasNext()){
				iterator.next().writeObject(usuarioOnline);
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}
