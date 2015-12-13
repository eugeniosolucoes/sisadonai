/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author eugenio
 */
public final class MyStrings {

    public static boolean isNullOrEmpty( java.lang.String string ) {
        return ( string == null || string.trim().equals( "" ) );
    }

    public static java.lang.String cleanMessage( java.lang.String msg ) {
        java.lang.String[] lixo = {
            "\n", "\r", "\"", "'"
        };
        for ( int i = 0; i < lixo.length; i++ ) {
            msg = msg.replaceAll( lixo[i], "" );
        }
        return msg;
    }

    public static boolean validaParametros( java.lang.String... params ) {
        for ( java.lang.String p : params ) {
            if ( MyStrings.isNullOrEmpty( p ) ) {
                return false;
            }
        }
        return true;
    }

    public static String padLeft( Integer numero ) {
        return String.format( "%03d", numero );
    }

    public static String padLeft( int part, Integer numero ) {
        String fmt = "%0" + part + "d";
        return String.format( fmt, numero );
    }

    public static void exibeMensagem( String msg ) {
        JOptionPane.showMessageDialog( null, msg );
    }

    public static boolean validarEmail( String email ) {
        //Set the email pattern string
        Pattern p = Pattern.compile( ".+@.+\\.[a-z]+" );

        //Match the given string with the pattern
        Matcher m = p.matcher( email );

        //check whether match is found
        boolean matchFound = m.matches();

        return matchFound;
    }
}
