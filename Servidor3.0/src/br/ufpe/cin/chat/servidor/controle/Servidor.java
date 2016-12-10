package br.ufpe.cin.chat.servidor.controle;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import br.ufpe.cin.chat.servidor.dados.ACK;
import br.ufpe.cin.chat.servidor.dados.Mensagem;
import br.ufpe.cin.chat.servidor.dados.RepositorioUsuarios;

public class Servidor {

	private RepositorioUsuarios repUsuarios;
	private Queue<ACK> filaACK;
	private Queue<Mensagem> filaMSG;
	private Encaminhador encaminhador;
	private Vector<Armazenador> armazenador;
	
	public Servidor(){
		this.filaACK = new LinkedList<ACK>();
		this.filaMSG = new LinkedList<Mensagem>();
		this.repUsuarios = new RepositorioUsuarios();
	}
	
	
}
