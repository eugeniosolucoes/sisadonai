/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.service;

import br.com.eugeniosolucoes.excecoes.RestamBoletosPagosException;
import br.com.eugeniosolucoes.view.model.NotaCariocaModel;
import java.util.Date;
import java.util.List;

/**
 *
 * @author eugenio
 */
public interface NotaService {

    String RPS_AVULSO = "RPS_AVULSO";

    void enviarNsfe( Date data ) throws RestamBoletosPagosException;

    public List<NotaCariocaModel> listarRpsEnviados( Date data );

    Date validarUltimoEnvio() throws IllegalStateException;

    void registrarRpsAvulso( NotaCariocaModel model );
}
