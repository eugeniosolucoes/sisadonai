/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.view.forms;

import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 *
 * @author eugenio
 */
public class BaseForm extends JFrame {

    
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
}
