/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.util;

import br.com.caelum.stella.boleto.Banco;
import br.com.caelum.stella.boleto.Beneficiario;
import br.com.caelum.stella.boleto.Boleto;
import br.com.caelum.stella.boleto.Datas;
import br.com.caelum.stella.boleto.Endereco;
import br.com.caelum.stella.boleto.Pagador;
import br.com.caelum.stella.boleto.bancos.Santander;
import br.com.caelum.stella.boleto.transformer.GeradorDeBoleto;
import java.io.IOException;

/**
 *
 * @author eugenio
 */
public class StellaExemploSantander {

    public static void main( String[] args ) throws IOException {
        Datas datas = Datas.novasDatas()
                .comDocumento( 22, 11, 2015 )
                .comProcessamento( 22, 11, 2015 )
                .comVencimento( 5, 12, 2015 );

        Endereco enderecoBeneficiario = Endereco.novoEndereco()
                .comLogradouro( "Rua da Quitanda, 185" )
                .comBairro( "Centro" )
                .comCep( "20091-005" )
                .comCidade( "Rio de Janeiro" )
                .comUf( "RJ" );

        //Quem emite o boleto
        Beneficiario beneficiario = Beneficiario.novoBeneficiario().comNomeBeneficiario( "CURSO ADONAI LTDA" )
                .comEndereco( enderecoBeneficiario )
                .comAgencia( "6790" ).comDigitoAgencia( "0" ).comCarteira( "102" )
                .comNumeroConvenio( "5260965" ).comNossoNumero( "105613749501" )
                .comDigitoNossoNumero( "4" );

        Endereco enderecoPagador = Endereco.novoEndereco()
                .comLogradouro( "Av dos testes, 111 apto 333" )
                .comBairro( "Bairro Teste" )
                .comCep( "01234-111" )
                .comCidade( "São Paulo" )
                .comUf( "SP" );

        //Quem paga o boleto
        Pagador pagador = Pagador.novoPagador()
                .comNome( "Fulano da Silva" )
                .comDocumento( "111.222.333-12" )
                .comEndereco( enderecoPagador );

        Banco banco = new Santander();

        Boleto boleto = Boleto.novoBoleto()
                .comBanco( banco )
                .comDatas( datas )
                .comBeneficiario( beneficiario )
                .comPagador( pagador )
                .comValorBoleto( "200.00" )
                .comNumeroDoDocumento( "1234" )
                .comInstrucoes( "instrucao 1", "instrucao 2", "instrucao 3", "instrucao 4", "instrucao 5" )
                .comLocaisDePagamento( "local 1", "local 2" );

        GeradorDeBoleto gerador = new GeradorDeBoleto( boleto );

        // Para gerar um boleto em PDF  
        gerador.geraPDF( "BancoSantander.pdf" );

        // Para gerar um boleto em PNG  
        gerador.geraPNG( "BancoSantander.png" );

        // Para gerar um array de bytes a partir de um PDF  
        byte[] bPDF = gerador.geraPDF();

        // Para gerar um array de bytes a partir de um PNG  
        byte[] bPNG = gerador.geraPNG();
    }
}
