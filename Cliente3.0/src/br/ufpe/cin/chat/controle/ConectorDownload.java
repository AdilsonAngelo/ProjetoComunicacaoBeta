package br.ufpe.cin.chat.controle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import br.ufpe.cin.chat.dados.ACK;
import br.ufpe.cin.chat.dados.Autenticador;
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
			System.out.println("ADICIONANDO ACK TIPO: "+ ack.getTipo() + " A FILA DE ENVIO DO CLIENTE");
			Autenticador autenticador = new Autenticador(cliente.getSelfUser().getLogin(), cliente.getSelfUser().getSenha(), cliente.getSelfUser().getIP());
			out.writeObject(autenticador);
			System.out.println("AUTENTICADOR DO SEGUNDO CLIENTE ENVIADO AO SERVIDOR");
			//	ACK ack = (ACK) in.readObject();
			System.out.println("\nINICIANDO FILERECEIVER CLIENTE");
			(new Thread(new FileReceiver(cliente, in, cliente.getFrame().getProgressoDownload(), cliente.getFrame().getCampoTempoEstimado()))).start();
		}
		catch(IOException e){

		}

	}

}
