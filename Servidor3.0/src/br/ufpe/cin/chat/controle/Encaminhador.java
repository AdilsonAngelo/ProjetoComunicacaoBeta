package br.ufpe.cin.chat.controle;

import java.io.IOException;
import java.io.ObjectOutputStream;

import br.ufpe.cin.chat.dados.ACK;
import br.ufpe.cin.chat.dados.Mensagem;

public class Encaminhador implements Runnable{

	private Servidor servidor;
	
	public Encaminhador(Servidor servidor) {
		this.servidor = servidor;
	}
	
	@Override
	public void run() {
		ObjectOutputStream saida = null;
		while(true){
			if (!servidor.getListaSaida().isEmpty()){
				Object objeto = servidor.getListaSaida().poll();
				if (objeto instanceof ACK){
					ACK ack = (ACK) objeto;
					if (servidor.isConectado(ack.getDestinatario())){
						saida = servidor.getMapaSaidas().get(ack.getDestinatario());
					}
					else{
						servidor.addListaSaida(objeto);
						continue;
					}
				}
				else if (objeto instanceof Mensagem){
					Mensagem mensagem = (Mensagem) objeto;
					if (servidor.isConectado(mensagem.getDestinatario())){
						saida = servidor.getMapaSaidas().get(mensagem.getDestinatario());
					}
					else{
						servidor.addListaSaida(objeto);
						continue;
					}
				}
				try {
					saida.writeObject(objeto);
				} catch (IOException e) {
					servidor.addListaSaida(objeto);
					if (objeto instanceof ACK){
						servidor.deslogaUsuario(((ACK)objeto).getDestinatario());
					}
					else if (objeto instanceof Mensagem){
						servidor.deslogaUsuario(((Mensagem)objeto).getDestinatario());
					}
				}
			}
		}
	}
}
