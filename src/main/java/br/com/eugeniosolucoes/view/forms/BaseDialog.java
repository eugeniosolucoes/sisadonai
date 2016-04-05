/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.view.forms;

import java.awt.Desktop;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;

/**
 *
 * @author eugenio
 */
public class BaseDialog extends JDialog implements IAjuda {

    public BaseDialog() {
    }

    public BaseDialog( Frame owner, String title, boolean modal ) {
        super( owner, title, modal );
        mostrarAjuda( rootPane );
    }

    @Override
    public final void mostrarAjuda( JComponent panel ) {
        InputMap im = panel.getInputMap( JComponent.WHEN_IN_FOCUSED_WINDOW );
        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_F1, 0 ), "openDialog" );
        ActionMap am = panel.getActionMap();
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
