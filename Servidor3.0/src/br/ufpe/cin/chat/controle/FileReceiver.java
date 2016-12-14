package br.ufpe.cin.chat.controle;

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
	private LinkedList<Pacote> listaPacotes;

	public FileReceiver(Servidor servidor, ObjectInputStream entrada, JProgressBar progressBar) {
		this.servidor = servidor;
		this.entrada = entrada;
		this.progressBar = progressBar;
		this.progresso = 0;
		this.listaPacotes = new LinkedList<Pacote>();
	}

	@Override
	public void run() {
		try{
			while(true){
				Pacote pacote = (Pacote) entrada.readObject();
				listaPacotes.add(pacote);
				progressBar.setMaximum(pacote.getTamanho());
				progresso += pacote.getConteudo().length;
				progressBar.setValue(progresso);
				progressBar.setStringPainted(true);
				System.out.println("Recebi pacote "+pacote.getToken());
				if(pacote.isLast()){
					(new Thread(new FileSender(listaPacotes, servidor, servidor.getMapaSaidaArquivos().get(pacote.getDestinatario()), servidor.getListaPanel().get(pacote.getDestinatario()).getProgressoEnvio()))).start();
				}
			}
		}
		catch(IOException e){
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
