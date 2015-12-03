/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.service.impl;

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
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperPrint;
import org.joda.time.LocalDate;

/**
 *
 * @author eugenio
 */
public class BoletoServiceImpl implements BoletoService {

    static final Logger LOG = Logger.getLogger( BoletoServiceImpl.class.getName() );

    static final SimpleDateFormat DATA_FORMAT = new SimpleDateFormat( "dd/MM/yyyy", new Locale( "pt", "BR" ) );

    static final SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat( "yyyy", new Locale( "pt", "BR" ) );

    static final SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat( "MMMM", new Locale( "pt", "BR" ) );

    BoletoRepository repository = new BoletoRepositoryImpl();

    @Override
    public DadosBoletoFiltroModel carregarFiltros() {
        LocalDate dataCorrente = LocalDate.now();
        Date proximoMes = dataCorrente.plusMonths( 0 ).toDate();
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

            String digito = banco.getGeradorDeDigito().calculaDVNossoNumero( beneficiario.getNossoNumero() );

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
                    .comInstrucoes( criarInstrucao1( dados ),
                            criarInstrucao2( dados ),
                            criarInstrucao3( dados ),
                            criarInstrucao4( dados ) )
                    .comLocaisDePagamento( "Até o vencimento pagável em qualquer banco do sistema de compensação" );

            boletos.add( boleto );
        }
        //MyGeradorDeBoleto gerador = new MyGeradorDeBoleto( boletos );

        //return gerador.geraRelatorio();
        return criarGeradorDeBoleto( boletos ).geraRelatorio();
    }

    private static MyGeradorDeBoleto criarGeradorDeBoleto( List<Boleto> boletos ) {
        Map<String, Object> parametros = new HashMap<>();

        InputStream templetoBoleto = BoletoServiceImpl.class.getResourceAsStream( "/templates/boleto-default.jasper" );

        parametros.put( "LOGO", BoletoServiceImpl.class.getResourceAsStream( "/imagens/adonai.png" ) );

        parametros.put( JRParameter.REPORT_LOCALE, new Locale( "pt", "BR" ) );

        MyGeradorDeBoleto gerador = new MyGeradorDeBoleto( templetoBoleto, parametros, boletos );
        return gerador;
    }

    private static String criarInstrucao1( DadosBoletoModel dados ) {
        return String.format( "Após %s cobrar: Juros de Mora de 0,99%% Mensal (R$ %.2f ao dia) / "
                + "Multa de 2,00%% (R$ %.2f)",
                DATA_FORMAT.format( dados.getVencimento() ),
                ( dados.getValor() * dados.getPercentualJuros() / 100 ),
                ( dados.getValor() * dados.getPercentualMulta() / 100 ) );
    }

    private static String criarInstrucao4( DadosBoletoModel dados ) {
        return String.format( "%s (%s)", dados.getAluno(), dados.getTurma() );
    }

    private static String criarInstrucao3( DadosBoletoModel dados ) {
        return String.format( "*** TOTAL À PAGAR ATÉ O VENCIMENTO: R$ %.2f ***", dados.getValor() );
    }

    private static String criarInstrucao2( DadosBoletoModel dados ) {
        return String.format( "Mensalidade %s %s: R$ %.2f",
                MONTH_FORMAT.format( dados.getVencimento() ).toUpperCase(),
                YEAR_FORMAT.format( dados.getVencimento() ),
                dados.getValor() );
    }
}
