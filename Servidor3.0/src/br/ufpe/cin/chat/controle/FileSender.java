package br.ufpe.cin.chat.controle;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JProgressBar;

import br.ufpe.cin.chat.dados.Servidor;

public class FileSender implements Runnable {

	private Servidor servidor;
	private ObjectOutputStream saida;
	private JProgressBar progressBar;
	private File file;
	
	public FileSender(Servidor servidor, ObjectOutputStream saida, JProgressBar progressBar, File file){
		this.servidor = servidor;
		this.saida = saida;
		this.progressBar = progressBar;
		this.file = file;
	}
	
	@Override
	public void run() {
		try {
			System.out.println("startando file sender");
			saida.writeObject(file.getName());
			long tamanho = file.length();
			saida.writeObject(new Integer((int) tamanho));
			FileInputStream fileIN = new FileInputStream(file);
			BufferedInputStream buffIN = new BufferedInputStream(fileIN);
			byte[] bytes = new byte[4000];
			int counter;
			int contador = 0;
			progressBar.setValue(0);
			progressBar.setMaximum((int)tamanho);
			while((counter = buffIN.read(bytes)) > 0){
				saida.write(bytes, 0, counter);
				contador += counter;
				progressBar.setValue(contador);
				progressBar.setStringPainted(true);
			}
			progressBar.setValue(100);
			buffIN.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
