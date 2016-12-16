package br.ufpe.cin.chat.controle;

import java.io.IOException;
import java.io.ObjectOutputStream;

import br.ufpe.cin.chat.dados.Cliente;
import br.ufpe.cin.chat.dados.Mensagem;
import br.ufpe.cin.chat.util.Criptografia;

public class EmissorCliente implements Runnable {

	private Cliente cliente;
	private ObjectOutputStream saidaObjetos;

	public EmissorCliente(Cliente cliente, ObjectOutputStream saidaObjetos) {
		this.cliente = cliente;
		this.saidaObjetos = saidaObjetos;
	}

	@Override
	public void run() {
		Object objeto = null;
		while(true){
			try{
				if (Thread.currentThread().isInterrupted()){
					System.out.println("interrompeu emissor");
					return;
				}
				if (!cliente.getFilaEnvio().isEmpty()){
					objeto = cliente.getFilaEnvio().poll();
					if (objeto instanceof Mensagem){
						((Mensagem)objeto).setContent(Criptografia.encripta(((Mensagem)objeto).getContent(), cliente.getSelfUser().getLogin()));
						System.out.println(((Mensagem)objeto).getContent());
					}
					saidaObjetos.writeObject(objeto);
				}
			}
			catch(IOException e){
				cliente.getFilaEnvio().add(objeto);
				if (!cliente.isTentandoReconexao()){
					(new Thread(new Reconector(cliente))).start();
					cliente.setTentandoReconexao(true);
				}
				Thread.currentThread().interrupt();
			}
		}
	}
}
