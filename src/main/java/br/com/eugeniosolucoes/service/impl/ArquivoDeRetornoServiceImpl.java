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
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat( "ddMMyyyy" );

    @Override
    public List<DadosBoletoPagoModel> lerArquivoDeRetorno( File file ) {
        try ( Scanner scanner = new Scanner( file ) ) {
            return criarLista( scanner );
        } catch ( FileNotFoundException ex ) {
            LOG.log( Level.SEVERE, ex.getMessage(), ex );
            throw new IllegalStateException( "Arquivo não encontrado!" );
        }
    }

    @Override
    public List<DadosBoletoPagoModel> lerArquivoDeRetorno( InputStream file ) {
        Scanner scanner = new Scanner( file );
        return criarLista( scanner );
    }

    /**
     * Layout Cobrança CNAB 240  versao  junho 2015 v 2.7.pdf
     * 014 - 014 Cód. segmento do registro detalhe A 001 T 13
     * 016 - 017 Código de movimento (ocorrência) A 002 40
     * 
     * @param scanner
     * @return 
     */
    private List<DadosBoletoPagoModel> criarLista( Scanner scanner ) {
        List<DadosBoletoPagoModel> lista = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String linha = scanner.nextLine();
            if ( linha.charAt( 13 ) == 'T' && linha.substring( 15, 17 ).equals( "06" ) ) {
                String linhaT = linha;
                String linhaU = scanner.nextLine();
                DadosBoletoPagoModel boletoPago = criarDadosBoletoPago( linhaT, linhaU );
                lista.add( boletoPago );
            }
        }
        return lista;
    }

    @Override
    public void processarBaixaDeBoleto( DadosBoletoPagoModel boletoPagoModels ) {
        throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processarBaixaDeBoleto( List<DadosBoletoPagoModel> boletoPagoModels ) {
        throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
    }

    private DadosBoletoPagoModel criarDadosBoletoPago( String linhaT, String linhaU ) {
        DadosBoletoPagoModel dadosBoletoPagoModel = new DadosBoletoPagoModel();
        dadosBoletoPagoModel.setAluno( linhaT.substring( 143, 183 ).trim() );
        dadosBoletoPagoModel.setMatricula( linhaT.substring( 54, 60 ).trim() );
        dadosBoletoPagoModel.setNossoNumero( linhaT.substring( 40, 52 ).trim() );
        dadosBoletoPagoModel.setNumeroMensalidade( linhaT.substring( 61, 63 ).trim() );
        try {
            dadosBoletoPagoModel.setPagamento( DATE_FORMAT.parse( linhaU.substring( 137, 144 ) ) );
            String valorPago = linhaU.substring( 77, 92 );
            double valor = Double.parseDouble( valorPago ) / 100;
            dadosBoletoPagoModel.setValor( valor );
        } catch ( ParseException ex ) {
            LOG.log( Level.SEVERE, ex.getMessage(), ex );
        }
        return dadosBoletoPagoModel;
    }

}
