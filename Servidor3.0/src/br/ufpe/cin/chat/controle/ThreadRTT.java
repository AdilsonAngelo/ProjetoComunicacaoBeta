package br.ufpe.cin.chat.controle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import br.ufpe.cin.chat.dados.Servidor;

public class ThreadRTT implements Runnable {

	private Servidor servidor;
	private Socket socket;

	public ThreadRTT(Servidor servidor, Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try{
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			while(true){
				if (Thread.currentThread().isInterrupted()){
					break;
				}
				Thread.sleep(0);
				String recebido = (String) in.readObject();
				out.writeObject("oi");
			}
		}
		catch (IOException e){
			System.out.println("cliente desconectado, interrompendo thread rtt");
			Thread.currentThread().interrupt();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
