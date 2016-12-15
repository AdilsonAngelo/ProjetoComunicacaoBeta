package br.ufpe.cin.chat.controle;

import java.io.File;
import java.io.ObjectOutputStream;

import javax.swing.JProgressBar;

import br.ufpe.cin.chat.dados.Servidor;

public class SenderCaller implements Runnable {

	private Servidor servidor;
	private String destinatario;
	private JProgressBar progressBar;
	private File file;

	public SenderCaller(Servidor servidor, String destinatario, JProgressBar progressBar, File file){
		this.servidor = servidor;
		this.destinatario = destinatario;
		this.progressBar = progressBar;
		this.file = file;
	}


	@Override
	public void run(){
		try {
			Thread.sleep(2000);
			(new Thread(new FileSender(servidor, destinatario, progressBar, file))).start();
			System.out.println("startou sender");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
