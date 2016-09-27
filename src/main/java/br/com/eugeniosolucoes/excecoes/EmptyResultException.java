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
public class EmptyResultException extends Exception {

    public EmptyResultException() {
    }

    public EmptyResultException( String message ) {
        super( message );
    }

    public EmptyResultException( String message, Throwable cause ) {
        super( message, cause );
    }

    public EmptyResultException( Throwable cause ) {
        super( cause );
    }
    
    
}
