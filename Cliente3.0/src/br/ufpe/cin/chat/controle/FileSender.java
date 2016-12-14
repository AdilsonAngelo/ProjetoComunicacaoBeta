package br.ufpe.cin.chat.controle;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.swing.JProgressBar;

import br.ufpe.cin.chat.dados.Cliente;
import br.ufpe.cin.chat.dados.Pacote;

public class FileSender implements Runnable {

	private Cliente cliente;
	private ObjectOutputStream saida;
	private File file;
	private JProgressBar progressBar;
	private String conversandoCom;

	public FileSender(String conversandoCom, Cliente cliente, ObjectOutputStream saida, File file, JProgressBar progressBar) {
		this.cliente = cliente;
		this.conversandoCom = conversandoCom;
		this.saida = saida;
		this.file = file;
		this.progressBar = progressBar;
	}

	@Override
	public void run() {
		try {
			System.out.println("startando file sender");
			Path caminho = Paths.get(file.getCanonicalPath());
			byte[] dados = Files.readAllBytes(caminho);
			int begin = 0;
			int offset = begin+(1024);
			int token = 0;
			progressBar.setValue(0);
			progressBar.setMaximum(dados.length);;
			while (offset < dados.length){
				byte[] janela = Arrays.copyOfRange(dados, begin, offset);
				Pacote pacote = new Pacote(token, janela, offset);
				pacote.setFileName(file.getName());
				pacote.setTamanho(dados.length);
				pacote.setRemetente(cliente.getSelfUser().getLogin());
				pacote.setDestinatario(conversandoCom);
				System.out.println("enviando pacotes");
				saida.writeObject(pacote);
				progressBar.setValue(offset);
				progressBar.setStringPainted(true);
				begin = offset+1;
				offset = begin+1024;
				token++;
			}
			offset = dados.length-1;
			byte[] janela = Arrays.copyOfRange(dados, begin, dados.length-1);
			Pacote pacote = new Pacote(token, janela, offset);
			pacote.setLast(true);
			pacote.setDestinatario(conversandoCom);
			pacote.setFileName(file.getName());
			pacote.setRemetente(cliente.getSelfUser().getLogin());
			System.out.println("enviando ultimo pacote");
			saida.writeObject(pacote);
			progressBar.setValue(100);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
