/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.view.forms;

import br.com.eugeniosolucoes.service.NotaService;
import br.com.eugeniosolucoes.service.impl.NotaServiceImpl;
import br.com.eugeniosolucoes.util.RelatorioUtils;
import br.com.eugeniosolucoes.view.model.NotaCariocaModel;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import org.joda.time.LocalDate;
import org.slf4j.LoggerFactory;

/**
 *
 * @author eugenio
 */
public class NotaForm extends BaseDialog {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger( NotaForm.class );

    private final NotaService notaService = new NotaServiceImpl();

    private int totalRpsProcessados;

    private int totalRpsNaoProcessados;

    private double valorTotal;

    /**
     * Creates new form View1
     *
     * @param owner
     * @param title
     * @param modal
     */
    public NotaForm( Frame owner, String title, boolean modal ) {
        super( owner, title, modal );
        initComponents();
        jProgressBar1.setVisible( false );
    }

    public NotaForm( Frame owner, String title, boolean modal, int w, int h ) {
        super( owner, title, modal );
        initComponents();
        configCustomSize( w, h );
        jProgressBar1.setVisible( false );
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
        plDados = new javax.swing.JPanel();
        splDados = new javax.swing.JScrollPane();
        tblDados = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblTotalRpsProcessados = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblTotalRpsNaoProcessados = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblValorTotalRpsProcessados = new javax.swing.JLabel();
        tabPanelControl = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jdtPagamento = new com.toedter.calendar.JDateChooser( new java.util.Date() );
        btnEnviarLoteRps = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        jPanel3 = new javax.swing.JPanel();
        btnVerificarUltimoEnvio = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jdtEnvio = new com.toedter.calendar.JDateChooser(new java.util.Date());
        btnListarRpsEnviados = new javax.swing.JButton();
        btnExportarExcel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Enviar Lote RPS");
        setType(java.awt.Window.Type.POPUP);

        plConteudo.setBackground(java.awt.SystemColor.control);
        plConteudo.setPreferredSize(new java.awt.Dimension(800, 600));

        plDados.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblDados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Boleto", "Nome", "Data Pagamento", "Total", "Protocolo", "Processado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDados.setIntercellSpacing(new java.awt.Dimension(3, 3));
        tblDados.setRowHeight(22);
        tblDados.getTableHeader().setReorderingAllowed(false);
        splDados.setViewportView(tblDados);

        jLabel3.setText("Total de RPS processados:");

        jLabel4.setText("Total de RPS não processados:");

        jLabel5.setText("Valor Total de RPS processados:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalRpsProcessados, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalRpsNaoProcessados, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblValorTotalRpsProcessados, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblValorTotalRpsProcessados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblTotalRpsNaoProcessados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblTotalRpsProcessados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        javax.swing.GroupLayout plDadosLayout = new javax.swing.GroupLayout(plDados);
        plDados.setLayout(plDadosLayout);
        plDadosLayout.setHorizontalGroup(
            plDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(plDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(splDados)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        plDadosLayout.setVerticalGroup(
            plDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(splDados, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel1.setText("Data Pagamento:");

        btnEnviarLoteRps.setText("Enviar Lote RPS");
        btnEnviarLoteRps.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarLoteRpsActionPerformed(evt);
            }
        });

        jProgressBar1.setIndeterminate(true);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(5, 5, 5)
                .addComponent(jdtPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(btnEnviarLoteRps)
                .addGap(5, 5, 5)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(324, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jdtPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnEnviarLoteRps)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabPanelControl.addTab("Envio de Lote RPS", jPanel2);

        btnVerificarUltimoEnvio.setText("Verificar Último Envio");
        btnVerificarUltimoEnvio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerificarUltimoEnvioActionPerformed(evt);
            }
        });

        jLabel2.setText("Data Envio:");

        btnListarRpsEnviados.setText("Listar RPS Enviados");
        btnListarRpsEnviados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarRpsEnviadosActionPerformed(evt);
            }
        });

        btnExportarExcel.setText("Exportar para Excel");
        btnExportarExcel.setEnabled(false);
        btnExportarExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarExcelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnVerificarUltimoEnvio)
                .addGap(5, 5, 5)
                .addComponent(jLabel2)
                .addGap(5, 5, 5)
                .addComponent(jdtEnvio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(btnListarRpsEnviados)
                .addGap(5, 5, 5)
                .addComponent(btnExportarExcel)
                .addContainerGap(215, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnVerificarUltimoEnvio)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel2))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jdtEnvio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnListarRpsEnviados)
                    .addComponent(btnExportarExcel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabPanelControl.addTab("Listar RPS Enviados", jPanel3);

        javax.swing.GroupLayout plConteudoLayout = new javax.swing.GroupLayout(plConteudo);
        plConteudo.setLayout(plConteudoLayout);
        plConteudoLayout.setHorizontalGroup(
            plConteudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plConteudoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(plConteudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(plDados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tabPanelControl))
                .addContainerGap())
        );
        plConteudoLayout.setVerticalGroup(
            plConteudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plConteudoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabPanelControl, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(plDados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(plConteudo, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnEnviarLoteRpsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarLoteRpsActionPerformed
        new Thread( new Runnable() {
            @Override
            public void run() {
                if ( jdtPagamento.getDate() != null ) {
                    try {
                        Date date = jdtPagamento.getDate();
                        jProgressBar1.setVisible( true );
                        btnEnviarLoteRps.setEnabled( false );
                        btnListarRpsEnviados.setEnabled( false );
                        notaService.enviarNsfe( date );
                        jdtEnvio.setDate( LocalDate.now().toDate() );
                        listarRpsEnviados( jdtEnvio.getDate() );
                    } catch ( Exception ex ) {
                        JOptionPane.showMessageDialog( null, ex.getMessage() );
                    } finally {
                        jProgressBar1.setVisible( false );
                        btnEnviarLoteRps.setEnabled( true );
                        btnListarRpsEnviados.setEnabled( true );
                    }
                }
            }
        } ).start();
    }//GEN-LAST:event_btnEnviarLoteRpsActionPerformed

    private void btnListarRpsEnviadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarRpsEnviadosActionPerformed
        // TODO add your handling code here:
        if ( jdtEnvio.getDate() != null ) {
            listarRpsEnviados( jdtEnvio.getDate() );
        }
    }//GEN-LAST:event_btnListarRpsEnviadosActionPerformed

    private void btnExportarExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarExcelActionPerformed
        // TODO add your handling code here:
        FileOutputStream fs = null;
        BufferedOutputStream bs = null;
        final String relatorio = "relatorio-excel-envio-rps.xls";
        File file = new File( relatorio );
        try {
            List<NotaCariocaModel> dados = notaService.listarRpsEnviados( jdtEnvio.getDate() );
            byte[] arquivoExcel = RelatorioUtils.gerarArquivoExcel( "/templates/relatorio-excel-envio-rps.jasper",
                    dados, new HashMap<String, Object>() );
            fs = new FileOutputStream( file );
            bs = new BufferedOutputStream( fs );
            bs.write( arquivoExcel );
            bs.close();
        } catch ( Exception e ) {
            JOptionPane.showMessageDialog( null, e.getMessage() );
        } finally {
            try {
                if ( bs != null ) {
                    bs.close();
                }
                if ( fs != null ) {
                    fs.close();
                }
            } catch ( IOException ex ) {
                LOG.error( ex.getMessage(), ex );
            }
        }
        if ( file.exists() && file.canRead() ) {
            try {
                Desktop.getDesktop().open( file );
            } catch ( IOException ex ) {
                LOG.error( ex.getMessage(), ex );
            }
        }
    }//GEN-LAST:event_btnExportarExcelActionPerformed

    private void btnVerificarUltimoEnvioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerificarUltimoEnvioActionPerformed
        // TODO add your handling code here:
        Date data = notaService.validarUltimoEnvio();
        JOptionPane.showMessageDialog( null, "Último envio validado com sucesso!" );
        if ( data != null ) {
            jdtEnvio.setDate( data );
            btnListarRpsEnviadosActionPerformed( evt );
        }
    }//GEN-LAST:event_btnVerificarUltimoEnvioActionPerformed

    private void listarRpsEnviados( Date data ) throws HeadlessException {
        try {
            MainForm.setWaitCursor( this );
            List<NotaCariocaModel> dados = notaService.listarRpsEnviados( data );
            tblDados.setModel( new javax.swing.table.DefaultTableModel( getDados( dados ),
                    new String[]{"Boleto", "Nome", "Data Pagamento", "Total", "Protocolo", "Processado"} ) {
                boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, false
                };

                @Override
                public Class<?> getColumnClass( int columnIndex ) {
                    switch ( columnIndex ) {
                        case 5:
                            return Boolean.class;
                        default:
                            return String.class;
                    }
                }

                @Override
                public boolean isCellEditable( int rowIndex, int columnIndex ) {
                    return canEdit[columnIndex];
                }
            } );
            configurarTabela();
            configurarBarraDeStatus();
            btnExportarExcel.setEnabled( tblDados.getModel().getRowCount() > 0 );
            MainForm.setDefaultCursor( this );
        } catch ( Exception ex ) {
            JOptionPane.showMessageDialog( null, ex.getMessage() );
        }
    }

    private void configurarBarraDeStatus() {
        lblTotalRpsProcessados.setText( String.valueOf( totalRpsProcessados ) );
        lblTotalRpsNaoProcessados.setText( String.valueOf( totalRpsNaoProcessados ) );
        lblValorTotalRpsProcessados.setText( String.format( "%.2f", valorTotal ) );
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

        tblDados.getColumnModel().getColumn( 0 ).setPreferredWidth( 70 );
        tblDados.getColumnModel().getColumn( 1 ).setPreferredWidth( 350 );
        tblDados.getColumnModel().getColumn( 2 ).setPreferredWidth( 100 );
        tblDados.getColumnModel().getColumn( 3 ).setPreferredWidth( 80 );
        tblDados.getColumnModel().getColumn( 4 ).setPreferredWidth( 340 );

    }

    private Object[][] getDados( List<NotaCariocaModel> lista ) {
        totalRpsProcessados = 0;
        totalRpsNaoProcessados = 0;
        valorTotal = 0;
        Object[][] dados = new Object[lista.size()][];
        int i = 0;
        for ( NotaCariocaModel model : lista ) {
            if ( model.isProcessado() ) {
                totalRpsProcessados++;
                try {
                    NumberFormat format = NumberFormat.getInstance( new Locale( "pt", "BR" ) );
                    Number number = format.parse( model.getTotal() );
                    valorTotal += number.doubleValue();
                } catch ( Exception e ) {
                    LOG.error( e.getMessage(), e );
                }
            } else {
                totalRpsNaoProcessados++;
            }
            dados[i] = new Object[]{
                model.getNumeroBoleto(),
                model.getNome(),
                model.getDataPagamento(),
                model.getTotal(),
                model.getProtocolo(),
                model.isProcessado()};
            i++;
        }
        return dados;
    }

    private void configCustomSize( int largura, int altura ) throws HeadlessException {
        this.setSize( largura, altura );
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds( ( tela.width - getSize().width ) / 2, ( tela.height - getSize().height ) / 2,
                getSize().width, getSize().height );
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnviarLoteRps;
    private javax.swing.JButton btnExportarExcel;
    private javax.swing.JButton btnListarRpsEnviados;
    private javax.swing.JButton btnVerificarUltimoEnvio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JProgressBar jProgressBar1;
    private com.toedter.calendar.JDateChooser jdtEnvio;
    private com.toedter.calendar.JDateChooser jdtPagamento;
    private javax.swing.JLabel lblTotalRpsNaoProcessados;
    private javax.swing.JLabel lblTotalRpsProcessados;
    private javax.swing.JLabel lblValorTotalRpsProcessados;
    private javax.swing.JPanel plConteudo;
    private javax.swing.JPanel plDados;
    private javax.swing.JScrollPane splDados;
    private javax.swing.JTabbedPane tabPanelControl;
    private javax.swing.JTable tblDados;
    // End of variables declaration//GEN-END:variables

}
