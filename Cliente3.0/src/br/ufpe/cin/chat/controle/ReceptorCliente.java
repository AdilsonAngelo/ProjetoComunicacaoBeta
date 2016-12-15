package br.ufpe.cin.chat.controle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Vector;

import javax.swing.JOptionPane;

import br.ufpe.cin.chat.dados.ACK;
import br.ufpe.cin.chat.dados.Cliente;

public class ReceptorCliente implements Runnable {

	private Cliente cliente;
	private ObjectInputStream entradaObjetos;

	public ReceptorCliente(Cliente cliente, ObjectInputStream entradaObjetos){
		this.cliente = cliente;
		this.entradaObjetos = entradaObjetos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		try{
			while(true){
				if (Thread.currentThread().isInterrupted()){
					System.out.println("interrompeu receptor");
					return;
				}
				Object objetoRecebido = entradaObjetos.readObject();
				if (objetoRecebido instanceof Vector<?>){
					Vector<String> lista = (Vector<String>) objetoRecebido;
					lista.remove(cliente.getSelfUser().getLogin());
					cliente.setListaUsuarios(lista);
				}
				else{
					if(objetoRecebido instanceof ACK && ((ACK)objetoRecebido).getTipo() == 7){
					//	(new Thread(new FileReceiver(cliente, cliente.getFrame().getEntradaArquivos(), cliente.getFrame().getProgressoDownload()))).start();
						JOptionPane.showMessageDialog(cliente.getFrame(), "Novo Arquivo dispon�vel para Download");
						cliente.getFrame().getBotaoPause().setEnabled(true);
						cliente.getFrame().getBotaoCancelar().setEnabled(true);
					}else{
						(new Thread(new Encaminhamento(cliente, objetoRecebido))).start();
					}
				}
			}
		}
		catch(IOException e){
			System.out.println("perdeu conexao com servidor");
			if (!cliente.isTentandoReconexao()){
				(new Thread(new Reconector(cliente))).start();
				cliente.setTentandoReconexao(true);
			}
			Thread.currentThread().interrupt();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
