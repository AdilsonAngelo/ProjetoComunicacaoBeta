package br.ufpe.cin.chat.servidor.controle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import br.ufpe.cin.chat.servidor.dados.ACK;
import br.ufpe.cin.chat.servidor.dados.Autenticador;
import br.ufpe.cin.chat.servidor.dados.Usuario;

public class MainServidor implements Runnable {

	private ServerSocket server;
	private Servidor servidor;

	public MainServidor(ServerSocket server) {
		this.server = server;
	}

	@Override
	public void run() {
		while(true){
			try {
				Socket conexao = this.server.accept();
				ObjectOutputStream saida = new ObjectOutputStream(conexao.getOutputStream());
				ObjectInputStream entrada = new ObjectInputStream(conexao.getInputStream());
				Autenticador autenticador = (Autenticador) entrada.readObject();
				ACK ack = new ACK(0, 0);
				if (!servidor.isConectado(autenticador.getLogin())){
					Usuario usuario = new Usuario(autenticador.getLogin(), autenticador.getSenha(), autenticador.getIp());
					if (servidor.contemUsuario(autenticador.getLogin())){
						servidor.logaUsuario(usuario, entrada, saida);
					}
					else{
						servidor.addUsuario(usuario, entrada, saida);
					}
					ack.setTipo(3);
				}
				else{
					ack.setTipo(4);
				}
				saida.writeObject(ack);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

}
