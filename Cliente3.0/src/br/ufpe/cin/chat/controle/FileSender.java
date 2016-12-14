package br.ufpe.cin.chat.controle;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import javax.swing.JProgressBar;

import br.ufpe.cin.chat.dados.Cliente;

public class FileSender implements Runnable {

	private Cliente cliente;
	private ObjectOutputStream saida;
	private File file;
	private JProgressBar progressBar;
	private String conversandoCom;

	public FileSender(String conversandoCom, Cliente cliente, ObjectOutputStream saida, File file, JProgressBar progressBar) {
		this.cliente = cliente;
		this.conversandoCom = conversandoCom;
		this.saida = saida;
		this.file = file;
		this.progressBar = progressBar;
	}

	@Override
	public void run() {
		try {
			System.out.println("startando file sender");
			saida.writeObject(conversandoCom);
			saida.writeObject(file.getName());
			long tamanho = file.length();
			saida.writeObject(new Integer((int) tamanho));
			FileInputStream fileIN = new FileInputStream(file);
//			BufferedInputStream buffIN = new BufferedInputStream(fileIN);
			byte[] bytes = new byte[4000];
			int counter;
			int contador = 0;
			progressBar.setValue(0);
			progressBar.setMaximum((int)tamanho);
			while((counter = fileIN.read(bytes)) > 0){
				
//				int diferenca = 0;
//				Thread.sleep(0);
				saida.write(bytes, 0, counter);
				contador += counter;
				progressBar.setValue(contador);
				progressBar.setStringPainted(true);
//				if (diferenca < counter){
//					bytes = new byte[diferenca];
//					counter = diferenca;
//					fileOut.write(bytes, 0, counter);
//					break;
//				}
			}
			progressBar.setValue(100);
			fileIN.close();
			saida.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
