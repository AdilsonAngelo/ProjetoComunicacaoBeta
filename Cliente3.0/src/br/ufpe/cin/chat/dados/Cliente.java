package br.ufpe.cin.chat.dados;

import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Vector;

import br.ufpe.cin.chat.util.Criptografia;
import br.ufpe.cin.chat.view.FrameConversa;
import br.ufpe.cin.chat.view.FramePrincipal;

public class Cliente {

	private Queue<Object> filaEnvio;
	private RepositorioConversas conversas;
	private Usuario selfUser;
	private Vector<String> listaUsuarios;
	private Map<String, FrameConversa> mapaFrameConversas;
	private String ipServer;
	private int portaServer;
	private FramePrincipal frame;
	private boolean tentandoReconexao;
	private boolean isEnviando;

	public Cliente(String login, String senha, String IP, String ipServer,int portaServer){
		this.mapaFrameConversas = new HashMap<String, FrameConversa>();
		this.filaEnvio = new LinkedList<Object>();
		this.conversas = new RepositorioConversas();
		this.selfUser = new Usuario(login, senha, IP);
		this.setIpServer(ipServer);
		this.setListaUsuarios(new Vector<String>());
		this.setPortaServer(portaServer);
		this.setTentandoReconexao(false);
		this.setEnviando(false);
	}

	public synchronized void encaminharMsg(Mensagem msg){
		synchronized(conversas){
			try{
				if(msg.getDestinatario().equals(selfUser.getLogin())){
					if(!mapaFrameConversas.get(msg.getRemetente()).isVisible()){
						mapaFrameConversas.get(msg.getRemetente()).setVisible(true);
					}
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
	}

	public synchronized void encaminharACK(ACK ack){
		if (ack.getTipo() == -1){

		}
		else if (ack.getTipo() == 0){
			conversas.tratarACK(ack, ack.getDestinatario());
		}
		else {
			conversas.tratarACK(ack, ack.getRemetente());
		}
	}

	public synchronized void iniciarConversa(String conversandoCom){
		if(!mapaFrameConversas.containsKey(conversandoCom)){
			conversas.criarConversa(selfUser.getLogin(), conversandoCom);
			FrameConversa frame = new FrameConversa(this, conversandoCom);
			mapaFrameConversas.put(conversandoCom, frame);
			frame.setVisible(true);
		}else{
			mapaFrameConversas.get(conversandoCom).setVisible(true);
		}
	}

	public synchronized void addFilaEnvio(Object objeto){
		filaEnvio.add(objeto);
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

	public void gerarAckRecebido(Mensagem mensagem){
		ACK ack = new ACK(mensagem.getToken(), 1);
		ack.setDestinatario(mensagem.getRemetente());
		ack.setRemetente(mensagem.getDestinatario());
		addFilaEnvio(ack);
		System.out.println("Gerado ACK de recebido");
	}

	public void gerarAckLido(String conversandoCom){
		if (!this.getConversas().procurarConversa(conversandoCom).getListaMensagens().isEmpty()){
			Mensagem mensagem = this.getConversas().procurarConversa(conversandoCom).getListaMensagens().getLast();
			if (!mensagem.isRead() && !mensagem.getRemetente().equals(this.getSelfUser().getLogin())){
				this.getConversas().procurarConversa(conversandoCom).getListaMensagens().getLast().setRead(true);
				ACK ack = new ACK(mensagem.getToken(), 2);
				ack.setDestinatario(mensagem.getRemetente());
				ack.setRemetente(mensagem.getDestinatario());
				addFilaEnvio(ack);
				System.out.println("Gerado ACK de lido");
			}
		}
	}

	public boolean isTentandoReconexao() {
		return tentandoReconexao;
	}

	public void setTentandoReconexao(boolean tentandoReconexao) {
		this.tentandoReconexao = tentandoReconexao;
	}

	public Map<String, FrameConversa> getMapaFrameConversas() {
		return mapaFrameConversas;
	}

	public boolean isEnviando() {
		return isEnviando;
	}

	public void setEnviando(boolean isEnviando) {
		this.isEnviando = isEnviando;
	}
}
