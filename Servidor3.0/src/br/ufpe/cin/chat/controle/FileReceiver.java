package br.ufpe.cin.chat.controle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;

import javax.swing.JProgressBar;

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
				int diferenca = 0;
				byte[] bytes = new byte[4000];
				progressBar.setValue(0);
				progressBar.setMaximum((int)tamanho);
				File file = new File("ArquivosRecebidos/"+fileName);
				OutputStream fileOut = new FileOutputStream(file);
				while((counter = entrada.read(bytes)) > 0){
					fileOut.write(bytes, 0, counter);
					contador += counter;
					System.out.println("conta: "+contador);
					progressBar.setValue(contador);
					progressBar.setStringPainted(true);

				}
				progressBar.setValue(100);
				//S(new Thread(new SenderCaller(servidor, destinatario, servidor.getListaPanel().get(destinatario).getProgressoEnvio(), file))).start();
				fileOut.flush();
				fileOut.close();
			}
		}
		catch(IOException e){
			System.out.println("conexao finalizada");
			Thread.currentThread().interrupt();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
