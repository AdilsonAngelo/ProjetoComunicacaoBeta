package br.ufpe.cin.chat.controle;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
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
		int counter = 0;
		while(i.hasNext()){
			Pacote pacote = i.next();
			
			for(int j = 0; j < (pacote.getConteudo().length); j++){
				dados[counter] = pacote.getConteudo()[j];
			}
			counter++;
		}
		
		try {
			FileOutputStream fos = new FileOutputStream("ArquivosRecebidos/" + listaPacotes.getLast().getFileName());
			fos.write(dados);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
