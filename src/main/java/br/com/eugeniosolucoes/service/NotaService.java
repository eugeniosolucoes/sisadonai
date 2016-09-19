/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.service;

import br.com.eugeniosolucoes.view.model.NotaCariocaModel;
import java.util.Date;
import java.util.List;

/**
 *
 * @author eugenio
 */
public interface NotaService {

    void enviarNsfe() throws Exception;

    void enviarNsfe( Date data ) throws Exception;

    public List<NotaCariocaModel> listarRpsEnviados( Date data );
}
