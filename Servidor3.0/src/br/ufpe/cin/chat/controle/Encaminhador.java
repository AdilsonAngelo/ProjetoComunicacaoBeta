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
			try {
				Thread.sleep(0);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if (!servidor.getListaSaida().isEmpty()){
				System.out.println("(servidor) objeto detectado");
				Object objeto = servidor.retiraListaSaida();
				if (objeto instanceof ACK){
					ACK ack = (ACK) objeto;
					if (ack.getTipo() == 0){
						saida = servidor.getMapaSaidas().get(ack.getRemetente());
						System.out.println("(servidor) saida encontrada");
					}
					else if (servidor.isConectado(ack.getDestinatario())){
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
					Thread.sleep(250);
				} catch (IOException e) {
					if (objeto instanceof ACK){
						servidor.deslogaUsuario(((ACK)objeto).getDestinatario());
						servidor.getMapaPendencias().get(((ACK)objeto).getDestinatario()).add(objeto);
						servidor.setPendencia(((ACK)objeto).getDestinatario());
					}
					else if (objeto instanceof Mensagem){
						servidor.deslogaUsuario(((Mensagem)objeto).getDestinatario());
						servidor.getMapaPendencias().get(((Mensagem)objeto).getDestinatario()).add(objeto);
						servidor.setPendencia(((Mensagem)objeto).getDestinatario());
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
