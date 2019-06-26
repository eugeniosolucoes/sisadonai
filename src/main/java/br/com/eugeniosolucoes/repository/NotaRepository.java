/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.repository;

import br.com.eugeniosolucoes.view.model.NotaCariocaModel;
import java.util.Date;
import java.util.List;

/**
 *
 * @author eugenio
 */
public interface NotaRepository {
    
    void registrarEnvio(NotaCariocaModel notaCariocaModel);

    public int retornarProximoNumeroLote();

    public int retornarProximoNumeroRps();

    List<NotaCariocaModel> listarRspEnviados( Date data );

    boolean retornarFaltaProcessar();

    String retornarUltimoProtocolo();

    void atualizarLoteProcessadoComSucesso( String protocolo );
    
    void removerLoteProcessadoComErro( String protocolo );
    
    Date retornarUltimaDataEnvio();

    void registrarRpsAvulso( NotaCariocaModel notaCariocaModel );

    boolean verificarExisteNumeroRps( int numeroRps );

    boolean verificarExisteNumeroBoleto( String numeroBoleto );

    boolean excluirRpsAvulso( String numeroBoleto );
    
    void registrarRpsCancelado( NotaCariocaModel notaCariocaModel );    

}
