package br.ufpe.cin.chat.controle;

import javax.swing.JProgressBar;

import br.ufpe.cin.chat.dados.ACK;
import br.ufpe.cin.chat.dados.Servidor;

public class SenderCaller implements Runnable {

	private Servidor servidor;
	private String destinatario;
	private String fileName;

	public SenderCaller(Servidor servidor, String destinatario, String fileName){
		this.servidor = servidor;
		this.destinatario = destinatario;
		this.fileName = fileName;
	}


	@Override
	public void run(){
		ACK ack = new ACK(0, 8);
		ack.setDestinatario(destinatario);
		ack.setFileName(fileName);
		servidor.addListaSaida(ack);
	}

}
