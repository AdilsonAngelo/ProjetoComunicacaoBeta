package br.ufpe.cin.chat.cliente.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import br.ufpe.cin.chat.cliente.dados.ACK;
import br.ufpe.cin.chat.cliente.dados.Autenticador;
import br.ufpe.cin.chat.cliente.dados.Cliente;

public class Testes {

	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(50000);
			Socket socket = server.accept();
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			
			Autenticador autenticador = (Autenticador) in.readObject();
			ACK ack = new ACK(1, 3);
			out.writeObject(ack);
			while(true){
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
