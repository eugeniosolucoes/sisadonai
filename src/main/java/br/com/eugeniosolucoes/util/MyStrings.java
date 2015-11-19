/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.util;

/**
 *
 * @author cpo-2021
 */
public class MyStrings {
    
    /**
     * Verifica se a string é nula ou vazia
     *
     * @param string string a ser verificada
     * @return true caso a string seja nula ou vazia
     */
    public static boolean isNullOrEmpty( java.lang.String string ) {
        return (string == null || ( "" ).equals( string.trim() ));
    }    
    
}
