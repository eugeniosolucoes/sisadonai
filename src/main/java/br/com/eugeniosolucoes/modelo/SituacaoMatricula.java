/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.modelo;

/**
 *
 * @author eugenio
 */
public enum SituacaoMatricula {
    
    ABERTA( "1" ), BAIXA( "2" );

    private final String codigo;

    private SituacaoMatricula( String codigo ) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

}
