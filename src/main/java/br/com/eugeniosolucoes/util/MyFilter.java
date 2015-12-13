/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.util;

import java.io.File;

/**
 *
 * @author alexandre
 */
public class MyFilter extends javax.swing.filechooser.FileFilter {

    private String extensao;

    public MyFilter( String extensao ) {
        this.extensao = extensao;
    }

    public boolean accept( File f ) {
        boolean accept = f.isDirectory();

        if ( !accept ) {
            String suffix = getSuffix( f );

            if ( suffix != null ) {
                accept = suffix.equals( extensao );
            }
        }
        return accept;
    }

    public String getDescription() {
        return "*." + extensao;
    }

    private String getSuffix( File f ) {
        String s = f.getPath(), suffix = null;
        int i = s.lastIndexOf( '.' );

        if ( i > 0 && i < s.length() - 1 ) {
            suffix = s.substring( i + 1 ).toLowerCase();
        }

        return suffix;
    }
}
