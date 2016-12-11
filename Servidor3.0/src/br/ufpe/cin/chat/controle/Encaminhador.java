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
				System.out.println("(servidor) objeto detectado");
				Object objeto = servidor.retiraListaSaida();
				if (objeto instanceof ACK){
					ACK ack = (ACK) objeto;
					if (servidor.isConectado(ack.getDestinatario())){
						saida = servidor.getMapaSaidas().get(ack.getDestinatario());
						System.out.println("(servidor) saida encontrada");
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
						System.out.println("(servidor) saida encontrada");
					}
					else{
						System.out.println("(servidor) usuario destino deslogado");
						servidor.addListaSaida(objeto);
						continue;
					}
				}
				try {
					saida.writeObject(objeto);
					System.out.println("(servidor) objeto encaminhado");
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
