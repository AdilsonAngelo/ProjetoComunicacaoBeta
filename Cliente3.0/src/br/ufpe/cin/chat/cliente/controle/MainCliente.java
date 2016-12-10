package br.ufpe.cin.chat.cliente.controle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import br.ufpe.cin.chat.cliente.dados.Cliente;

public class MainCliente implements Runnable {
	
	private Socket socket;
	private Cliente cliente;
	
	public MainCliente(Socket socket, String login, String senha, String IP) {
		this.socket = socket;
		this.cliente = new Cliente(login, senha, IP);
	}

	@Override
	public void run() {
		try{
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			(new Thread(new EmissorCliente(cliente, out))).start();
			(new Thread(new ReceptorCliente(in))).start();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}
