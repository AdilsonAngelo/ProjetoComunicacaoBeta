/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpe.cin.chat.view;
import java.awt.Color;
import java.io.IOException;
import java.net.ServerSocket;

import br.ufpe.cin.chat.controle.MainServidor;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTabbedPane;

/**
 *
 * @author Adilson
 */
public class FramePrincipal extends javax.swing.JFrame {
	
	private static final long serialVersionUID = 6325280011615059594L;
	private ServerSocket server;
	private int porta;
	
    public FramePrincipal(int porta) throws IOException {
    	setResizable(false);
    	server = new ServerSocket(porta);
    	this.porta = porta;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Servidor");
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{102, 364, 0};
        gridBagLayout.rowHeights = new int[]{20, 398, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        getContentPane().setLayout(gridBagLayout);
        
                labelPorta = new javax.swing.JLabel();
                
                        labelPorta.setText("Porta deste servidor:");
                        GridBagConstraints gbc_labelPorta = new GridBagConstraints();
                        gbc_labelPorta.anchor = GridBagConstraints.WEST;
                        gbc_labelPorta.fill = GridBagConstraints.VERTICAL;
                        gbc_labelPorta.insets = new Insets(0, 0, 5, 5);
                        gbc_labelPorta.gridx = 0;
                        gbc_labelPorta.gridy = 0;
                        getContentPane().add(labelPorta, gbc_labelPorta);
        painelPrincipal = new javax.swing.JTabbedPane();
        painelPrincipal.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        GridBagConstraints gbc_painelPrincipal = new GridBagConstraints();
        gbc_painelPrincipal.anchor = GridBagConstraints.NORTHWEST;
        gbc_painelPrincipal.gridwidth = 2;
        gbc_painelPrincipal.gridx = 0;
        gbc_painelPrincipal.gridy = 1;
        getContentPane().add(painelPrincipal, gbc_painelPrincipal);
        
        FirstPanel first = new FirstPanel();
        painelPrincipal.addTab("In�cio", first);
        
        
        this.labelPorta.setText(this.labelPorta.getText() + " " + this.porta);
        
        pack();
        (new Thread(new MainServidor(server, painelPrincipal))).start();
    }// </editor-fold> 
    
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
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FramePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FramePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FramePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FramePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
					new FramePrincipal(0).setVisible(true);
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JLabel labelPorta;
    private javax.swing.JTabbedPane painelPrincipal;
    // End of variables declaration                   
}
