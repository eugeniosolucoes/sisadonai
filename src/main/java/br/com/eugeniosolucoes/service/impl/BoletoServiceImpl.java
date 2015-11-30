/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.service.impl;

import br.com.caelum.stella.boleto.Banco;
import br.com.caelum.stella.boleto.Beneficiario;
import br.com.caelum.stella.boleto.Boleto;
import br.com.caelum.stella.boleto.Datas;
import br.com.caelum.stella.boleto.Endereco;
import br.com.caelum.stella.boleto.Pagador;
import br.com.caelum.stella.boleto.bancos.Santander;
import br.com.eugeniosolucoes.repository.BoletoRepository;
import br.com.eugeniosolucoes.repository.impl.BoletoRepositoryImpl;
import br.com.eugeniosolucoes.service.BoletoService;
import br.com.eugeniosolucoes.util.MyGeradorDeBoleto;
import br.com.eugeniosolucoes.view.model.DadosBoletoFiltroModel;
import br.com.eugeniosolucoes.view.model.DadosBoletoModel;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.util.JRLoader;
import org.joda.time.LocalDate;

/**
 *
 * @author eugenio
 */
public class BoletoServiceImpl implements BoletoService {

    static final Logger LOG = Logger.getLogger( BoletoServiceImpl.class.getName() );

    static final SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat( "yyyy", new Locale( "pt", "BR" ) );

    static final SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat( "MMMM", new Locale( "pt", "BR" ) );

    BoletoRepository repository = new BoletoRepositoryImpl();

    @Override
    public DadosBoletoFiltroModel carregarFiltros() {
        LocalDate dataCorrente = LocalDate.now();
        Date proximoMes = dataCorrente.plusMonths( 1 ).toDate();
        DadosBoletoFiltroModel model = new DadosBoletoFiltroModel();
        model.getAnos().addAll( repository.listarAnos() );
        model.getTurmas().addAll( repository.listarTurmas() );
        model.setAno( YEAR_FORMAT.format( proximoMes ) );
        model.setMes( MONTH_FORMAT.format( proximoMes ) );
        if ( !model.getTurmas().isEmpty() ) {
            model.setTurma( model.getTurmas().get( 0 ) );
        }
        return model;
    }

    @Override
    public List<DadosBoletoModel> listarBoletos( DadosBoletoFiltroModel dadosBoletoFiltroModel ) {
        return repository.listarBoletos( dadosBoletoFiltroModel );
    }

    @Override
    public JasperPrint visualizarBoletos( List<DadosBoletoModel> lista ) {
        if ( lista.isEmpty() ) {
            throw new IllegalStateException( "Nenhum boleto selecionado!" );
        }
        List<Boleto> boletos = new ArrayList<>();

        Santander banco = new Santander();
        
        //TODO: TESTE -105613749500
        long nossoNumeroInicial = Long.parseLong( "1056137495" ) * 100;

        Endereco enderecoBeneficiario = Endereco.novoEndereco()
                .comLogradouro( "Rua da Quitanda, 185" )
                .comBairro( "Centro" )
                .comCep( "20091-005" )
                .comCidade( "Rio de Janeiro" )
                .comUf( "RJ" );

        for ( DadosBoletoModel dados : lista ) {
            
            Datas datas = Datas.novasDatas()
                    .comDocumento( LocalDate.now().getDayOfMonth(),
                            LocalDate.now().getMonthOfYear(),
                            LocalDate.now().getYear() )
                    .comProcessamento( LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthOfYear(), LocalDate.now().getYear() )
                    .comVencimento( LocalDate.fromDateFields( dados.getVencimento() ).getDayOfMonth(),
                            LocalDate.fromDateFields( dados.getVencimento() ).getMonthOfYear(),
                            LocalDate.fromDateFields( dados.getVencimento() ).getYear() );

            //Quem emite o boleto
            Beneficiario beneficiario = Beneficiario.novoBeneficiario()
                    .comNomeBeneficiario( "CURSO ADONAI LTDA" )
                    .comEndereco( enderecoBeneficiario )
                    .comAgencia( "6790" ).comDigitoAgencia( "0" )
                    .comCarteira( "102" )
                    .comNumeroConvenio( "5260965" )
                    .comNossoNumero( String.valueOf( ++nossoNumeroInicial ) );
            
            String digito = banco.getGeradorDeDigito().calculaDVNossoNumero(beneficiario.getNossoNumero());
            
            beneficiario.comDigitoNossoNumero( digito );

            Endereco enderecoPagador = Endereco.novoEndereco()
                    .comLogradouro( dados.getEndereco().getLogradouro() )
                    .comBairro( dados.getEndereco().getBairro() )
                    .comCep( dados.getEndereco().getCep() )
                    .comCidade( dados.getEndereco().getCidade() )
                    .comUf( dados.getEndereco().getEstado() );

            //Quem paga o boleto
            Pagador pagador = Pagador.novoPagador()
                    .comNome( dados.getAluno() )
                    .comDocumento( dados.getCpf() )
                    .comEndereco( enderecoPagador );

            Boleto boleto = Boleto.novoBoleto()
                    .comBanco( banco )
                    .comDatas( datas )
                    .comBeneficiario( beneficiario )
                    .comPagador( pagador )
                    .comValorBoleto( dados.getValor().toString() )
                    .comNumeroDoDocumento( dados.getNumeroDocumento() )
                    .comInstrucoes(
                            "Após 5/12/2015 cobrar: Juros de Mora de 0,99% Mensal (R$ 0,12 ao dia) / Multa de 2,00% (R$ 7,50)",
                            "Mensalidade DEZEMBRO 2015: R$ 375,00",
                            "*** TOTAL À PAGAR ATÉ O VENCIMENTO: R$ 375,00 ***",
                            String.format( "%s (%s)", dados.getAluno(), dados.getTurma() ) )
                    .comLocaisDePagamento( "Até o vencimento pagável em qualquer banco do sistema de compensação", "local 2" );

            boletos.add( boleto );
        }
        MyGeradorDeBoleto gerador = new MyGeradorDeBoleto( boletos );

//        Map<String, Object> parametros = new HashMap<>();
//
//        InputStream templetoBoleto = getClass().getResourceAsStream( "/templates/boleto-default.jasper" );
//
//        parametros.put( "LOGO", getClass().getResourceAsStream( "/imagens/adonai.png" ) );
//
//        parametros.put( JRParameter.REPORT_LOCALE, new Locale( "pt", "BR" ) );
//        InputStream template_sub = getClass().getResourceAsStream( "/templates/boleto-default_instrucoes.jasper" );
//        try {
//            parametros.put( "SUB_INSTRUCOES", JRLoader.loadObject( template_sub ) );
//        } catch ( Exception e ) {
//            LOG.log( Level.SEVERE, e.getMessage(), e );
//        }
//
//        MyGeradorDeBoleto gerador = new MyGeradorDeBoleto( templetoBoleto, parametros, boletos );
        return gerador.geraRelatorio();
    }
}
