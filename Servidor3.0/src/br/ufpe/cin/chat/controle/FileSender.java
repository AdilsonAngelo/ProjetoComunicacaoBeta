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
	private String destinatario;

	public FileSender(Servidor servidor, String destinatario, JProgressBar progressBar, File file){
		this.servidor = servidor;
		this.destinatario = destinatario;
		this.saida = servidor.getMapaSaidaArquivos().get(destinatario);
		this.progressBar = progressBar;
		this.file = file;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(3500);
			System.out.println("startando file sender");
			String nomeDoArquivo = file.getName();
			Thread.sleep(150);
			saida.writeObject(nomeDoArquivo);
			long tamanho = file.length();
			saida.writeObject(new Integer((int) tamanho));
			FileInputStream fileIN = new FileInputStream(file);
			//			BufferedInputStream buffIN = new BufferedInputStream(fileIN);
			byte[] bytes = new byte[4000];
			int counter;
			int contador = 0;
			progressBar.setValue(0);
			progressBar.setMaximum((int)tamanho);
			counter = fileIN.read(bytes);
			while(counter > 0){
				if(!servidor.getMapaPause().get(destinatario)){
					System.out.println("enviando arquivos");
					System.out.println(counter);
					saida.write(bytes, 0, counter);
					contador += counter;
					progressBar.setValue(contador);
					progressBar.setStringPainted(true);
					counter = fileIN.read(bytes);
				}
			}
			progressBar.setValue(100);
			servidor.getMapaSaidaArquivos().remove(destinatario);
			fileIN.close();
			file.delete();
			saida.close();
		} catch (IOException e) {
			System.out.println("SOQUETE FEXADU");
			servidor.getMapaSaidaArquivos().remove(destinatario);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
