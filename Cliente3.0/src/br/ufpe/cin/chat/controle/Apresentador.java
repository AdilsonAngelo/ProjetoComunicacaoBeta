package br.ufpe.cin.chat.controle;

import java.util.Iterator;

import javax.swing.JTextArea;

import br.ufpe.cin.chat.dados.Cliente;
import br.ufpe.cin.chat.dados.Mensagem;

public class Apresentador implements Runnable {

	private Cliente cliente;
	private JTextArea campoConversa;
	private String conversandoCom;
	private int tamanhoAnterior;

	public Apresentador(Cliente cliente, JTextArea campoConversa, String conversandoCom) {
		this.cliente = cliente;
		this.campoConversa = campoConversa;
		this.conversandoCom = conversandoCom;
		this.tamanhoAnterior = 0;
	}

	@Override
	public void run() {
		String status = "";
		String temp = "";
		while(true){
			if (cliente.getConversas().procurarConversa(conversandoCom).getListaMensagens().size() > tamanhoAnterior){
				tamanhoAnterior = cliente.getConversas().procurarConversa(conversandoCom).getListaMensagens().size();
				temp = "";
				Iterator<Mensagem> iterator = cliente.getConversas().procurarConversa(conversandoCom).getListaMensagens().iterator();
				while(iterator.hasNext()){
					Mensagem mensagem = iterator.next();
					if (mensagem.isRead()){
						status = "*** ";
					}
					else if (mensagem.isReceived()){
						status = "** ";
					}
					else if (mensagem.isSent() && !mensagem.getRemetente().equals(conversandoCom)){
						status = "* ";
					}
					temp += (status+mensagem.getRemetente()+" diz: "+mensagem.getContent() + "\n");
				}
				campoConversa.setText(temp);
			}
		}
	}
}
