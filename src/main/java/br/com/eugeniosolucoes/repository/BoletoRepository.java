/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.repository;

import br.com.eugeniosolucoes.view.model.DadosBoletoFiltroModel;
import br.com.eugeniosolucoes.view.model.DadosBoletoModel;
import java.util.List;

/**
 *
 * @author eugenio
 */
public interface BoletoRepository {

    List<String> listarAnos();

    List<String> listarTurmas();

    List<DadosBoletoModel> listarBoletos(DadosBoletoFiltroModel boletoFiltroDTO);

}
