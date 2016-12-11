package br.ufpe.cin.chat.dados;

import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import br.ufpe.cin.chat.view.FrameConversa;
import br.ufpe.cin.chat.view.FramePrincipal;

public class Cliente {

	private Queue<Object> filaEnvio;
	private RepositorioConversas conversas;
	private Usuario selfUser;
	private Vector<String> listaUsuarios;
	private String ipServer;
	private int portaServer;
	private FramePrincipal frame;

	public Cliente(String login, String senha, String IP, int portaServer){
		this.filaEnvio = new LinkedList<Object>();
		this.conversas = new RepositorioConversas();
		this.selfUser = new Usuario(login, senha, IP);
		this.setListaUsuarios(new Vector<String>());
		this.setPortaServer(portaServer);
	}

	public synchronized void encaminharMsg(Mensagem msg){
		try{
			if(msg.getDestinatario().equals(selfUser.getLogin())){
				conversas.addMsgRecebida(msg);
			}else if(msg.getRemetente().equals(selfUser.getLogin())){
				addFilaEnvio(msg);
				conversas.addMsgEnviada(msg);
			}
		}
		catch (NullPointerException e){
			iniciarConversa(msg.getRemetente());
			encaminharMsg(msg);
		}
	}

	public synchronized void encaminharACK(ACK ack){
		conversas.tratarACK(ack, ack.getDestinatario());
	}

	public synchronized void iniciarConversa(String conversandoCom){
		conversas.criarConversa(selfUser.getLogin(), conversandoCom);
		FrameConversa frame = new FrameConversa(this, conversandoCom);
		frame.setVisible(true);
	}

	public synchronized void addFilaEnvio(Mensagem mensagem){
		filaEnvio.add(mensagem);
	}

	public synchronized RepositorioConversas getConversas() {
		synchronized (conversas) {
			return conversas;
		}
	}

	public synchronized Usuario getSelfUser() {
		return selfUser;
	}

	public synchronized Queue<Object> getFilaEnvio() {
		return filaEnvio;
	}

	public synchronized Vector<String> getListaUsuarios() {
		return listaUsuarios;
	}

	public synchronized void setListaUsuarios(Vector<String> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public int getPortaServer() {
		return portaServer;
	}

	public void setPortaServer(int portaServer) {
		this.portaServer = portaServer;
	}

	public String getIpServer() {
		return ipServer;
	}

	public void setIpServer(String ipServer) {
		this.ipServer = ipServer;
	}

	public void atualizarConexao(Socket socket){
		frame.atualizaConexao(socket);
	}

	public FramePrincipal getFrame() {
		return frame;
	}

	public void setFrame(FramePrincipal frame) {
		this.frame = frame;
	}
}
