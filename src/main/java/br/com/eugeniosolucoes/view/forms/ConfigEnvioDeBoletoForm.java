/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * Principal.java
 *
 * Created on 21/01/2011, 07:34:43
 */
package br.com.eugeniosolucoes.view.forms;

import br.com.eugeniosolucoes.controle.Controlador;
import br.com.eugeniosolucoes.excecoes.ActionException;
import br.com.eugeniosolucoes.modelo.Remetente;
import java.awt.Frame;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author eugenio
 */
public class ConfigEnvioDeBoletoForm extends BaseDialog {

    private Remetente obj;

    /**
     * Creates new dialog ConfigEnvioDeBoletoForm
     *
     * @param owner
     * @param title
     * @param modal
     */
    public ConfigEnvioDeBoletoForm( Frame owner, String title, boolean modal ) {
        super( owner, title, modal );
        initComponents();
        obj = new Remetente();
        retornarRemetente();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabEmail = new javax.swing.JTabbedPane();
        painelRemetente = new javax.swing.JPanel();
        lblRemetente = new javax.swing.JLabel();
        txtRemetente = new javax.swing.JTextField();
        lblEmailRemetente = new javax.swing.JLabel();
        txtEmailRemetente = new javax.swing.JTextField();
        lblServidorRemetente = new javax.swing.JLabel();
        txtServidorRemetente = new javax.swing.JTextField();
        lblUsuario = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        lblSenha = new javax.swing.JLabel();
        txtSenha = new javax.swing.JPasswordField();
        jPanel1 = new javax.swing.JPanel();
        btnSalvarDados = new javax.swing.JButton();
        painelEmail = new javax.swing.JPanel();
        txtAssunto = new javax.swing.JTextField();
        lblRemetente1 = new javax.swing.JLabel();
        lblMensagem = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnSalvarEmail = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMensagem = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Configuração de Envio de Boletos");

        lblRemetente.setText("Nome:");

        lblEmailRemetente.setText("Email:");

        lblServidorRemetente.setText("Servidor:");

        lblUsuario.setText("Usuário:");

        lblSenha.setText("Senha:");

        btnSalvarDados.setText("Salvar");
        btnSalvarDados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarDadosActionPerformed(evt);
            }
        });
        jPanel1.add(btnSalvarDados);

        javax.swing.GroupLayout painelRemetenteLayout = new javax.swing.GroupLayout(painelRemetente);
        painelRemetente.setLayout(painelRemetenteLayout);
        painelRemetenteLayout.setHorizontalGroup(
            painelRemetenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelRemetenteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelRemetenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelRemetenteLayout.createSequentialGroup()
                        .addGroup(painelRemetenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblServidorRemetente)
                            .addComponent(lblUsuario)
                            .addComponent(lblEmailRemetente)
                            .addComponent(lblRemetente)
                            .addComponent(lblSenha, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(painelRemetenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelRemetenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtServidorRemetente, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtEmailRemetente, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtRemetente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE))
                            .addGroup(painelRemetenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtUsuario, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtSenha, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE))))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE))
                .addContainerGap())
        );
        painelRemetenteLayout.setVerticalGroup(
            painelRemetenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelRemetenteLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(painelRemetenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRemetente)
                    .addComponent(txtRemetente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelRemetenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmailRemetente)
                    .addComponent(txtEmailRemetente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelRemetenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblServidorRemetente)
                    .addComponent(txtServidorRemetente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelRemetenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUsuario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelRemetenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSenha))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tabEmail.addTab("Dados do Remetente", painelRemetente);

        lblRemetente1.setText("Assunto:");

        lblMensagem.setText("Mensagem:");

        btnSalvarEmail.setText("Salvar");
        btnSalvarEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarEmailActionPerformed(evt);
            }
        });
        jPanel4.add(btnSalvarEmail);

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        txtMensagem.setColumns(20);
        txtMensagem.setRows(5);
        jScrollPane1.setViewportView(txtMensagem);

        javax.swing.GroupLayout painelEmailLayout = new javax.swing.GroupLayout(painelEmail);
        painelEmail.setLayout(painelEmailLayout);
        painelEmailLayout.setHorizontalGroup(
            painelEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelEmailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE)
                    .addGroup(painelEmailLayout.createSequentialGroup()
                        .addGroup(painelEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMensagem)
                            .addComponent(lblRemetente1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(painelEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
                            .addComponent(txtAssunto, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE))))
                .addContainerGap())
        );
        painelEmailLayout.setVerticalGroup(
            painelEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelEmailLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(painelEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAssunto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRemetente1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMensagem)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tabEmail.addTab("Dados do Email", painelEmail);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(565, 319));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void retornarRemetente() {
        Controlador control = new Controlador();
        try {
            control.processar( "CMD_RETORNAR_REMETENTE", obj );
            obj = (Remetente) control.getParametro( 0 );
            txtRemetente.setText( obj.getNome() );
            txtEmailRemetente.setText( obj.getEmail().getEndereco() );
            txtServidorRemetente.setText( obj.getServidor() );
            txtUsuario.setText( obj.getUsuario() );
            txtSenha.setText( obj.getSenha() );
            txtAssunto.setText( obj.getEmail().getAssunto() );
            txtMensagem.setText( obj.getEmail().getMensagem() );
        } catch ( Exception ex ) {
            //Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void btnSalvarDadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarDadosActionPerformed

        obj = new Remetente( txtRemetente.getText() );
        obj.getEmail().setEndereco( txtEmailRemetente.getText() );
        obj.setServidor( txtServidorRemetente.getText() );
        obj.setUsuario( txtUsuario.getText() );
        obj.setSenha( new String( txtSenha.getPassword() ) );
        obj.getEmail().setAssunto( txtAssunto.getText() );
        obj.getEmail().setMensagem( txtMensagem.getText() );

        try {

            new Controlador().processar( "CMD_SALVAR_REMETENTE", obj );

        } catch ( ActionException ex ) {

            Logger.getLogger( ConfigEnvioDeBoletoForm.class.getName() ).log( Level.SEVERE, null, ex );
            JOptionPane.showMessageDialog( this, ex.getMessage() );

        }
    }//GEN-LAST:event_btnSalvarDadosActionPerformed

    private void btnSalvarEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarEmailActionPerformed
        this.btnSalvarDadosActionPerformed( evt );
    }//GEN-LAST:event_btnSalvarEmailActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSalvarDados;
    private javax.swing.JButton btnSalvarEmail;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEmailRemetente;
    private javax.swing.JLabel lblMensagem;
    private javax.swing.JLabel lblRemetente;
    private javax.swing.JLabel lblRemetente1;
    private javax.swing.JLabel lblSenha;
    private javax.swing.JLabel lblServidorRemetente;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPanel painelEmail;
    private javax.swing.JPanel painelRemetente;
    private javax.swing.JTabbedPane tabEmail;
    private javax.swing.JTextField txtAssunto;
    private javax.swing.JTextField txtEmailRemetente;
    private javax.swing.JTextArea txtMensagem;
    private javax.swing.JTextField txtRemetente;
    private javax.swing.JPasswordField txtSenha;
    private javax.swing.JTextField txtServidorRemetente;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
