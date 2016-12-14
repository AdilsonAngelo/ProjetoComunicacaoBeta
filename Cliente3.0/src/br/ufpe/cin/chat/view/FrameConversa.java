/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpe.cin.chat.view;

import br.ufpe.cin.chat.controle.Apresentador;
import br.ufpe.cin.chat.controle.FileSender;
import br.ufpe.cin.chat.controle.FocusThread;
import br.ufpe.cin.chat.dados.Cliente;
import br.ufpe.cin.chat.dados.Mensagem;
import br.ufpe.cin.chat.util.TokenGenerator;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 *
 * @author Adilson
 */
public class FrameConversa extends javax.swing.JFrame {

	private static final long serialVersionUID = -6059384211484294031L;
	private Cliente cliente;
	private String conversandoCom;

	public FrameConversa(Cliente cliente, String conversandoCom) {
		super("Chat - "+conversandoCom);
		this.cliente = cliente;
		this.conversandoCom = conversandoCom;
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
		java.awt.GridBagConstraints gridBagConstraints;

		painelConversa = new javax.swing.JPanel();
		jScrollPane3 = new javax.swing.JScrollPane();
		campoConversa = new javax.swing.JTextArea();
		campoMensagem = new javax.swing.JTextField();
		campoMensagem.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER){
					addMensagemLista();
				}
			}
		});
		botaoEnviarMsg = new javax.swing.JButton();
		botaoUpload = new javax.swing.JButton();
		botaoUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chooseFile();
			}
		});
		painelTransf = new javax.swing.JTabbedPane();
		painelUploads = new javax.swing.JPanel();
		progressoUp = new javax.swing.JProgressBar();
		labelRTTUp = new javax.swing.JLabel();
		labelTempoUp = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);

		campoConversa.setEditable(false);
		campoConversa.setColumns(20);
		campoConversa.setRows(5);
		jScrollPane3.setViewportView(campoConversa);

		botaoEnviarMsg.setText("Enviar");
		botaoEnviarMsg.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				botaoEnviarMsgActionPerformed(evt);
			}
		});

		botaoUpload.setText("Enviar Arquivo");

		javax.swing.GroupLayout painelConversaLayout = new javax.swing.GroupLayout(painelConversa);
		painelConversa.setLayout(painelConversaLayout);
		painelConversaLayout.setHorizontalGroup(
				painelConversaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(painelConversaLayout.createSequentialGroup()
						.addGap(10, 10, 10)
						.addGroup(painelConversaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 627, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGroup(painelConversaLayout.createSequentialGroup()
										.addComponent(campoMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(6, 6, 6)
										.addComponent(botaoEnviarMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(6, 6, 6)
										.addComponent(botaoUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))))
				);
		painelConversaLayout.setVerticalGroup(
				painelConversaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(painelConversaLayout.createSequentialGroup()
						.addGap(11, 11, 11)
						.addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(6, 6, 6)
						.addGroup(painelConversaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(botaoEnviarMsg, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
								.addComponent(botaoUpload, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(campoMensagem))
						.addContainerGap(16, Short.MAX_VALUE))
				);

		labelRTTUp.setText("RTT: ");

		labelTempoUp.setText("Tempo estimado: ");

		javax.swing.GroupLayout painelUploadsLayout = new javax.swing.GroupLayout(painelUploads);
		painelUploadsLayout.setHorizontalGroup(
			painelUploadsLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(painelUploadsLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(painelUploadsLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(painelUploadsLayout.createSequentialGroup()
							.addGap(398)
							.addComponent(labelRTTUp, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE))
						.addGroup(painelUploadsLayout.createSequentialGroup()
							.addComponent(progressoUp, GroupLayout.PREFERRED_SIZE, 394, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(labelTempoUp, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)))
					.addGap(80))
		);
		painelUploadsLayout.setVerticalGroup(
			painelUploadsLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(painelUploadsLayout.createSequentialGroup()
					.addGap(11)
					.addComponent(labelRTTUp)
					.addGap(20)
					.addGroup(painelUploadsLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(labelTempoUp, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(progressoUp, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(15))
		);
		painelUploads.setLayout(painelUploadsLayout);

		painelTransf.addTab("Uploads", painelUploads);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addGap(15, 15, 15)
										.addComponent(painelTransf))
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
										.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(painelConversa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addContainerGap())
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(painelConversa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(painelTransf, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
						.addContainerGap())
				);

		painelTransf.getAccessibleContext().setAccessibleName("Downloads");

		pack();
		(new Thread(new Apresentador(cliente, campoConversa, conversandoCom))).start();
		(new Thread(new FocusThread(campoMensagem, cliente, conversandoCom))).start();
	}// </editor-fold>                        

	private void botaoEnviarMsgActionPerformed(java.awt.event.ActionEvent evt) { 
		addMensagemLista();
	}                        
	

	private void addMensagemLista() {
		if (campoMensagem.getText().trim().isEmpty()){

		}
		else {
			Mensagem mensagem = new Mensagem(TokenGenerator.generateToken(), campoMensagem.getText());
			mensagem.setRemetente(cliente.getSelfUser().getLogin());
			mensagem.setDestinatario(conversandoCom);
			cliente.encaminharMsg(mensagem);
			campoMensagem.setText("");
		}
	}

	private void chooseFile(){
		JFileChooser chooser = new JFileChooser();
		int retorno = chooser.showDialog(this, "Escolha o arquivo");
		if (retorno == JFileChooser.APPROVE_OPTION){
			File file = chooser.getSelectedFile();
			System.out.println(file.getName());
			(new Thread(new FileSender(conversandoCom, cliente, cliente.getFrame().getSaidaArquivos(), file, progressoUp))).start();
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
		/* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
		 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(FrameConversa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(FrameConversa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(FrameConversa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(FrameConversa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

	}

	// Variables declaration - do not modify                     
	private javax.swing.JButton botaoEnviarMsg;
	private javax.swing.JButton botaoUpload;
	private javax.swing.JTextArea campoConversa;
	private javax.swing.JTextField campoMensagem;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JLabel labelRTTUp;
	private javax.swing.JLabel labelTempoUp;
	private javax.swing.JPanel painelConversa;
	private javax.swing.JTabbedPane painelTransf;
	private javax.swing.JPanel painelUploads;
	private javax.swing.JProgressBar progressoUp;
	// End of variables declaration                   
}
