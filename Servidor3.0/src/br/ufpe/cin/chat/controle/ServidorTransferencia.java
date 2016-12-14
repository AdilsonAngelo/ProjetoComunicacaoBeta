package br.ufpe.cin.chat.controle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import br.ufpe.cin.chat.dados.Autenticador;
import br.ufpe.cin.chat.dados.Servidor;

public class ServidorTransferencia implements Runnable {

	private Servidor servidor;
	private ServerSocket serverTransferencia;

	public ServidorTransferencia(Servidor servidor, ServerSocket serverTransferencia) {
		this.servidor = servidor;
		this.serverTransferencia = serverTransferencia;
	}

	@Override
	public void run() {
		while(true){
			try {
				Socket socket = serverTransferencia.accept();
				ObjectOutputStream saida = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
				Autenticador autenticador = (Autenticador) entrada.readObject();
				servidor.getMapaSaidaArquivos().put(autenticador.getLogin(), saida);
				servidor.getMapaEntradaArquivos().put(autenticador.getLogin(), entrada);
				System.out.println("inserido"+autenticador.getLogin());
				(new Thread(new FileReceiver(servidor, entrada, servidor.getListaPanel().get(autenticador.getLogin()).getProgressoRecebimento()))).start();
				System.out.println("Novo caminho de transferencia inserido");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

}
