/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.controle;

import br.com.eugeniosolucoes.excecoes.ActionException;
import br.com.eugeniosolucoes.modelo.Remetente;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eugenio
 */
public class ActionRetornarRemetente extends ActionComand implements IAction {

    public ActionRetornarRemetente( String comando, Object... parametros ) {
        super( comando, parametros );
    }

    @Override
    public void execute() {
        Remetente r = null;
        byte[] cleanPwd = null;
        String strCleanPwd = null;

        try {
            r = (Remetente) serializarByteParaObjeto( new FileInputStream( ActionSalvarRemetente.DATA_FILE ) );
        } catch ( FileNotFoundException | ActionException ex ) {
            Logger.getLogger( ActionRetornarRemetente.class.getName() ).log( Level.SEVERE, null, ex );
        }
        this.setParametro( r, 0 );
    }

    public Object serializarByteParaObjeto( InputStream bytes ) throws ActionException {
        ByteArrayOutputStream bytesGrava = new ByteArrayOutputStream();
        Object objeto = null;
        byte[] b = new byte[8];
        try {
            int i = bytes.read( b );
            while (i != -1) {
                bytesGrava.write( b, 0, i );
                i = bytes.read( b );
            }
            ObjectInputStream objetoLeitura = new ObjectInputStream( new ByteArrayInputStream( bytesGrava.toByteArray() ) );
            objeto = objetoLeitura.readObject();
        } catch ( IOException | ClassNotFoundException e ) {
            throw new ActionException( "Houve um erro ao transformar byte para objeto: " + e.toString() );
        }
        return objeto;
    }
}
