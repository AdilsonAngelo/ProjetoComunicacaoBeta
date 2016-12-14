package br.ufpe.cin.chat.controle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;

import javax.swing.JProgressBar;

import br.ufpe.cin.chat.dados.Cliente;
import br.ufpe.cin.chat.dados.Pacote;

public class FileReceiver implements Runnable {
	
	private Cliente cliente;
	private ObjectInputStream entrada;
	private JProgressBar barraProgresso;
	private LinkedList<Pacote> listaPacotes;
	private int begin;
	
	public FileReceiver(Cliente cliente, ObjectInputStream entrada, JProgressBar barraProgresso) {
		this.cliente = cliente;
		this.entrada = entrada;
		this.barraProgresso = barraProgresso;
		this.listaPacotes = new LinkedList<Pacote>();
		this.begin = 0;
	}

	@Override
	public void run() {
		try{
			while(true){
				Thread.sleep(0);
				String fileName = (String) entrada.readObject();
				int tamanho = (Integer) entrada.readObject();
				int counter;
				int contador = 0;
				byte[] bytes = new byte[4000];
				barraProgresso.setValue(0);
				barraProgresso.setMaximum((int)tamanho);
				FileOutputStream fileOut = new FileOutputStream(new File("ArquivosRecebidos"+fileName));
				while((counter = entrada.read(bytes)) > 0){
					fileOut.write(bytes, 0, counter);
					contador += counter;
					barraProgresso.setValue(contador);
					barraProgresso.setStringPainted(true);
				}
				barraProgresso.setValue(100);
				fileOut.flush();
				fileOut.close();
			}
		}
		catch (IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
