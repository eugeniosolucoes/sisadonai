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
public class TamanhoLinhaException extends Exception {

    public TamanhoLinhaException() {
    }

    public TamanhoLinhaException( String message ) {
        super( message );
    }

    public TamanhoLinhaException( String message, Throwable cause ) {
        super( message, cause );
    }

    public TamanhoLinhaException( Throwable cause ) {
        super( cause );
    }

    public TamanhoLinhaException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
        super( message, cause, enableSuppression, writableStackTrace );
    }

    
}
