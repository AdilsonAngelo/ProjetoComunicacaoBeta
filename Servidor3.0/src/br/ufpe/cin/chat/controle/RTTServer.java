package br.ufpe.cin.chat.controle;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import br.ufpe.cin.chat.dados.Servidor;

public class RTTServer implements Runnable {
	
	private Servidor servidor;
	private ServerSocket rttSocket;
	
	public RTTServer(Servidor servidor, ServerSocket rttSocket) {
		this.servidor = servidor;
		this.rttSocket = rttSocket;
	}
	
	@Override
	public void run() {
		while(true){
			try {
				Socket socket = rttSocket.accept();
				(new Thread(new ThreadRTT(servidor, socket))).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
