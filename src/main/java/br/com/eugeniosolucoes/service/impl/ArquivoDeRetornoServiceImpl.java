/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.service.impl;

import br.com.eugeniosolucoes.service.ArquivoDeRetornoService;
import br.com.eugeniosolucoes.view.model.DadosBoletoPagoModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eugenio
 */
public class ArquivoDeRetornoServiceImpl implements ArquivoDeRetornoService {

    private static final Logger LOG = Logger.getLogger( ArquivoDeRetornoServiceImpl.class.getName() );

    @Override
    public List<DadosBoletoPagoModel> lerArquivoDeRetorno( File file ) {
        StringBuilder arquivo = new StringBuilder();
        StringBuilder erro = new StringBuilder();
        int cont = 0;
        try ( Scanner scanner = new Scanner( file ) ) {
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                cont++;
            }
        } catch ( FileNotFoundException ex ) {
            LOG.log( Level.SEVERE, ex.getMessage(), ex );
            throw new IllegalStateException( "Arquivo não encontrado!" );
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public void processarBaixaDeBoleto( DadosBoletoPagoModel boletoPagoModels ) {
        throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processarBaixaDeBoleto( List<DadosBoletoPagoModel> boletoPagoModels ) {
        throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
    }

}
