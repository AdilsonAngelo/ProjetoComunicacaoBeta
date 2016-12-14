package br.ufpe.cin.chat.controle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JTextField;

import br.ufpe.cin.chat.dados.Cliente;

public class ThreadRTT implements Runnable {

	private Cliente cliente;
	private JTextField campoRTT;

	public ThreadRTT(Cliente cliente, JTextField campoRTT) {
		this.cliente = cliente;
		this.campoRTT = campoRTT;
	}

	@Override
	public void run() {
		try{
			System.out.println("startou rtt thread");
			Socket socket = new Socket(cliente.getIpServer(), cliente.getPortaServer()+2);
			System.out.println("Conectado ao servidor de RTT");
			ObjectOutputStream saida = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
			while(true){
				if (Thread.currentThread().isInterrupted()){
					break;
				}
				Thread.sleep(500);
				long tempo1 = System.currentTimeMillis();
				saida.writeObject("oi");
				String recebido = (String) entrada.readObject();
				long tempo2 = System.currentTimeMillis();
				long RTT = tempo2-tempo1;
				campoRTT.setText(String.valueOf(RTT));
			}
		}
		catch(IOException e){
			System.out.println("Conexao perdida, desconectando do servidor de RTT");
			Thread.currentThread().interrupt();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
