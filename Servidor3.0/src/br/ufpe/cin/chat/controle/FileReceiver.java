package br.ufpe.cin.chat.controle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;

import javax.swing.JProgressBar;


import br.ufpe.cin.chat.dados.Pacote;
import br.ufpe.cin.chat.dados.Servidor;

public class FileReceiver implements Runnable {

	private Servidor servidor;
	private ObjectInputStream entrada;
	private JProgressBar progressBar;
	private int progresso;

	public FileReceiver(Servidor servidor, ObjectInputStream entrada, JProgressBar progressBar) {
		this.servidor = servidor;
		this.entrada = entrada;
		this.progressBar = progressBar;
		this.progresso = 0;
	}

	@Override
	public void run() {
		try{
			while(true){
				Thread.sleep(0);
				String destinatario = (String) entrada.readObject();
				String fileName = (String) entrada.readObject();
				int tamanho = (Integer) entrada.readObject();
				int counter;
				int contador = 0;
				byte[] bytes = new byte[16*1024];
				progressBar.setValue(0);
				progressBar.setMaximum((int)tamanho);
				File file = new File("ArquivosRecebidos/"+fileName);
				FileOutputStream fileOut = new FileOutputStream(file);
				while((counter = entrada.read(bytes)) >= 0){
					fileOut.write(bytes, 0, counter);
					contador += counter;
					progressBar.setValue(contador);
					progressBar.setStringPainted(true);
				}
				progressBar.setValue(100);
				fileOut.flush();
				fileOut.close();
				(new Thread(new FileSender(servidor, servidor.getMapaSaidaArquivos().get(destinatario), servidor.getListaPanel().get(destinatario).getProgressoEnvio(), file))).start();
			}
		}
		catch(IOException e){
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
