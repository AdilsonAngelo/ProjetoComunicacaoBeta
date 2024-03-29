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
	
	private void desconectar(OutputStream fileOut, File file){
		try {
			fileOut.flush();
			fileOut.close();
			file.delete();
			cliente.setEnviando(false);
			barraProgresso.setValue(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		OutputStream fileOut = null;
		File file = null;
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
			file = new File("ArquivosRecebidos/"+fileName);
			fileOut = new FileOutputStream(file);
			JOptionPane.showMessageDialog(cliente.getFrame(), "Voce tem um novo arquivo disponivel para download!");
			cliente.getFrame().getBotaoInicio().setEnabled(true);
			cliente.getFrame().getBotaoCancelar().setEnabled(true);
			long tempo1 = System.nanoTime();
			while((counter = entrada.read(bytes)) > 0){
				
				fileOut.write(bytes, 0, counter);
				contador += counter;
				barraProgresso.setValue(contador);
				barraProgresso.setStringPainted(true);
				
				long tempo2 = System.nanoTime();
				
				double vMedia = contador/((tempo2-tempo1)/1000000);
				
				long diff = tamanho - contador;
				
				double tempoRestante = diff/vMedia;
				
				cliente.getFrame().getCampoTempoEstimado().setText((int)(tempoRestante/1000) + " s");
			}
		/*	campoTempoEstimado.setText(String.valueOf(1));
			campoTempoEstimado.setText(String.valueOf(0));*/
			barraProgresso.setValue(100);
			fileOut.flush();
			fileOut.close();
			cliente.setEnviando(false);
			cliente.getFrame().getBotaoPause().setEnabled(false);
		}
		catch (IOException e){
			desconectar(fileOut, file);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}