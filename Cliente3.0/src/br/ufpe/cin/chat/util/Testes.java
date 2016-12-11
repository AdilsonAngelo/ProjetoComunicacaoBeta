package br.ufpe.cin.chat.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import br.ufpe.cin.chat.dados.ACK;
import br.ufpe.cin.chat.dados.Autenticador;
import br.ufpe.cin.chat.dados.Cliente;

public class Testes {

	public static void main(String[] args) {
		System.out.println(TokenGenerator.generateToken());
	}

}
