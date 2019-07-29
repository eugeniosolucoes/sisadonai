/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.service.impl;

import br.com.eugeniosolucoes.repository.BoletoRepository;
import br.com.eugeniosolucoes.repository.impl.BoletoRepositoryImpl;
import br.com.eugeniosolucoes.service.ArquivoDeRetornoService;
import br.com.eugeniosolucoes.view.model.DadosBoletoPagoModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
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
public class ArquivoDeRetornoCNAB400ServiceImpl implements ArquivoDeRetornoService {

    private static final Logger LOG = Logger.getLogger( ArquivoDeRetornoCNAB400ServiceImpl.class.getName() );

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat( "ddMMyy" );

    BoletoRepository repository = new BoletoRepositoryImpl();

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
     * Layout Cobrança CNAB 400
     *
     * @param scanner
     * @return
     */
    private List<DadosBoletoPagoModel> criarLista( Scanner scanner ) {
        List<DadosBoletoPagoModel> lista = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String linha = scanner.nextLine();
            // 1 - SEGMENTO DETALHE
            // 02 - CODIGO OCORRENCIA
            if ( isLinhaDetalhe( linha ) ) {
                DadosBoletoPagoModel boletoPago = criarDadosBoletoPago( linha );
                lista.add( boletoPago );
            }
        }
        return lista;
    }

    private static boolean isLinhaDetalhe( String linha ) {
        if ( linha.charAt( 0 ) == '1' ) {
            // CODIGO DA OCORRENCIA OU MOVIMENTO
            String codigo = linha.substring( 108, 110 );
            switch ( codigo ) {
                case "06":
                    return true;
            }
        }
        return false;
    }

    @Override
    public void processarBaixaDeBoleto( DadosBoletoPagoModel boletoPagoModels ) {
        repository.processarBaixaDeBoleto( boletoPagoModels );
        LOG.info( boletoPagoModels.toString() );
    }

    @Override
    public void processarBaixaDeBoleto( List<DadosBoletoPagoModel> boletoPagoModels ) {
        for ( DadosBoletoPagoModel model : boletoPagoModels ) {
            processarBaixaDeBoleto( model );
        }
    }

    private DadosBoletoPagoModel criarDadosBoletoPago( String linhaDetalhe ) {
        DadosBoletoPagoModel dadosBoletoPagoModel = new DadosBoletoPagoModel();
        dadosBoletoPagoModel.setAluno( linhaDetalhe.substring( 324, 324 + 30 ).trim() );
        dadosBoletoPagoModel.setMatricula( linhaDetalhe.substring( 116, 116 + 6 ).trim() );
        dadosBoletoPagoModel.setNossoNumero( "00" + linhaDetalhe.substring( 126, 126 + 8 ).trim() );
        dadosBoletoPagoModel.setNumeroMensalidade( linhaDetalhe.substring( 123, 123 + 2 ).trim() );
        try {
            dadosBoletoPagoModel.setPagamento( DATE_FORMAT.parse( linhaDetalhe.substring( 110, 110 + 6 ) ) );
            dadosBoletoPagoModel.setVencimento( DATE_FORMAT.parse( linhaDetalhe.substring( 146, 146 + 6 ) ) );

            String valorPago = linhaDetalhe.substring( 152, 152 + 13 );
            double valor = Double.parseDouble( valorPago ) / 100;
            dadosBoletoPagoModel.setValor( valor );
        } catch ( ParseException ex ) {
            LOG.log( Level.SEVERE, ex.getMessage(), ex );
        }
        return dadosBoletoPagoModel;
    }

    public static String checksum( MessageDigest md, File file ) throws IOException {
        return checksum( md, new FileInputStream( file ) );
    }

    public static String checksum( MessageDigest md, InputStream file ) throws IOException {
        // DigestInputStream is better, but you also can hash file like this.
        try ( InputStream fis = file ) {
            byte[] buffer = new byte[1024];
            int nread;
            while (( nread = fis.read( buffer ) ) != -1) {
                md.update( buffer, 0, nread );
            }
        }
        // bytes to hex
        StringBuilder result = new StringBuilder();
        for ( byte b : md.digest() ) {
            result.append( String.format( "%02x", b ) );
        }
        return result.toString();
    }

}
