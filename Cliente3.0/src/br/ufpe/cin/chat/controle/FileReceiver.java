package br.ufpe.cin.chat.controle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import br.ufpe.cin.chat.dados.Cliente;
import br.ufpe.cin.chat.dados.Pacote;

public class FileReceiver implements Runnable {

	private Cliente cliente;
	private ObjectInputStream entrada;
	private JProgressBar barraProgresso;
	private LinkedList<Pacote> listaPacotes;
	private int begin;
	private JTextField campoTempoEstimado;

	public FileReceiver(Cliente cliente, ObjectInputStream entrada, JProgressBar barraProgresso, JTextField campoTempoEstimado) {
		this.cliente = cliente;
		this.entrada = entrada;
		this.barraProgresso = barraProgresso;
		this.listaPacotes = new LinkedList<Pacote>();
		this.begin = 0;
		this.campoTempoEstimado = campoTempoEstimado;
	}

	@Override
	public void run() {
		try{
			String fileName = (String) entrada.readObject();
			System.out.println("RECEBENDO ARQUIVO NOVO: " + fileName);
			int tamanho = (Integer) entrada.readObject();
			System.out.println("TAMANHO DO ARQUIVO: " + tamanho + " bytes");
			int counter;
			int contador = 0;
			byte[] bytes = new byte[4000];
			barraProgresso.setValue(0);
			barraProgresso.setMaximum((int)tamanho);
			OutputStream fileOut = new FileOutputStream(new File("ArquivosRecebidos/"+fileName));
			JOptionPane.showMessageDialog(cliente.getFrame(), "Voce tem um novo arquivo disponivel para download!");
			cliente.getFrame().getBotaoInicio().setEnabled(true);
			cliente.getFrame().getBotaoCancelar().setEnabled(true);
			Long startTime = System.nanoTime();
			while((counter = entrada.read(bytes)) > 0){
				fileOut.write(bytes, 0, counter);
				contador += counter;
				barraProgresso.setValue(contador);
				barraProgresso.setStringPainted(true);
				Long elapsedTime = System.nanoTime() - startTime;
				Long allTimeForDownloading = (elapsedTime * tamanho / contador);
				Long remainingTime = allTimeForDownloading - elapsedTime;
				remainingTime = remainingTime/1000;
				campoTempoEstimado.setText(String.valueOf((int)(remainingTime/10000000)+3));
			}
			campoTempoEstimado.setText(String.valueOf(1));
			campoTempoEstimado.setText(String.valueOf(0));
			barraProgresso.setValue(100);
			fileOut.flush();
			fileOut.close();
			cliente.getFrame().getBotaoPause().setEnabled(false);
		}
		catch (IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}