package br.ufpe.cin.chat.controle;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.LinkedList;

import br.ufpe.cin.chat.dados.Pacote;

public class TratadorArquivo implements Runnable {

	private LinkedList<Pacote> listaPacotes;
	
	public TratadorArquivo(LinkedList<Pacote> listaPacotes) {
		this.listaPacotes = listaPacotes;		
	}
	
	@Override
	public void run() {

		Iterator<Pacote> i = listaPacotes.iterator();
		
		byte[] dados = new byte[listaPacotes.peek().getTamanho()];
		
		while(i.hasNext()){
			Pacote pacote = i.next();
			
			for(int j = pacote.getToken(); j < pacote.getOffset(); j++){
				dados[j] = pacote.getConteudo()[j];
			}
			
		}
		
		try {
			FileOutputStream fos = new FileOutputStream("//ArquivosRecebidos/" + listaPacotes.getLast().getFileName());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
