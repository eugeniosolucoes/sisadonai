/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.app;

import br.com.eugeniosolucoes.view.forms.MainForm;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eugenio
 */
public class Main {

    private static final Logger LOG = Logger.getLogger( Main.class.getName() );

    private static final Properties CONFIG = new Properties();

    static {
        try {
            CONFIG.load( new FileInputStream( "sisadonai.properties" ) );
        } catch ( IOException ex ) {
            LOG.log( Level.SEVERE, ex.getMessage(), ex );
            System.exit( 1 );
        }
    }

    public static void main( String[] args ) {
        try {
            java.awt.EventQueue.invokeLater( new Runnable() {
                @Override
                public void run() {
                    new MainForm().setVisible( true );
                }
            } );
        } catch ( Exception e ) {
            LOG.log( Level.SEVERE, e.getMessage(), e );
        } finally {
        }
    }

    public static boolean isTestMode() {
        try {
            return Integer.parseInt( CONFIG.getProperty( "modo.teste.habilitado" ) ) == 1;
        } catch ( Exception e ) {
            return false;
        }
    }
    
    public static String getEmailTest() {
        try {
            return CONFIG.getProperty( "email.teste" );
        } catch ( Exception e ) {
            return null;
        }
    }    

    public static String getDBTest() {
        try {
            return CONFIG.getProperty( "banco-teste" );
        } catch ( Exception e ) {
            return null;
        }
    }    
}
