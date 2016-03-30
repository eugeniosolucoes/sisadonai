/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.service;

import br.com.eugeniosolucoes.view.model.DadosBoletoPagoModel;
import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 *
 * @author eugenio
 */
public interface ArquivoDeRetornoService {

    /**
     * Ler arquivo de Retorno e criar uma lista de boletos pagos.
     * 
     * @param file Arquivo de Retorno.
     * @return Lista de boletos pagos.
     */
    List<DadosBoletoPagoModel> lerArquivoDeRetorno( File file );

    /**
     * Ler arquivo de Retorno e criar uma lista de boletos pagos.
     * 
     * @param file Arquivo de Retorno.
     * @return Lista de boletos pagos.
     */
    List<DadosBoletoPagoModel> lerArquivoDeRetorno( InputStream file );
    /**
     * Processar a baixa de um boleto pago no sistema.
     * 
     * @param boletoPagoModels boletos pago.
     */
    void processarBaixaDeBoleto( DadosBoletoPagoModel  boletoPagoModels );
    
    /**
     * Processar a lista de boletos pagos efetuando a baixa no sistema.
     * 
     * @param boletoPagoModels Lista de boletos pagos.
     */
    void processarBaixaDeBoleto( List<DadosBoletoPagoModel>  boletoPagoModels );

}
