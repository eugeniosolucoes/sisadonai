/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.view.forms;

import br.com.eugeniosolucoes.controle.Controlador;
import br.com.eugeniosolucoes.excecoes.ActionException;
import br.com.eugeniosolucoes.service.BoletoService;
import br.com.eugeniosolucoes.service.impl.BoletoServiceImpl;
import br.com.eugeniosolucoes.util.MyStrings;
import br.com.eugeniosolucoes.view.model.DadosBoletoFiltroModel;
import br.com.eugeniosolucoes.view.model.DadosBoletoModel;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author eugenio
 */
public class BoletoForm extends BaseDialog {
    
    static final Logger LOG = Logger.getLogger( BoletoForm.class.getName() );
    
    private final BoletoService service = new BoletoServiceImpl();
    
    private DadosBoletoFiltroModel boletoFiltroModel;
    
    private List<DadosBoletoModel> boletoModel;

    /**
     * Creates new form View1
     *
     * @param owner
     * @param title
     * @param modal
     */
    public BoletoForm( Frame owner, String title, boolean modal ) {
        super( owner, title, modal );
        carregarFiltros();
        initComponents();
        mostrarAjuda( plConteudo );
        jpbEnvio.setVisible( false );
        listarBoletos();
        adicionarListeners();
    }
    
    private void carregarFiltros() {
        boletoFiltroModel = service.carregarFiltros();
    }
    
    private void adicionarListeners() {
        tblDados.getSelectionModel().addListSelectionListener( new ListSelectionListener() {
            @Override
            public void valueChanged( ListSelectionEvent e ) {
                lblTotalSelecionados.setText( String.format( "Selecionados: %d", tblDados.getSelectedRowCount() ) );
            }
        } );
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        plConteudo = new javax.swing.JPanel();
        plControles = new javax.swing.JPanel();
        txtMatriculaAluno = new javax.swing.JTextField();
        cmbTurma = new javax.swing.JComboBox();
        btnPesquisar = new javax.swing.JButton();
        lblTurma = new javax.swing.JLabel();
        lblMatriculoAno = new javax.swing.JLabel();
        btnEnviarBoletos = new javax.swing.JButton();
        chkTodos = new javax.swing.JCheckBox();
        btnVisualizarBoletos = new javax.swing.JButton();
        cmbAno = new javax.swing.JComboBox<>();
        lblAno = new javax.swing.JLabel();
        lblMes = new javax.swing.JLabel();
        cmbMes = new javax.swing.JComboBox<>();
        jpbEnvio = new javax.swing.JProgressBar();
        plDados = new javax.swing.JPanel();
        splDados = new javax.swing.JScrollPane();
        tblDados = new javax.swing.JTable();
        plStatus = new javax.swing.JPanel();
        lblTotal = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Envio de Boletos");
        setType(java.awt.Window.Type.POPUP);

        plConteudo.setBackground(java.awt.SystemColor.control);

        plControles.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtMatriculaAluno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMatriculaAlunoKeyPressed(evt);
            }
        });

        cmbTurma.setModel(new javax.swing.DefaultComboBoxModel(getTurmas()));
        cmbTurma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTurmaActionPerformed(evt);
            }
        });

        btnPesquisar.setText("Pesquisar");
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        lblTurma.setText("Turma:");

        lblMatriculoAno.setText("Matrícula/Aluno:");

        btnEnviarBoletos.setText("Enviar Boletos");
        btnEnviarBoletos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarBoletosActionPerformed(evt);
            }
        });

        chkTodos.setText("Selecionar Todos");
        chkTodos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkTodos.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        chkTodos.setMargin(new java.awt.Insets(2, 0, 2, 2));
        chkTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkTodosActionPerformed(evt);
            }
        });

        btnVisualizarBoletos.setText("Visualizar Boletos");
        btnVisualizarBoletos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVisualizarBoletosActionPerformed(evt);
            }
        });

        cmbAno.setModel(new javax.swing.DefaultComboBoxModel<>(getAnos()));
        cmbAno.setSelectedItem(boletoFiltroModel.getAno());
        cmbAno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAnoActionPerformed(evt);
            }
        });

        lblAno.setText("Ano:");

        lblMes.setText("Mês:");

        cmbMes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro" }));
        cmbMes.setSelectedItem(boletoFiltroModel.getMes());
        cmbMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMesActionPerformed(evt);
            }
        });

        jpbEnvio.setIndeterminate(true);
        jpbEnvio.setOpaque(true);
        jpbEnvio.setString("Processando envio...");
        jpbEnvio.setStringPainted(true);

        javax.swing.GroupLayout plControlesLayout = new javax.swing.GroupLayout(plControles);
        plControles.setLayout(plControlesLayout);
        plControlesLayout.setHorizontalGroup(
            plControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plControlesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(plControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(plControlesLayout.createSequentialGroup()
                        .addComponent(lblAno)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblMes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTurma)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbTurma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblMatriculoAno)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMatriculaAluno, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPesquisar))
                    .addGroup(plControlesLayout.createSequentialGroup()
                        .addComponent(chkTodos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnVisualizarBoletos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEnviarBoletos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpbEnvio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(124, Short.MAX_VALUE))
        );
        plControlesLayout.setVerticalGroup(
            plControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, plControlesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(plControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, plControlesLayout.createSequentialGroup()
                        .addGroup(plControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblAno)
                            .addComponent(cmbAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14))
                    .addGroup(plControlesLayout.createSequentialGroup()
                        .addGroup(plControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(plControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cmbTurma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblTurma)
                                .addComponent(lblMatriculoAno)
                                .addComponent(lblMes)
                                .addComponent(cmbMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, plControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtMatriculaAluno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnPesquisar)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(plControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkTodos)
                    .addComponent(btnVisualizarBoletos)
                    .addComponent(btnEnviarBoletos)
                    .addComponent(jpbEnvio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        plDados.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblDados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"00000101", "Fulano Nascimento", "Informática", "300.00", "05/11/2015"},
                {"00000102", "Beltrano da Silva", "Informática", "0.00", "05/11/2015"},
                {"00000103", "Ciclano de Sousa", "Informática", "300.00", "05/11/2015"}
            },
            new String [] {
                "Matricula", "Aluno", "Turma", "Valor", "Vencimento"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDados.setIntercellSpacing(new java.awt.Dimension(3, 3));
        tblDados.setRowHeight(22);
        tblDados.getTableHeader().setReorderingAllowed(false);
        splDados.setViewportView(tblDados);

        javax.swing.GroupLayout plDadosLayout = new javax.swing.GroupLayout(plDados);
        plDados.setLayout(plDadosLayout);
        plDadosLayout.setHorizontalGroup(
            plDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(splDados)
                .addContainerGap())
        );
        plDadosLayout.setVerticalGroup(
            plDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(splDados, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
                .addContainerGap())
        );

        plStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTotal.setText("Total: 0");

        lblTotalSelecionados.setText("Selecionados: 0");

        javax.swing.GroupLayout plStatusLayout = new javax.swing.GroupLayout(plStatus);
        plStatus.setLayout(plStatusLayout);
        plStatusLayout.setHorizontalGroup(
            plStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plStatusLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalSelecionados, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        plStatusLayout.setVerticalGroup(
            plStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plStatusLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(plStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotal)
                    .addComponent(lblTotalSelecionados))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout plConteudoLayout = new javax.swing.GroupLayout(plConteudo);
        plConteudo.setLayout(plConteudoLayout);
        plConteudoLayout.setHorizontalGroup(
            plConteudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plConteudoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(plConteudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(plDados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(plControles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(plStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        plConteudoLayout.setVerticalGroup(
            plConteudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plConteudoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(plControles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(plDados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(plStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(plConteudo, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void chkTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkTodosActionPerformed
        if ( chkTodos.isSelected() ) {
            tblDados.selectAll();
        } else {
            tblDados.clearSelection();
        }
    }//GEN-LAST:event_chkTodosActionPerformed

    private void cmbAnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAnoActionPerformed
        boletoFiltroModel.setAno( cmbAno.getSelectedItem().toString() );
        listarBoletos();
    }//GEN-LAST:event_cmbAnoActionPerformed

    private void cmbMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMesActionPerformed
        boletoFiltroModel.setMes( cmbMes.getSelectedItem().toString() );
        listarBoletos();
    }//GEN-LAST:event_cmbMesActionPerformed

    private void cmbTurmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTurmaActionPerformed
        boletoFiltroModel.setTurma( cmbTurma.getSelectedItem().toString() );
        listarBoletos();
    }//GEN-LAST:event_cmbTurmaActionPerformed

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        boletoFiltroModel.setMatriculaAluno( txtMatriculaAluno.getText() );
        listarBoletos();
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void txtMatriculaAlunoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMatriculaAlunoKeyPressed
        if ( evt.getKeyCode() == KeyEvent.VK_ENTER ) {
            btnPesquisarActionPerformed( null );
        }
    }//GEN-LAST:event_txtMatriculaAlunoKeyPressed

    private void btnVisualizarBoletosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVisualizarBoletosActionPerformed
        List<DadosBoletoModel> selecionados = getSelecionados();
        try {
            JasperPrint jasperPrint = service.visualizarBoletos( selecionados );
            if ( jasperPrint != null ) {
                JDialog viewer = new JDialog( new javax.swing.JFrame(), "Visualização dos Boletos", true );
                viewer.setSize( 800, 600 );
                viewer.setLocationRelativeTo( null );
                JasperViewer jrViewer = new JasperViewer( jasperPrint, true );
                viewer.getContentPane().add( jrViewer.getContentPane() );
                viewer.setVisible( true );
            }
        } catch ( Exception e ) {
            JOptionPane.showMessageDialog( null, e.getMessage() );
        }
    }//GEN-LAST:event_btnVisualizarBoletosActionPerformed
    
    private List<DadosBoletoModel> getSelecionados() {
        List<DadosBoletoModel> selecionados = new ArrayList<>();
        for ( int i : tblDados.getSelectedRows() ) {
            selecionados.add( boletoModel.get( i ) );
        }
        return selecionados;
    }

    private void btnEnviarBoletosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarBoletosActionPerformed
        final List<DadosBoletoModel> selecionados = getSelecionados();
        new Thread( new Runnable() {
            @Override
            public void run() {
                Controlador control = new Controlador();
                try {
                    control.processar( "CMD_ENVIAR_EMAIL", selecionados, jpbEnvio, btnEnviarBoletos );
                } catch ( ActionException ex ) {
                    LOG.log( Level.SEVERE, ex.getMessage(), ex );
                    MyStrings.exibeMensagem( ex.getMessage() );
                }
            }
        } ).start();
    }//GEN-LAST:event_btnEnviarBoletosActionPerformed
    
    private String[] getAnos() {
        return boletoFiltroModel.getAnos().toArray( new String[boletoFiltroModel.getAnos().size()] );
    }
    
    private String[] getTurmas() {
        return boletoFiltroModel.getTurmas().toArray( new String[boletoFiltroModel.getTurmas().size()] );
    }
    
    private void listarBoletos() {
        try {
            boletoModel = service.listarBoletos( boletoFiltroModel );
            Object[][] dados = getDados();
            tblDados.setModel( new javax.swing.table.DefaultTableModel( dados,
                    new String[]{"Matrícula", "Aluno", "Turma", "Nosso Número", "Valor", "Vencimento"} ) {
                boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, false
                };
                
                @Override
                public boolean isCellEditable( int rowIndex, int columnIndex ) {
                    return canEdit[columnIndex];
                }
            } );
            lblTotal.setText( String.format( "Total: %d", dados.length ) );
        } catch ( Exception e ) {
            LOG.log( Level.SEVERE, e.getMessage(), e );
        }
        configurarTabela();
        try {
            service.validarListaDeBoletos( boletoModel, boletoFiltroModel );
        } catch ( Exception e ) {
            new PopupForm( null, true, BaseForm.SYSTEM_TITLE + " - Observações", e.getMessage(), 900, 300 ).setVisible( true );
        }
    }
    
    private Object[][] getDados() {
        Object[][] dados = new Object[boletoModel.size()][];
        int i = 0;
        for ( DadosBoletoModel model : boletoModel ) {
            dados[i] = new String[]{
                model.getMatricula(),
                model.getAluno(),
                model.getTurma(),
                model.getNossoNumero(),
                String.format( "R$ %.2f", model.getValor() ),
                BaseForm.DATE_FORMAT.format( model.getVencimento() )};
            i++;
        }
        return dados;
    }
    
    private void configurarTabela() {
        tblDados.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
        
        DefaultTableCellRenderer cellRight = new DefaultTableCellRenderer();
        cellRight.setHorizontalAlignment( SwingConstants.RIGHT );
        DefaultTableCellRenderer cellCenter = new DefaultTableCellRenderer();
        cellCenter.setHorizontalAlignment( SwingConstants.CENTER );
        
        tblDados.getColumnModel().getColumn( 0 ).setCellRenderer( cellCenter );
        tblDados.getColumnModel().getColumn( 2 ).setCellRenderer( cellCenter );
        tblDados.getColumnModel().getColumn( 3 ).setCellRenderer( cellCenter );
        tblDados.getColumnModel().getColumn( 4 ).setCellRenderer( cellRight );
        tblDados.getColumnModel().getColumn( 5 ).setCellRenderer( cellCenter );
        
        tblDados.getColumnModel().getColumn( 0 ).setPreferredWidth( 60 );
        tblDados.getColumnModel().getColumn( 1 ).setPreferredWidth( 400 );
        tblDados.getColumnModel().getColumn( 2 ).setPreferredWidth( 110 );
        tblDados.getColumnModel().getColumn( 3 ).setPreferredWidth( 100 );
        tblDados.getColumnModel().getColumn( 5 ).setPreferredWidth( 80 );
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnviarBoletos;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnVisualizarBoletos;
    private javax.swing.JCheckBox chkTodos;
    private javax.swing.JComboBox<String> cmbAno;
    private javax.swing.JComboBox<String> cmbMes;
    private javax.swing.JComboBox cmbTurma;
    private javax.swing.JProgressBar jpbEnvio;
    private javax.swing.JLabel lblAno;
    private javax.swing.JLabel lblMatriculoAno;
    private javax.swing.JLabel lblMes;
    private javax.swing.JLabel lblTotal;
    private final javax.swing.JLabel lblTotalSelecionados = new javax.swing.JLabel();
    private javax.swing.JLabel lblTurma;
    private javax.swing.JPanel plConteudo;
    private javax.swing.JPanel plControles;
    private javax.swing.JPanel plDados;
    private javax.swing.JPanel plStatus;
    private javax.swing.JScrollPane splDados;
    private javax.swing.JTable tblDados;
    private javax.swing.JTextField txtMatriculaAluno;
    // End of variables declaration//GEN-END:variables

}
