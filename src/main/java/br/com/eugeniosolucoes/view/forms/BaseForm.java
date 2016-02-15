/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.view.forms;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

/**
 *
 * @author eugenio
 */
public class BaseForm extends JFrame implements IAjuda {

    static final String SYSTEM_TITLE = "Sistema ADONAI 1.0";

    static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat( "dd/MM/yyyy", new Locale( "pt", "BR" ) );

    public BaseForm() {
        super();
        setupView();
    }

    final void setupView() {
        setTitle( SYSTEM_TITLE );
        try {
            javax.swing.UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
        } catch ( ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex ) {
            java.util.logging.Logger.getLogger( BoletoForm.class.getName() ).log( java.util.logging.Level.SEVERE, null, ex );
        }
        setIconImage( Toolkit.getDefaultToolkit().getImage( getClass().getResource( "/imagens/adonai.png" ) ) );
    }

    @Override
    public final void mostrarAjuda(JComponent component) {
        InputMap im = component.getInputMap( JComponent.WHEN_IN_FOCUSED_WINDOW );
        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_F1, 0 ), "openDialog" );
        ActionMap am = component.getActionMap();
        am.put( "openDialog", new AbstractAction() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                try {
                    Desktop.getDesktop().open( new File( IAjuda.ARQUIVO_CHM ) );
                } catch ( IOException ex ) {
                    Logger.getLogger( BaseForm.class.getName() ).log( Level.SEVERE, null, ex );
                }
            }
        } );
    }
}
