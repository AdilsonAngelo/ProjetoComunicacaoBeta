package br.ufpe.cin.chat.controle;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			DataInputStream in = new DataInputStream(socket.getInputStream());
			while(true){
				Thread.sleep(0);
				in.readBoolean();
				out.writeBoolean(true);
			}
		}
		catch (IOException e){
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
