/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.util;

import com.jacob.com.*;
import com.jacob.activeX.*;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eugenio
 */
public class ExemploCBXBoleto {

    private static final Logger LOG = Logger.getLogger( ExemploCBXBoleto.class.getName() );
    
    
    /**
     * @author CobreBem Tecnologia
     * @param args
     * @since 2010 <br/>
     * <br/>
     * Este exemplo foi feito utilizando o pacote JACOB 1.15-M3 <br/>
     * Não esqueça de colocar a DLL do pacote JACOB na pasta
     * "%SystemRoot%/System32" <br/>
     * e o JAR do pacote JACOB dentro do diretório de Libraries da sua aplicação
     */
    public static void main(String[] args)  {
        try {
            ActiveXComponent cbx = new ActiveXComponent("CobreBemX.ContaCorrente");

            Properties props = new Properties();
            props.load(ExemploCBXBoleto.class.getResourceAsStream("/conf/boleto.properties"));

            // Monta arquivo de licença de teste para banco 104 carteira CR;
            cbx.setProperty("ArquivoLicenca", props.getProperty("cobrebem.licenca"));

            // Verifica se há algum erro no arquivo de licença;
            if (cbx.getProperty("UltimaMensagemErro") == null) {
                System.out.print(cbx.getProperty("UltimaMensagemErro"));
            }

            //Dados de conta corrente;
            // Monta dados da conta corrente apartir da tela de configuração;
            //cbx.invoke("ConfiguraContaCorrente");
            // Monta dados da conta corrente manualmente pelo código;
            cbx.setProperty("CodigoAgencia", props.getProperty("cobrebem.agencia"));
            cbx.setProperty("NumeroContaCorrente", props.getProperty("cobrebem.conta-corrente"));
            cbx.setProperty("InicioNossoNumero", props.getProperty("cobrebem.inicio-nossonumero"));
            cbx.setProperty("FimNossoNumero", props.getProperty("cobrebem.fim-nossonumero"));

            int proximoNN = Integer.parseInt(cbx.getProperty("InicioNossoNumero").toString()) + 15;
            cbx.setProperty("ProximoNossoNumero", proximoNN);

            // Cria PadroesBoleto e PadroesBoletoImpresso;
            Dispatch PadroesBoleto = cbx.getProperty("PadroesBoleto").toDispatch();
            Dispatch PadroesBoletoImpresso = Dispatch.get(PadroesBoleto, "PadroesBoletoImpresso").toDispatch();
            // Define o arquivo logotipo;
            Dispatch.put(PadroesBoletoImpresso, "ArquivoLogotipo", props.getProperty("cobrebem.logo-adonai"));
            // Define a pasta que contem as imagens que serão utilizadas no boleto;
            Dispatch.put(PadroesBoletoImpresso, "CaminhoImagensCodigoBarras", props.getProperty("cobrebem.imagens"));

            //	  Cria documento de cobrança;
            Dispatch DocumentosCobranca = cbx.getProperty("DocumentosCobranca").toDispatch();
            Dispatch Boleto = Dispatch.get(DocumentosCobranca, "Add").toDispatch();

            // Monta dados do sacado;
            Dispatch.put(Boleto, "NomeSacado", "Fulano de Tal");
            Dispatch.put(Boleto, "EnderecoSacado", "Rua de Teste, 123");
            Dispatch.put(Boleto, "BairroSacado", "Bairro de Teste");
            Dispatch.put(Boleto, "CidadeSacado", "Cidade de Teste");
            Dispatch.put(Boleto, "EstadoSacado", "RJ");
            Dispatch.put(Boleto, "CepSacado", "12345678");
            // Para CPF do Sacado -- Dispatch.put(Boleto, "CPFSacado", "111.111.111-11");
            Dispatch.put(Boleto, "CNPJSacado", "11.111.111/1111-11");

            // Monta dados do documento de cobrança;
            Dispatch.put(Boleto, "NumeroDocumento", "12345");
            Dispatch.put(Boleto, "DataDocumento", "05/02/2010");
            Dispatch.put(Boleto, "DataVencimento", "12/02/2010");
            Dispatch.put(Boleto, "ValorDocumento", "123,45");

            // Cria PadroesBoletoSacado;
            Dispatch PadroesBoletoSacado = Dispatch.get(Boleto, "PadroesBoleto").toDispatch();
            // Define Demonstrativo do Boleto;
            Dispatch.put(PadroesBoletoSacado, "Demonstrativo", "Referente a compras na WEB<br><b>O melhor site da Internet</b>");
            // Define Instrucoes Caixa do Boleto;
            Dispatch.put(PadroesBoletoSacado, "InstrucoesCaixa", "<br>Não dispensar juros e multa após o vencimento");

            // Imprime o boleto;
            // cbx.invoke("ImprimeBoletos");
            cbx.invoke("ImprimeBoletosSemPreview");

            cbx = null;

        } catch (IOException | NumberFormatException e) {
            LOG.log( Level.SEVERE, e.getMessage(), e );
        } finally {
            System.exit(0);
        }
        

    }

}
