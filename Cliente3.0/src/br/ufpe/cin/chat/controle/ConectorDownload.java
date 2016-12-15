package br.ufpe.cin.chat.controle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import br.ufpe.cin.chat.dados.ACK;
import br.ufpe.cin.chat.dados.Cliente;

public class ConectorDownload implements Runnable {
	
	private Cliente cliente;
	private ACK ack;
	
	public ConectorDownload(Cliente cliente, ACK ack) {
		this.cliente = cliente;
		this.ack = ack;
	}

	@Override
	public void run() {
		try{
			Socket socket = new Socket(cliente.getIpServer(), cliente.getPortaServer()+1);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			ack.setRemetente(cliente.getSelfUser().getLogin());
			cliente.addFilaEnvio(ack);
			ACK ack = (ACK) in.readObject();
			(new Thread(new FileReceiver(cliente, in, cliente.getFrame().getProgressoDownload()))).start();
		}
		catch(IOException e){
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}
