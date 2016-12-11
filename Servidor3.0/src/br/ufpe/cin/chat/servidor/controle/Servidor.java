package br.ufpe.cin.chat.servidor.controle;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Vector;

import br.ufpe.cin.chat.servidor.dados.ACK;
import br.ufpe.cin.chat.servidor.dados.Usuario;

public class Servidor {

	private Vector<Usuario> usuarios;
	private Map<String, ObjectOutputStream> mapaSaidas;
	private Map<String, ObjectInputStream> mapaEntradas;
	private LinkedList<Object> listaSaida;

	public Servidor(){
	}

	public void addUsuario(Usuario usuario, ObjectInputStream entrada, ObjectOutputStream saida){
		usuarios.add(usuario);
		mapaSaidas.put(usuario.getLogin(), saida);
		mapaEntradas.put(usuario.getLogin(), entrada);
	}

	public void addListaSaida(Object objeto){
		synchronized (listaSaida) {
			this.listaSaida.add(objeto);
		}
	}
	
	public Object retiraListaSaida(){
		synchronized (listaSaida) {
			return listaSaida.poll();
		}
	}
	
	public void gerarAck(int token){
		ACK ack = new ACK(token, 1);
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

	public void logaUsuario(Usuario usuario, ObjectInputStream entrada, ObjectOutputStream saida){
		Iterator<Usuario> iterator = usuarios.iterator();
		while (iterator.hasNext()){
			Usuario usuario1 = iterator.next();
			if (usuario1.getLogin().equals(usuario.getLogin())){
				usuarios.remove(usuario1);
				usuario1.setLogado(true);
				usuarios.add(usuario1);
			}
		}
	}
	
	public void deslogaUsuario(String username){
		Iterator<Usuario> iterator = usuarios.iterator();
		while (iterator.hasNext()){
			Usuario usuario = iterator.next();
			if (usuario.getLogin().equals(username)){
				usuarios.remove(usuario);
				usuario.setLogado(false);
				usuarios.add(usuario);
			}
		}
	}

	public boolean contemUsuario(String username){
		Iterator<Usuario> iterator = usuarios.iterator();
		while (iterator.hasNext()){
			Usuario usuario = iterator.next();
			if (usuario.getLogin().equals(username)){
				return true;
			}
		}
		return false;
	}

	public boolean isConectado(String username){
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
