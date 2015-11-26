/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.service;

import br.com.eugeniosolucoes.view.model.DadosBoletoFiltroModel;
import br.com.eugeniosolucoes.view.model.DadosBoletoModel;
import java.util.List;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author eugenio
 */
public interface BoletoService {

    JasperPrint visualizarBoletos(List<DadosBoletoModel> lista);

    DadosBoletoFiltroModel carregarFiltros();

    List<DadosBoletoModel> listarBoletos(DadosBoletoFiltroModel boletoFiltroModel);

}
