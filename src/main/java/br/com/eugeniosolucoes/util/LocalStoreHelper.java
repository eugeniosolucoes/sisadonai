/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author eugenio
 */
public class LocalStoreHelper {

    public static void save( Object obj, String file ) throws Exception {
        try {
            byte[] bytesObjeto = objectToByteArray( obj );
            InputStream is;
            try ( FileOutputStream arquivoSaida = new FileOutputStream( new File( file ) ) ) {
                is = new ByteArrayInputStream( bytesObjeto );
                byte[] b = new byte[2048];
                int i = is.read( b );
                while (i != -1) {
                    arquivoSaida.write( b, 0, i );
                    i = is.read( b );
                }
                arquivoSaida.flush();
            }
            is.close();
        } catch ( Exception e ) {
            throw new Exception( e );
        }
    }

    public static Object restore( InputStream bytes ) throws Exception {
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
            throw new Exception( e );
        }
        return objeto;
    }

    private static byte[] objectToByteArray( Object obj ) throws IOException {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        ObjectOutputStream objetoStream = new ObjectOutputStream( byteArray );
        objetoStream.writeObject( obj );
        objetoStream.flush();
        return byteArray.toByteArray();
    }

}
