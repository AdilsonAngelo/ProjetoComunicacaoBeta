package br.ufpe.cin.chat.controle;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import br.ufpe.cin.chat.dados.ACK;
import br.ufpe.cin.chat.dados.Servidor;

public class Heartbeat implements Runnable {

	private Servidor servidor;

	public Heartbeat(Servidor servidor) {
		this.servidor = servidor;
	}

	@Override
	public void run() {
		String user = "";
		while (true){
			try{
				Thread.sleep(5000);
				Vector<String> usuariosOnline = servidor.getUsuariosOnline();
				if (!usuariosOnline.isEmpty()){
					Iterator<String> iterator = usuariosOnline.iterator();
					while(iterator.hasNext()){
						user = iterator.next();
						servidor.getMapaSaidas().get(user).writeObject(new ACK(0, -1));
						System.out.println("Enviando heartbeat");
					}
				}
			}
			catch(IOException e){
				if (!user.trim().isEmpty()){
					servidor.deslogaUsuario(user);
					System.out.println("usuario deslogado");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
