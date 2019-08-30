/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.util;

import br.com.caelum.stella.boleto.Boleto;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author eugenio
 */
public class ArquivoRemessaCNAB400 {
    
    private final HeaderCNAB400 header;
    
    private final DetalheCNAB400 detalhe;
    
    private final MultaCNAB400 multa;
    
    private final TrailerCNAB400 trailer;
    
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat( "ddMMyy" );
    
    private final SimpleDateFormat DATE_FORMAT2 = new SimpleDateFormat( "ddMMyyyy" );
    
    public ArquivoRemessaCNAB400() {
        header = new HeaderCNAB400();
        detalhe = new DetalheCNAB400();
        multa = new MultaCNAB400();
        trailer = new TrailerCNAB400();
    }
    
    public String processarArquivoRemessa( List<Boleto> boletos ) {
        if ( boletos.isEmpty() ) {
            throw new IllegalStateException( "Nenhum boleto selecionado!" );
        }
        String result = "";
        Boleto modelo = boletos.get( 0 );
        int numSeq = 1;
        try {
            header.getCampo( "AGENCIA" ).setValor( modelo.getBeneficiario().getAgencia() );
            header.getCampo( "CONTA" ).setValor( modelo.getBeneficiario().getCodigoBeneficiario() );
            header.getCampo( "DATA_DE_GERACAO" ).setValor( DATE_FORMAT.format( new Date() ) );
            header.getCampo( "NOME_DA_EMPRESA" ).setValor( modelo.getBeneficiario().getNomeBeneficiario() );
            header.getCampo( "NUMERO_SEQUENCIAL" ).setValor( String.valueOf( numSeq++ ) );
            result += header.getLinhaHeader() + String.format( "%n" );
            for ( Boleto boleto : boletos ) {
                detalhe.getCampo( "TIPO_DE_REGISTRO" ).setValor( "1" );
                detalhe.getCampo( "CODIGO_DE_INSCRICAO" ).setValor( "02" );
                detalhe.getCampo( "NUMERO_DE_INSCRICAO" ).setValor( modelo.getBeneficiario().getDocumento().replaceAll( "\\D*", "" ) );
                detalhe.getCampo( "AGENCIA" ).setValor( modelo.getBeneficiario().getAgencia() );
                detalhe.getCampo( "CONTA" ).setValor( modelo.getBeneficiario().getCodigoBeneficiario() );
                detalhe.getCampo( "NOSSO_NUMERO" ).setValor( boleto.getNossoNumeroECodDocumento().substring( 4, 12 ) );
                detalhe.getCampo( "NR_DA_CARTEIRA" ).setValor( boleto.getBeneficiario().getCarteira() );
                detalhe.getCampo( "CARTEIRA" ).setValor( "I" );//CODIGO CARTEIRA 
                detalhe.getCampo( "COD_DE_OCORRENCIA" ).setValor( "1" ); //REMESSA
                detalhe.getCampo( "NR_DO_DOCUMENTO" ).setValor( boleto.getNumeroDoDocumento() ); // MATRICULA/MES_MENSALIDADE
                detalhe.getCampo( "VENCIMENTO" ).setValor( DATE_FORMAT.format( boleto.getDatas().getVencimento().getTime() ) );
                detalhe.getCampo( "VALOR_DO_TITULO" ).setValor( tratarValorBoleto( boleto ) );
                detalhe.getCampo( "CODIGO_DO_BANCO" ).setValor( "341" );
                detalhe.getCampo( "ESPECIE" ).setValor( "04" ); // MENSALIDADE ESCOLAR
                detalhe.getCampo( "ACEITE" ).setValor( boleto.getAceite() ? "A" : "N" );
                detalhe.getCampo( "DATA_DE_EMISSAO" ).setValor( DATE_FORMAT.format( boleto.getDatas().getDocumento().getTime() ) );
                detalhe.getCampo( "INSTRUCAO_1" ).setValor( "05" ); //RECEBER CONFORME INSTRUÇÕES NO PRÓPRIO TÍTULO
                detalhe.getCampo( "INSTRUCAO_2" ).setValor( "05" ); //RECEBER CONFORME INSTRUÇÕES NO PRÓPRIO TÍTULO
                detalhe.getCampo( "JUROS_DE_1_DIA" ).setValor( tratarValorJuros( boleto.getValorBoleto() ) );
                detalhe.getCampo( "CODIGO_DE_INSCRICAO_PAGADOR" ).setValor( "1" );
                detalhe.getCampo( "NUMERO_DE_INSCRICAO_PAGADOR" ).setValor( boleto.getPagador().getDocumento().replaceAll( "\\D*", "" ) );
                detalhe.getCampo( "NOME_PAGADOR" ).setValor( MyStrings.removerAcentos( boleto.getPagador().getNome().toUpperCase() ) );
                detalhe.getCampo( "LOGRADOURO" ).setValor( MyStrings.removerAcentos( boleto.getPagador().getEndereco().getLogradouro().toUpperCase() ) );
                detalhe.getCampo( "BAIRRO" ).setValor( MyStrings.removerAcentos( boleto.getPagador().getEndereco().getBairro().toUpperCase() ) );
                detalhe.getCampo( "CEP" ).setValor( boleto.getPagador().getEndereco().getCep() );
                detalhe.getCampo( "CIDADE" ).setValor( MyStrings.removerAcentos( boleto.getPagador().getEndereco().getCidade().toUpperCase() ) );
                detalhe.getCampo( "ESTADO" ).setValor( MyStrings.removerAcentos( boleto.getPagador().getEndereco().getUf().toUpperCase() ) );
                detalhe.getCampo( "NUMERO_SEQUENCIAL" ).setValor( String.valueOf( numSeq++ ) );
                detalhe.getCampo( "DATA_DE_MORA" ).setValor( DATE_FORMAT.format( boleto.getDatas().getVencimento().getTime() ) );
                
                result += detalhe.getLinhaDetalhe() + String.format( "%n" );
                /**
                 * (35) - MULTA O código da Multa irá determinar como o sistema
                 * irá atribuir o valor da multa. CÓDIGO DESCRIÇÃO 0 NÃO
                 * REGISTRA A MULTA 1 VALOR EM REAIS (FIXO) 2 VALOR EM
                 * PERCENTUAL COM DUAS CASAS DECIMAIS CONFORME ESTRUTURA DO
                 * CAMPO
                 */
                multa.getCampo( "TIPO_DE_REGISTRO" ).setValor( "2" );
                multa.getCampo( "CODIGO_MULTA" ).setValor( "1" );
                multa.getCampo( "DATA_MULTA" ).setValor( DATE_FORMAT2.format( boleto.getDatas().getVencimento().getTime() ) );
                multa.getCampo( "MULTA" ).setValor( tratarValorMulta( boleto.getValorBoleto() ) );
                multa.getCampo( "NUMERO_SEQUENCIAL" ).setValor( String.valueOf( numSeq++ ) );
                
                result += multa.getLinhaDetalhe() + String.format( "%n" );
            }
            trailer.getCampo( "NUMERO_SEQUENCIAL" ).setValor( String.valueOf( numSeq ) );
            result += trailer.getLinhaTrailer() + String.format( "%n" );
        } catch ( SizeLineException e ) {
            throw new IllegalStateException( e );
        }
        return result;
    }
    
    private static String tratarValorBoleto( Boleto boleto ) {
        return MyStrings.apenasNumeros( boleto.getValorBoleto() );
    }
    
    public static String tratarValorJuros( BigDecimal valorBoleto ) {
        final String taxaDeJuros = "1";
        final String numeroDeDias = "30";
        final int precisaoDeCalculo = 5;
        final BigDecimal percentualJuros = new BigDecimal( taxaDeJuros ).divide( new BigDecimal( numeroDeDias ), precisaoDeCalculo, RoundingMode.HALF_UP );
        final String percentual = "100";
        final int precisaoDeSaida = 2;
        final BigDecimal juros = valorBoleto.multiply( percentualJuros ).divide( new BigDecimal( percentual ), precisaoDeSaida, RoundingMode.HALF_UP );
        return MyStrings.apenasNumeros( juros );
    }
    
    public static String tratarValorMulta( BigDecimal valorBoleto ) {
        final BigDecimal multa = valorBoleto.multiply( new BigDecimal( "0.02" ) );
        return MyStrings.apenasNumeros( multa );
    }
}
