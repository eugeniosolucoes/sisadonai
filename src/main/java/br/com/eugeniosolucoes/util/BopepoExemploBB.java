/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.util;

/**
 * 
 
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.LocalDate;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.view.BoletoViewer;
import org.jrimum.domkee.comum.pessoa.endereco.CEP;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.comum.pessoa.endereco.UnidadeFederativa;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeCobranca;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeTitulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo.Aceite;

*/
 
/**
 *
 * @author eugenio
 */
public class BopepoExemploBB {

//    private static final Logger LOG = Logger.getLogger(BopepoExemploBB.class.getName());
//
//    private static final SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy", new Locale("pt", "BR"));
//    private static final SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat("MMMM", new Locale("pt", "BR"));
//    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
//
//    public static void main(String[] args) {
//
//        Properties params = new Properties();
//
//        try {
//
//            params.load(BopepoExemploBB.class.getResourceAsStream("/conf/boleto.properties"));
//
//            /*
//                * INFORMANDO DADOS SOBRE O CEDENTE.
//             */
//            Cedente cedente = new Cedente(params.getProperty("boleto.cedente.nome"),
//                    params.getProperty("boleto.cedente.cnpj"));
//
//            /*
//                * INFORMANDO DADOS SOBRE O SACADO.
//             */
//            Sacado sacado = new Sacado("FULANO DE TAL");
//
//            // Informando o endereço do sacado.
//            Endereco enderecoSac = new Endereco();
//            enderecoSac.setUF(UnidadeFederativa.RJ);
//            enderecoSac.setLocalidade("Rio de Janeiro");
//            enderecoSac.setCep(new CEP("55555-555"));
//            enderecoSac.setBairro("Jacarepaguá");
//            enderecoSac.setLogradouro("Rua Fulano de Tal");
//            enderecoSac.setNumero("500");
//            enderecoSac.setComplemento("/BL 6/102");
//            sacado.addEndereco(enderecoSac);
//
//            /*
//                * INFORMANDO OS DADOS SOBRE O TÍTULO.
//             */
//            // Informando dados sobre a conta bancária do título.
//            ContaBancaria contaBancaria = new ContaBancaria(BancosSuportados.BANCO_DO_BRASIL.create());
//            contaBancaria.setAgencia(new Agencia(Integer.valueOf(params.getProperty("boleto.agencia.codigo")), params.getProperty("boleto.agencia.digito")));
//            contaBancaria.setNumeroDaConta(new NumeroDaConta(Integer.valueOf(params.getProperty("boleto.conta.codigo")), params.getProperty("boleto.conta.digito")));
//            contaBancaria.setCarteira(new Carteira(Integer.valueOf(params.getProperty("boleto.carteira.variacao")), TipoDeCobranca.SEM_REGISTRO));
//
//            Titulo titulo = new Titulo(contaBancaria, sacado, cedente);
//            titulo.setNumeroDoDocumento("003774-02");
//            titulo.setNossoNumero("21251410000015263");
//            titulo.setValor(new BigDecimal("375.00"));
//            titulo.setDataDoDocumento(LocalDate.now().toDate());
//            titulo.setDataDoVencimento(LocalDate.parse("2015-12-05").toDate());
//            titulo.setTipoDeDocumento(TipoDeTitulo.RC_RECIBO);
//            titulo.setAceite(Aceite.N);
////            titulo.setDesconto(new BigDecimal(0.05));
////            titulo.setDeducao(BigDecimal.ZERO);
////            titulo.setMora(BigDecimal.ZERO);
////            titulo.setAcrecimo(BigDecimal.ZERO);
////            titulo.setValorCobrado(BigDecimal.ZERO);
//
//            /*
//                * INFORMANDO OS DADOS SOBRE O BOLETO.
//             */
//            Boleto boleto = new Boleto(titulo);
//
//            boleto.setInstrucao1(String.format("Após %s cobrar: Juros de Mora de 0,99%% Mensal (R$ 0,12 ao dia) / Multa de 2,00%% (R$ 7,50)", 
//                    DATE_FORMAT.format(titulo.getDataDoVencimento())));
//            boleto.setInstrucao2(String.format("Mensalidade %s %s: R$ %.2f", 
//                    MONTH_FORMAT.format(titulo.getDataDoVencimento()).toUpperCase(), 
//                    YEAR_FORMAT.format(titulo.getDataDoDocumento()), titulo.getValor()));
//            boleto.setInstrucao3(String.format("* * * TOTAL À PAGAR ATÉ O VENCIMENTO: R$ %.2f * * *", 
//                    titulo.getValor()));
//            boleto.setInstrucao4(String.format("%s (QT/INF/SEM)", 
//                    sacado.getNome()));
//
//            boleto.addTextosExtras("txtRsCedente", String.format("%s  CNJP: %s", 
//                            params.getProperty("boleto.cedente.nome"), 
//                            params.getProperty("boleto.cedente.cnpj")));
//
//            
//            boleto.addTextosExtras("txtFcSacadorAvalistaL2", 
//                    String.format("%s, %s %s %s %s %s %s", 
//                            enderecoSac.getLogradouro(),
//                            enderecoSac.getNumero(),
//                            enderecoSac.getComplemento(),
//                            enderecoSac.getBairro(),
//                            enderecoSac.getLocalidade(),
//                            enderecoSac.getUF().getSigla(),
//                            enderecoSac.getCEP().getCep()
//                    ));
//            
//            boleto.addTextosExtras("txtFcSacadorAvalistaL3", String.format("%s", "111.111.111-11"));            
//            
//            /*
//                * GERANDO O BOLETO BANCÁRIO.
//             */
//            // Instanciando um objeto "BoletoViewer", classe responsável pela
//            // geração do boleto bancário.
//            BoletoViewer boletoViewer = new BoletoViewer(boleto, BopepoExemploBB.class.getResourceAsStream("/templates/template.pdf"));
//
//            // Gerando o arquivo. No caso o arquivo mencionado será salvo na mesma
//            // pasta do projeto. Outros exemplos:
//            // WINDOWS: boletoViewer.getAsPDF("C:/Temp/MeuBoleto.pdf");
//            // LINUX: boletoViewer.getAsPDF("/home/temp/MeuBoleto.pdf");
//            File arquivoPdf = boletoViewer.getPdfAsFile("BopepoExemploBB.pdf");
//
//            // Mostrando o boleto gerado na tela.
//            mostreBoletoNaTela(arquivoPdf);
//
//        } catch (IOException ex) {
//            LOG.log(Level.SEVERE, ex.getMessage(), ex);
//        }
//    }
//
//    /**
//     * Exibe o arquivo na tela.
//     *
//     * @param arquivoBoleto
//     */
//    private static void mostreBoletoNaTela(File arquivoBoleto) throws IOException {
//
//        java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
//
//        desktop.open(arquivoBoleto);
//    }
}
