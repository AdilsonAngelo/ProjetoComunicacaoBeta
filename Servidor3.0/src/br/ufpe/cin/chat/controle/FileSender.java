package br.ufpe.cin.chat.controle;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import javax.swing.JProgressBar;

import br.ufpe.cin.chat.dados.Pacote;
import br.ufpe.cin.chat.dados.Servidor;

public class FileSender implements Runnable {

	private Servidor servidor;
	private ObjectOutputStream output;
	private JProgressBar progressBar;
	private LinkedList<Pacote> listaPacotes;
	
	public FileSender(LinkedList<Pacote> listaPacotes, Servidor servidor, ObjectOutputStream output, JProgressBar progressBar){
		this.servidor = servidor;
		this.output = output;
		this.progressBar = progressBar;
		this.listaPacotes = listaPacotes;
	}
	
	@Override
	public void run() {
		Pacote pacote = null;
		try {
			System.out.println("startando file sender");
			int begin = 0;
			progressBar.setValue(0);
			progressBar.setMaximum(listaPacotes.size()-1);
			while (!listaPacotes.isEmpty()){
				pacote = listaPacotes.poll();
				output.writeObject(pacote);
				progressBar.setValue(++begin);
				progressBar.setStringPainted(true);
			}
			progressBar.setValue(0);
		} catch (IOException e) {
			listaPacotes.addFirst(pacote);
		}
	}

}
