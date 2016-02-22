/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.excecoes;

/**
 *
 * @author eugenio
 */
public class CepException extends Exception {

    public CepException() {
    }

    public CepException( String message ) {
        super( message );
    }

    public CepException( String message, Throwable cause ) {
        super( message, cause );
    }

    public CepException( Throwable cause ) {
        super( cause );
    }

    public CepException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
        super( message, cause, enableSuppression, writableStackTrace );
    }
    
    
    
}
