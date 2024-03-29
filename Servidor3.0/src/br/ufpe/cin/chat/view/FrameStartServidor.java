/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpe.cin.chat.view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;//Tem que adaptar os imports pra chamar do c�digo novo
import java.io.IOException;

import javax.swing.JOptionPane;

/**
 *
 * @author Adilson
 */
public class FrameStartServidor extends javax.swing.JFrame {

	private static final long serialVersionUID = 1748799331220547480L;
	public FrameStartServidor() {
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated Code">                          
	private void initComponents() {

		BotaoIniciar = new javax.swing.JButton();
		LabelPorta = new javax.swing.JLabel();
		TextoPorta = new javax.swing.JTextField();
		TextoPorta.setText("50000");
		TextoPorta.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					startServidor();
				}
			}
		});

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		BotaoIniciar.setText("Iniciar servidor");
		BotaoIniciar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				BotaoIniciarActionPerformed(evt);
			}
		});

		LabelPorta.setText("Definir porta do servidor");

		TextoPorta.setToolTipText("Porta");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap(137, Short.MAX_VALUE)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(LabelPorta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(BotaoIniciar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(TextoPorta))
						.addContainerGap(146, Short.MAX_VALUE))
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addContainerGap(49, Short.MAX_VALUE)
						.addComponent(LabelPorta, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(TextoPorta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 123, Short.MAX_VALUE)
						.addComponent(BotaoIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(31, Short.MAX_VALUE))
				);

		pack();
	}// </editor-fold>                        

	private void BotaoIniciarActionPerformed(java.awt.event.ActionEvent evt) {
		startServidor();
	}                   

	private void startServidor(){
		try{
			FramePrincipal frame = new FramePrincipal(Integer.parseInt(TextoPorta.getText()));
			frame.setVisible(true);
			this.dispose();
		}
		catch(IOException e){
			JOptionPane.showMessageDialog(this, "Falha ao iniciar servidor");
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
				if ("Meu CU".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(FrameStartServidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(FrameStartServidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(FrameStartServidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(FrameStartServidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new FrameStartServidor().setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify                     
	private javax.swing.JButton BotaoIniciar;
	private javax.swing.JLabel LabelPorta;
	private javax.swing.JTextField TextoPorta;
	// End of variables declaration                   
}
