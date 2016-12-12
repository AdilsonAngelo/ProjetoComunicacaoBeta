package br.ufpe.cin.chat.controle;

import java.util.Iterator;
import java.util.LinkedList;

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
			if (cliente.getConversas().procurarConversa(conversandoCom).getListaMensagens().size() > tamanhoAnterior
					|| cliente.getConversas().procurarConversa(conversandoCom).getAtualiza()){
				cliente.getConversas().procurarConversa(conversandoCom).setAtualiza(false);
				tamanhoAnterior = cliente.getConversas().procurarConversa(conversandoCom).getListaMensagens().size();
				temp = "";
				Iterator<Mensagem> iterator;
				synchronized (cliente.getConversas().procurarConversa(conversandoCom).getListaMensagens()){
					LinkedList<Mensagem> temporario = cliente.getConversas().procurarConversa(conversandoCom).getListaMensagens();
					iterator = temporario.iterator();
					while(iterator.hasNext()){
						Mensagem mensagem =  iterator.next();
						
						if (mensagem.isRead() && !mensagem.getRemetente().equals(conversandoCom)){
							status = "*** ";
						}
						else if (mensagem.isReceived() && !mensagem.getRemetente().equals(conversandoCom)){
							status = "** ";
						}
						else if (mensagem.isSent() && !mensagem.getRemetente().equals(conversandoCom)){
							status = "* ";
						}
						temp += (status+mensagem.getRemetente()+" diz: "+mensagem.getContent() + "\n");
						status = "";
					}
				}
				campoConversa.setText(temp);
				campoConversa.setCaretPosition(campoConversa.getDocument().getLength());
			}
		}
	}
}
