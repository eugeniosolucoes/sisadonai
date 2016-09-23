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
public class RestamBoletosPagosException extends Exception {

    public RestamBoletosPagosException() {
    }

    public RestamBoletosPagosException( String message ) {
        super( message );
    }

    public RestamBoletosPagosException( String message, Throwable cause ) {
        super( message, cause );
    }

    public RestamBoletosPagosException( Throwable cause ) {
        super( cause );
    }



}
