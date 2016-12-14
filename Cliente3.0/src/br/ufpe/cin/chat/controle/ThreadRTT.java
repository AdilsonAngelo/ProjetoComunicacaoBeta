package br.ufpe.cin.chat.controle;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ThreadRTT implements Runnable {

	private Socket socket;

	public ThreadRTT(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try{
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			DataInputStream in = new DataInputStream(socket.getInputStream());
			long tempo1 = System.currentTimeMillis();
			out.writeBoolean(true);
			in.readBoolean();
			long tempo2 = System.currentTimeMillis();
			long RTT = tempo2 - tempo1;
		}
		catch(IOException e){

		}

	}

}
