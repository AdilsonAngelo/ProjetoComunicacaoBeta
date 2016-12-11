package br.ufpe.cin.chat.cliente.dados;

import java.util.LinkedList;

public class Conversa {

	private LinkedList<Mensagem> listaMensagens;
	private String meuLogin;
	private String conversandoCom;
	
	public Conversa(String meuLogin, String conversandoCom){
		this.listaMensagens = new LinkedList<Mensagem>();
		this.conversandoCom = conversandoCom;
		this.meuLogin = meuLogin;
	}
	
	public void inserirMensagem(Mensagem msg){
		this.listaMensagens.add(msg);
	}
	
	public String getConversandoCom(){
		return this.getConversandoCom();
	}

	public void tratarACK(ACK ack) {
		if(ack.getTipo() == 2){
			for(int i = 0; i < listaMensagens.size(); i++){
				Mensagem msg = listaMensagens.get(i);
				if(msg.getDestinatario().equals(conversandoCom) && msg.getToken() == ack.getToken()){
					msg.setRead(true);
				}
			}
		}else if(ack.getTipo() == 1){
			for(int i = 0; i < listaMensagens.size(); i++){
				Mensagem msg = listaMensagens.get(i);
				if(msg.getDestinatario().equals(conversandoCom) && msg.getToken() == ack.getToken()){
					msg.setReceived(true);
				}
			}
		}else if(ack.getTipo() == 0){
			for(int i = 0; i < listaMensagens.size(); i++){
				Mensagem msg = listaMensagens.get(i);
				if(msg.getDestinatario().equals(conversandoCom) && msg.getToken() == ack.getToken()){
					msg.setSent(true);
				}
			}
		}
	}

	public LinkedList<Mensagem> getMensagens(){
		return listaMensagens;
	}

	public LinkedList<Mensagem> getListaMensagens() {
		return listaMensagens;
	}
}
