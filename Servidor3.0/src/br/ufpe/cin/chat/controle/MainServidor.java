package br.ufpe.cin.chat.controle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTabbedPane;

import br.ufpe.cin.chat.dados.ACK;
import br.ufpe.cin.chat.dados.Autenticador;
import br.ufpe.cin.chat.dados.Servidor;
import br.ufpe.cin.chat.dados.Usuario;
import br.ufpe.cin.chat.view.PainelUsuario;

public class MainServidor implements Runnable {

	private ServerSocket server;
	private Servidor servidor;
	private JTabbedPane painelAbas;
	private boolean first;

	public MainServidor(ServerSocket server, JTabbedPane painelPrincipal) {
		this.server = server;
		this.servidor = new Servidor();
		this.painelAbas = painelPrincipal;
		this.first = true;
		(new Thread(new Encaminhador(servidor))).start();
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
					usuario.setLogado(true);
					if (servidor.contemUsuario(autenticador.getLogin())){
						servidor.logaUsuario(usuario, entrada, saida);
					}
					else{
						if(first){
							painelAbas.removeTabAt(0);
							first = false;
						}
						servidor.addUsuario(usuario, entrada, saida);
						PainelUsuario painelUsuario = new PainelUsuario(usuario.getLogin(), usuario.getIP(), true);
						painelAbas.add(usuario.getLogin(), painelUsuario);
						servidor.addPanelMap(usuario.getLogin(), painelUsuario);
					}
					(new Thread(new Armazenador(servidor, entrada, usuario.getLogin()))).start();
					(new Thread(new Heartbeat(servidor))).start();
					ack.setTipo(3);
				}
				else{
					ack.setTipo(4);
				}
				saida.writeObject(ack);
				(new Thread(new Broadcast(servidor))).start();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
