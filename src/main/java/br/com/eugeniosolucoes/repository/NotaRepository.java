/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.repository;

import br.com.eugeniosolucoes.view.model.NotaCariocaModel;

/**
 *
 * @author eugenio
 */
public interface NotaRepository {
    
    void registrarEnvio(NotaCariocaModel notaCariocaModel);

    public int retornarProximoNumeroLote();

    public int retornarProximoNumeroRps();

}
