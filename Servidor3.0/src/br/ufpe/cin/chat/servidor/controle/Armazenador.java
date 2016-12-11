package br.ufpe.cin.chat.servidor.controle;

import java.io.IOException;
import java.io.ObjectInputStream;

import br.ufpe.cin.chat.servidor.dados.Mensagem;

public class Armazenador implements Runnable {

	private Servidor servidor;
	private ObjectInputStream entrada;

	public Armazenador(Servidor servidor){
		this.servidor = servidor;
	}

	@Override
	public void run() {
		Object objetoRecebido = null;
		while(true){
			try {
				objetoRecebido = entrada.readObject();
				if (objetoRecebido instanceof Mensagem){
					servidor.gerarAck(((Mensagem) objetoRecebido).getToken());
				}
				else{
					
				}
				servidor.addListaSaida(objetoRecebido);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
