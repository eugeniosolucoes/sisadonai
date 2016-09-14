/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.repository;

import br.com.eugeniosolucoes.view.model.DadosBoletoFiltroModel;
import br.com.eugeniosolucoes.view.model.DadosBoletoModel;
import br.com.eugeniosolucoes.view.model.DadosBoletoPagoModel;
import java.util.List;

/**
 *
 * @author eugenio
 */
public interface BoletoRepository {

    List<String> listarAnos();

    List<String> listarTurmas();

    List<DadosBoletoModel> listarBoletos( DadosBoletoFiltroModel boletoFiltroDTO );

    List<DadosBoletoModel> listarAlunosPorTurma( DadosBoletoFiltroModel boletoFiltroDTO );

    void processarBaixaDeBoleto( DadosBoletoPagoModel boletoPagoModels );

    DadosBoletoModel retornarBoletoPago( DadosBoletoPagoModel boletoPagoModels );

    List<DadosBoletoModel> retornarBoletosPagos( int ano, int mes );

}
