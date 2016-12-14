package br.ufpe.cin.chat.controle;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

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
			BufferedInputStream buffIN = new BufferedInputStream(fileIN);
			byte[] bytes = new byte[4000];
			int counter;
			int contador = 0;
			progressBar.setValue(0);
			progressBar.setMaximum((int)tamanho);
			while((counter = buffIN.read(bytes)) >= 0){
				Thread.sleep(0);
				saida.write(bytes, 0, counter);
				contador += counter;
				progressBar.setValue(contador);
				progressBar.setStringPainted(true);
				System.out.println(counter);
			}
			progressBar.setValue(100);
			buffIN.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
