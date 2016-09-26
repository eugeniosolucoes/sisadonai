/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.modelo;

import java.io.Serializable;

/**
 *
 * @author eugenio
 */
public class RpsConfig implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private String discriminacaoDoServico;

    public String getDiscriminacaoDoServico() {
        return discriminacaoDoServico;
    }

    public void setDiscriminacaoDoServico( String discriminacaoDoServico ) {
        this.discriminacaoDoServico = discriminacaoDoServico;
    }
    
    
}
