/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.controle;

import br.com.eugeniosolucoes.excecoes.ActionException;
import br.com.eugeniosolucoes.modelo.Remetente;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author alexandre
 */
public class ActionSalvarRemetente extends ActionComand implements IAction {

    public static String DATA_FILE = "sendmail.dat";

    public ActionSalvarRemetente( String comando, Object... parametros ) {
        super( comando, parametros );
    }

    @Override
    public void execute() {
        Remetente obj = (Remetente) this.getParametro( 0 );
        try {
            obj.setSenha( obj.getSenha() );
            salvarObjeto( obj, DATA_FILE );
            JOptionPane.showMessageDialog( null, "Os dados foram salvos com sucesso!" );
        } catch ( ActionException ex ) {
            Logger.getLogger( ActionSalvarRemetente.class.getName() ).log( Level.SEVERE, null, ex );
        }

    }

    public void salvarObjeto( Object obj, String file ) throws ActionException {
        try {
            byte[] bytesObjeto = serializarObjetoParaByte( obj );
            FileOutputStream arquivoSaida = new FileOutputStream( new File( file ) );
            InputStream is = new ByteArrayInputStream( bytesObjeto );
            byte[] b = new byte[2048];
            int i = is.read( b );
            while (i != -1) {
                arquivoSaida.write( b, 0, i );
                i = is.read( b );
            }
            arquivoSaida.flush();
            arquivoSaida.close();
            is.close();
        } catch ( ActionException e ) {
            throw new ActionException( "Falha ao gravar arquivo: " + e.getMessage() );
        } catch ( Exception e ) {
            JOptionPane.showMessageDialog( null, e.getMessage() );
        }
    }

    public byte[] serializarObjetoParaByte( Object obj ) throws ActionException {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        byte[] byteRetorno = null;
        try {
            ObjectOutputStream objetoStream = new ObjectOutputStream( byteArray );
            objetoStream.writeObject( obj );
            objetoStream.flush();
            byteRetorno = byteArray.toByteArray();
        } catch ( Exception e ) {
            JOptionPane.showMessageDialog( null, "Falha ao serializar objeto: " + e.getMessage() );
        }
        return byteRetorno;
    }

}
