package br.ufpe.cin.chat.controle;

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
				Pacote pacote = (Pacote) entrada.readObject();
				barraProgresso.setMaximum(pacote.getTamanho());
				listaPacotes.add(pacote);
				barraProgresso.setValue((int) pacote.getOffset());
				System.out.println("Recebi pacote "+pacote.getToken());
				if(pacote.isLast()){
					(new Thread(new TratadorArquivo(listaPacotes))).start();
					barraProgresso.setMaximum(100);
					barraProgresso.setValue(100);
					barraProgresso.setValue(0);
				}
			}
		}
		catch (IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
