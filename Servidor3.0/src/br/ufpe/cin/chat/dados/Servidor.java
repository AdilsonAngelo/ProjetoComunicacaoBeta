package br.ufpe.cin.chat.dados;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import br.ufpe.cin.chat.view.PainelUsuario;

public class Servidor {

	private Vector<Usuario> usuarios;
	
	private Map<String, ObjectOutputStream> mapaSaidas;
	private Map<String, ObjectInputStream> mapaEntradas;
	private Map<String, ObjectOutputStream> mapaSaidaArquivos;
	private Map<String, ObjectInputStream> mapaEntradaArquivos;
	
	private Map<String, Boolean> mapaPause;
	
	private Map<String, PainelUsuario> listaPanel;
	private Map<String, Vector<Object>> mapaPendencias;

	private LinkedList<Object> listaSaida;

	public Servidor(){
		this.usuarios = new Vector<Usuario>();
		this.mapaPause = new HashMap<String, Boolean>();
		this.mapaEntradas = new HashMap<String, ObjectInputStream>();
		this.mapaSaidas = new HashMap<String, ObjectOutputStream>();
		this.mapaSaidaArquivos = new HashMap<String, ObjectOutputStream>();
		this.mapaEntradaArquivos = new HashMap<String, ObjectInputStream>();
		this.listaSaida = new LinkedList<Object>();
		this.setListaPanel(new HashMap<String, PainelUsuario>());
		this.mapaPendencias = new HashMap<String, Vector<Object>>();
	}

	public void addUsuario(Usuario usuario, ObjectInputStream entrada, ObjectOutputStream saida){
		usuarios.add(usuario);
		mapaSaidas.put(usuario.getLogin(), saida);
		mapaEntradas.put(usuario.getLogin(), entrada);
	}

	public void addListaSaida(Object objeto){
		synchronized (listaSaida) {
			System.out.println("(servidor) objeto inserido na lista de saida");
			if (objeto instanceof Mensagem){
				String destinatario = ((Mensagem) objeto).getDestinatario();
				listaPanel.get(destinatario).setPendente(true);
			}
			this.listaSaida.add(objeto);
			System.out.println(listaSaida);
		}
	}

	public Object retiraListaSaida(){
		synchronized (listaSaida) {
			Object objeto = listaSaida.poll();
			if (objeto instanceof Mensagem){
				String destinatario = ((Mensagem) objeto).getDestinatario();
				listaPanel.get(destinatario).setPendente(false);
			}
			return objeto;
		}
	}

	public void gerarAck(Mensagem mensagem){
		ACK ack = new ACK(mensagem.getToken(), 0);
		ack.setDestinatario(mensagem.getDestinatario());
		ack.setRemetente(mensagem.getRemetente());
		addListaSaida(ack);
	}

	public Vector<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Vector<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Map<String, ObjectOutputStream> getMapaSaidas() {
		return mapaSaidas;
	}

	public void setMapaSaidas(Map<String, ObjectOutputStream> mapaSaidas) {
		this.mapaSaidas = mapaSaidas;
	}

	public Map<String, ObjectInputStream> getMapaEntradas() {
		return mapaEntradas;
	}

	public void setMapaEntradas(Map<String, ObjectInputStream> mapaEntradas) {
		this.mapaEntradas = mapaEntradas;
	}

	public LinkedList<Object> getListaSaida() {
		return listaSaida;
	}

	public void setListaSaida(LinkedList<Object> listaSaida) {
		this.listaSaida = listaSaida;
	}

	public synchronized void logaUsuario(Usuario usuario, ObjectInputStream entrada, ObjectOutputStream saida){
		synchronized (usuarios) {
			Iterator<Usuario> iterator = usuarios.iterator();
			Usuario usuario1 = null;
			while (iterator.hasNext()){
				usuario1 = iterator.next();
				if (usuario1.getLogin().equals(usuario.getLogin())){
					break;
				}
			}
			usuarios.remove(usuario1);
			listaPanel.get(usuario.getLogin()).setConectado(true);
			mapaEntradas.put(usuario.getLogin(), entrada);
			mapaSaidas.put(usuario.getLogin(), saida);
			usuarios.add(usuario);
			if (mapaPendencias.containsKey(usuario.getLogin())){
				listaSaida.addAll(mapaPendencias.get(usuario.getLogin()));
				mapaPendencias.remove(usuario.getLogin());
				listaPanel.get(usuario.getLogin()).setPendente(false);
			}
		}
	}

	public Map<String, Boolean> getMapaPause() {
		return mapaPause;
	}

	public synchronized void deslogaUsuario(String username){
		synchronized (usuarios) {
			Iterator<Usuario> iterator = usuarios.iterator();
			Usuario usuario = null;
			while (iterator.hasNext()){
				usuario = iterator.next();
				if (usuario.getLogin().equals(username)){
					break;
				}
			}
			mapaPendencias.put(username, new Vector<Object>());
			mapaEntradas.remove(usuario.getLogin());
			mapaSaidas.remove(usuario.getLogin());
			listaPanel.get(usuario.getLogin()).setConectado(false);
			usuario.setLogado(false);
			usuarios.add(usuario);
		}
	}

	public synchronized boolean contemUsuario(String username){
		synchronized (usuarios) {
			Iterator<Usuario> iterator = usuarios.iterator();
			while (iterator.hasNext()){
				Usuario usuario = iterator.next();
				if (usuario.getLogin().equals(username)){
					return true;
				}
			}
			return false;
		}
	}

	public synchronized boolean isConectado(String username){
		synchronized (usuarios) {
			Iterator<Usuario> iterator = usuarios.iterator();
			while (iterator.hasNext()){
				Usuario usuario = iterator.next();
				if (usuario.getLogin().equals(username) && usuario.isLogado()){
					return true;
				}
			}
			return false;
		}
	}

	public synchronized Vector<String> getUsuariosOnline(){
		synchronized (usuarios) {
			Vector<String> usuariosOnline = new Vector<String>();
			Iterator<Usuario> iterator = usuarios.iterator();
			while (iterator.hasNext()){
				Usuario usuario = iterator.next();
				if (usuario.isLogado()){
					usuariosOnline.add(usuario.getLogin());
				}
			}
			return usuariosOnline;
		}
	}

	public synchronized Vector<ObjectOutputStream> getAllSaidas(){
		Vector<ObjectOutputStream> saidas = new Vector<ObjectOutputStream>();
		Set<String> usuariosOnline = mapaSaidas.keySet();
		Iterator<String> iterator = usuariosOnline.iterator();
		while (iterator.hasNext()){
			saidas.add(mapaSaidas.get(iterator.next()));
		}
		return saidas;
	}

	public synchronized Map<String, PainelUsuario> getListaPanel() {
		return listaPanel;
	}

	public synchronized void setListaPanel(Map<String, PainelUsuario> listaPanel) {
		this.listaPanel = listaPanel;
	}

	public synchronized void addPanelMap(String usuario, PainelUsuario painel){
		this.listaPanel.put(usuario, painel);
	}

	public synchronized Map<String, Vector<Object>> getMapaPendencias() {
		return mapaPendencias;
	}
	
	public synchronized void setPendencia(String username){
		listaPanel.get(username).setPendente(true);
	}

	public Map<String, ObjectOutputStream> getMapaSaidaArquivos() {
		return mapaSaidaArquivos;
	}

	public void setMapaSaidaArquivos(Map<String, ObjectOutputStream> mapaSaidaArquivos) {
		this.mapaSaidaArquivos = mapaSaidaArquivos;
	}

	public Map<String, ObjectInputStream> getMapaEntradaArquivos() {
		return mapaEntradaArquivos;
	}

	public void setMapaEntradaArquivos(Map<String, ObjectInputStream> mapaEntradaArquivos) {
		this.mapaEntradaArquivos = mapaEntradaArquivos;
	}

	public synchronized File getFile(String fileName) {
		File folder = new File("ArquivosRecebidos\\");
		File[] fileList = folder.listFiles();
		for(int i = 0; folder.exists() && i < fileList.length; i++){
			File file = fileList[i];
			if(file.getName().equals(fileName)){
				System.out.println("Lendo arquivos da pasta. ARQUIVO: " + file.getName());
				return file;
			}
		}
		return null;
	}

}
