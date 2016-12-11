package br.ufpe.cin.chat.controle;

import java.io.IOException;
import java.io.ObjectInputStream;

import br.ufpe.cin.chat.dados.Mensagem;

public class Armazenador implements Runnable {

	private Servidor servidor;
	private ObjectInputStream entrada;
	private String fonte;

	public Armazenador(Servidor servidor, ObjectInputStream entrada, String fonte){
		this.servidor = servidor;
		this.entrada = entrada;
		this.fonte = fonte;
	}

	@Override
	public void run() {
		Object objetoRecebido = null;
		while(true){
			try {
				objetoRecebido = entrada.readObject();
				System.out.println("(servidor) objeto recebido");
				if (objetoRecebido instanceof Mensagem){
					servidor.gerarAck((Mensagem) objetoRecebido);
				}
				servidor.addListaSaida(objetoRecebido);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				servidor.deslogaUsuario(fonte);
			}
		}
	}
}
