package br.ufpe.cin.chat.controle;

import java.io.File;
import java.io.ObjectOutputStream;

import javax.swing.JProgressBar;

import br.ufpe.cin.chat.dados.Servidor;

public class SenderCaller implements Runnable {
	
	private Servidor servidor;
	private ObjectOutputStream saida;
	private JProgressBar progressBar;
	private File file;
	
	public SenderCaller(Servidor servidor, ObjectOutputStream saida, JProgressBar progressBar, File file){
		this.servidor = servidor;
		this.saida = saida;
		this.progressBar = progressBar;
		this.file = file;
	}

	
	@Override
	public void run(){
		try {
			Thread.sleep(2000);
			(new Thread(new FileSender(servidor, saida, progressBar, file))).start();
			System.out.println("startou sender");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
