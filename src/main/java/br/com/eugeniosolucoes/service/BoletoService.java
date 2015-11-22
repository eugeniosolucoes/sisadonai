/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.service;

import br.com.eugeniosolucoes.view.model.BoletoFiltroModel;
import br.com.eugeniosolucoes.view.model.BoletoModel;
import java.util.List;

/**
 *
 * @author eugenio
 */
public interface BoletoService {
    
    byte[] gerarBoleto( BoletoModel boletoModel );
    
    BoletoFiltroModel getBoletoFiltroModel();

    List<BoletoModel> getBoletosModel( BoletoFiltroModel boletoFiltroModel );
    
}
