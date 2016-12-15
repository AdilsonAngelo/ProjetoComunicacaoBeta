/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpe.cin.chat.view;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JList;
import javax.swing.JOptionPane;

import br.ufpe.cin.chat.controle.Atualizador;
import br.ufpe.cin.chat.controle.EmissorCliente;
import br.ufpe.cin.chat.controle.FileReceiver;
import br.ufpe.cin.chat.controle.ReceptorCliente;
import br.ufpe.cin.chat.dados.ACK;
import br.ufpe.cin.chat.dados.Autenticador;
import br.ufpe.cin.chat.dados.Cliente;
import br.ufpe.cin.chat.dados.Heartbeat;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 *
 * @author Adilson
 */
public class FramePrincipal extends javax.swing.JFrame {

	private Socket socket;
	private Cliente cliente;
	private ObjectInputStream entradaObjetos;
	private ObjectOutputStream saidaObjetos;
	private static final long serialVersionUID = -1413926017169968935L;
	
    public FramePrincipal(Socket socket, Cliente cliente, ObjectInputStream entradaObjetos, ObjectOutputStream saidaObjetos) {
    	super(cliente.getSelfUser().getLogin());
		setResizable(false);
		this.socket = socket;
		this.cliente = cliente;
		this.entradaObjetos = entradaObjetos;
		this.saidaObjetos = saidaObjetos;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jList1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				JList<String> list = (JList<String>)event.getSource();
				if (event.getClickCount() == 2) {
					if ((String) list.getSelectedValue() != null){
						cliente.iniciarConversa((String) list.getSelectedValue());
					}
				}
			}
		});
        jList1.setSelectionBackground(new java.awt.Color(204, 51, 255));
        jPanel1 = new javax.swing.JPanel();
        lblRTT = new javax.swing.JLabel();
        campoRTT = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        lblTempo = new javax.swing.JLabel();
        campoTempoEstimado = new javax.swing.JTextField();
        botaoInicio = new javax.swing.JButton();
        botaoInicio.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		ACK ack = new ACK(-1, 7);
        		ack.setRemetente(cliente.getSelfUser().getLogin());
        		cliente.addFilaEnvio(ack);
        		botaoInicio.setEnabled(false);
        		botaoPause.setEnabled(true);
        	}
        });
        botaoInicio.setEnabled(false);
        botaoPause = new javax.swing.JButton();
        botaoPause.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ACK ack = new ACK(-1, 7);
        		ack.setRemetente(cliente.getSelfUser().getLogin());
        		cliente.addFilaEnvio(ack);
        		botaoPause.setEnabled(false);
        		botaoInicio.setEnabled(true);
        	}
        });
        botaoPause.setEnabled(false);
        botaoCancelar = new javax.swing.JButton();
        botaoCancelar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	
        	}
        });
        botaoCancelar.setEnabled(false);
        progressoDownload = new javax.swing.JProgressBar();
        progressoDownload.setForeground(Color.MAGENTA);
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setViewportView(jList1);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Downloads"));

        lblRTT.setText("RTT:");

        campoRTT.setEditable(false);

        jLabel3.setText("ms");

        lblTempo.setText("Tempo estimado:");

        botaoInicio.setText("Iniciar");

        botaoPause.setText("Pause");

        botaoCancelar.setText("Cancelar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(botaoInicio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botaoPause)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botaoCancelar)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblRTT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campoRTT, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                        .addComponent(lblTempo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campoTempoEstimado, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(progressoDownload, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botaoInicio)
                    .addComponent(botaoPause)
                    .addComponent(botaoCancelar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressoDownload, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRTT)
                    .addComponent(campoRTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(lblTempo)
                    .addComponent(campoTempoEstimado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel1.setText("Contatos");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        (new Thread(new Atualizador(cliente, jList1))).start();
		initThreads();
		cliente.setFrame(this);
    }// </editor-fold>                        
    
    private void initThreads(){
		(new Thread(new EmissorCliente(cliente, saidaObjetos))).start();
		(new Thread(new ReceptorCliente(cliente, entradaObjetos))).start();
		(new Thread(new Heartbeat (cliente))).start();
	}
    
    public void atualizaConexao(Socket socket) {
		try{
			this.socket = socket;
			ObjectOutputStream saida = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
			Autenticador autenticador = new Autenticador(cliente.getSelfUser().getLogin(), cliente.getSelfUser().getSenha(), cliente.getSelfUser().getIP());
			saida.writeObject(autenticador);
			ACK ack = (ACK) entrada.readObject();
			if (ack.getTipo() == 3){
				JOptionPane.showMessageDialog(this, "Reconectado ao servidor com sucesso");
				this.entradaObjetos = entrada;
				this.saidaObjetos = saida;
				initThreads();
			}
			else{
				JOptionPane.showMessageDialog(this, "Erro fatal (Usuario j� conectado)");
				System.exit(0);
			}
		}
		catch(IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

    public javax.swing.JButton getBotaoInicio() {
		return botaoInicio;
	}

	public javax.swing.JButton getBotaoPause() {
		return botaoPause;
	}

	public javax.swing.JButton getBotaoCancelar() {
		return botaoCancelar;
	}

	public javax.swing.JProgressBar getProgressoDownload() {
		return progressoDownload;
	}

	// Variables declaration - do not modify                     
    private javax.swing.JButton botaoInicio;
    private javax.swing.JButton botaoPause;
    private javax.swing.JButton botaoCancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblRTT;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblTempo;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar progressoDownload;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField campoRTT;
    private javax.swing.JTextField campoTempoEstimado;
    // End of variables declaration                   
}
