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
import br.com.eugeniosolucoes.modelo.SituacaoMatricula;
import br.com.eugeniosolucoes.repository.BoletoRepository;
import br.com.eugeniosolucoes.repository.impl.BoletoRepositoryImpl;
import br.com.eugeniosolucoes.service.BoletoService;
import br.com.eugeniosolucoes.util.MyGeradorDeBoleto;
import br.com.eugeniosolucoes.util.MyStrings;
import br.com.eugeniosolucoes.view.model.DadosBoletoFiltroModel;
import br.com.eugeniosolucoes.view.model.DadosBoletoModel;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperPrint;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author eugenio
 */
public class BoletoServiceImpl implements BoletoService {
    
    static final Logger LOG = LoggerFactory.getLogger( BoletoServiceImpl.class.getName() );
    
    static final Properties CONF = new Properties();
    
    static {
        try {
            CONF.load( BoletoServiceImpl.class.getResourceAsStream( "/conf/boleto.properties" ) );
        } catch ( Exception e ) {
            LOG.error( e.getMessage(), e );
        }
    }
    
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
        List<DadosBoletoModel> lista = repository.listarBoletos( dadosBoletoFiltroModel );
        return lista;
    }
    
    @Override
    public JasperPrint visualizarBoletos( List<DadosBoletoModel> lista ) {
        if ( lista.isEmpty() ) {
            throw new IllegalStateException( "Nenhum boleto selecionado!" );
        }
        List<Boleto> boletos = new ArrayList<>();
        
        for ( DadosBoletoModel dados : lista ) {
            
            if ( !dados.isBoletoValido() ) {
                continue;
            }
            
            try {
                Boleto boleto = criarBoleto( dados );
                boletos.add( boleto );
            } catch ( Exception e ) {
                LOG.error( e.getMessage(), e );
            }
        }
        //MyGeradorDeBoleto gerador = new MyGeradorDeBoleto( boletos );

        //return gerador.geraRelatorio();
        return criarGeradorDeBoleto( boletos ).geraRelatorio();
    }
    
    private Boleto criarBoleto( DadosBoletoModel dados ) {
        Santander banco = new Santander();
        //TODO: TESTE -105613749500
        //long nossoNumeroInicial = 15262;
        Endereco enderecoBeneficiario = Endereco.novoEndereco()
                //.comLogradouro( "Rua São Bento, 13 - 2o Andar Parte" )
                .comLogradouro( CONF.getProperty( "boleto.curso.endereco" ) )
                //.comBairro( "Centro" )
                .comBairro( CONF.getProperty( "boleto.curso.bairro" ) )
                .comCep( CONF.getProperty( "boleto.curso.cep" ) )
                .comCidade( CONF.getProperty( "boleto.curso.cidade" ) )
                .comUf( CONF.getProperty( "boleto.curso.uf" ) );
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
                .comAgencia( "3451" ).comDigitoAgencia( "7" )
                .comCodigoBeneficiario( "13003087" )
                .comDigitoCodigoBeneficiario( "9" )
                .comCarteira( "101" )
                .comNumeroConvenio( "7570007" )
                .comDocumento( "10.851.328/0001-75" )
                .comNossoNumero( dados.getNossoNumero() );
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
                .comDocumento( dados.getCpfFormatado() )
                .comEndereco( enderecoPagador );
        Boleto boleto = Boleto.novoBoleto()
                .comBanco( banco )
                .comDatas( datas )
                .comBeneficiario( beneficiario )
                .comPagador( pagador )
                .comValorBoleto( dados.getValor().toString() )
                .comNumeroDoDocumento( dados.getNumeroDocumento() )
                .comEspecieDocumento( "RC" )
                .comInstrucoes( criarInstrucao1( dados ),
                        criarInstrucao2( dados ),
                        criarInstrucao3( dados ),
                        criarInstrucao4( dados ) )
                .comLocaisDePagamento( "Até o vencimento pagável em qualquer banco do sistema de compensação" );
        return boleto;
    }
    
    @Override
    public byte[] criarBoletoPDF( DadosBoletoModel dados ) {
        Boleto boleto = criarBoleto( dados );
        byte[] pdf = null;
        try {
            pdf = criarGeradorDeBoleto( boleto ).geraPDF();
            Thread.sleep( 1000 );
        } catch ( Exception e ) {
            LOG.error( e.getMessage(), e );
            throw new IllegalStateException( e.getMessage() );
        }
        return pdf;
    }
    
    private static MyGeradorDeBoleto criarGeradorDeBoleto( List<Boleto> boletos ) {
        Map<String, Object> parametros = new HashMap<>();
        
        InputStream templetoBoleto = BoletoServiceImpl.class.getResourceAsStream( "/templates/boleto-default.jasper" );
        
        parametros.put( JRParameter.REPORT_LOCALE, new Locale( "pt", "BR" ) );
        
        MyGeradorDeBoleto gerador = new MyGeradorDeBoleto( templetoBoleto, parametros, boletos );
        return gerador;
    }
    
    private static MyGeradorDeBoleto criarGeradorDeBoleto( Boleto boleto ) {
        Map<String, Object> parametros = new HashMap<>();
        
        InputStream templetoBoleto = BoletoServiceImpl.class.getResourceAsStream( "/templates/boleto-default.jasper" );
        
        parametros.put( JRParameter.REPORT_LOCALE, new Locale( "pt", "BR" ) );
        
        MyGeradorDeBoleto gerador = new MyGeradorDeBoleto( templetoBoleto, parametros, boleto );
        return gerador;
    }

//    private static String criarInstrucao1( DadosBoletoModel dados ) {
//        return String.format( "Após %s cobrar: Juros de Mora de 0,99%% Mensal (R$ %.2f ao dia) / "
//                + "Multa de 2,00%% (R$ %.2f)",
//                DATA_FORMAT.format( dados.getVencimento() ),
//                ( dados.getValor() * dados.getPercentualJuros() / 100 ),
//                ( dados.getValor() * dados.getPercentualMulta() / 100 ) );
//    }
    private static String criarInstrucao1( DadosBoletoModel dados ) {
        return String.format( "Após %s cobrar: Juros de Mora de 1%% Mensal / "
                + "Multa de 2,00%% ",
                DATA_FORMAT.format( dados.getVencimento() ) );
    }
    
    private static String criarInstrucao4( DadosBoletoModel dados ) {
        return String.format( "%s (%s)", dados.getAluno(), dados.getTurma() );
    }
    
    private static String criarInstrucao3( DadosBoletoModel dados ) {
        return String.format( "*** TOTAL À PAGAR ATÉ O VENCIMENTO: R$ %.2f ***", dados.getValor() );
    }
    
    private static String criarInstrucao2( DadosBoletoModel dados ) {
        return String.format( "Parcela %s de %s: R$ %.2f",
                dados.getNumeroMensalidade(),
                dados.getQuantidadeMensalidade(),
                dados.getValor() );
    }
    
    @Override
    public void validarListaDeBoletos( List<DadosBoletoModel> dados ) {
        StringBuilder sb = new StringBuilder();
        for ( DadosBoletoModel model : dados ) {
            Map<String, String> parametros = new HashMap<>();
            if ( MyStrings.isNullOrEmpty( model.getCpf() ) ) {
                parametros.put( "CPF", " CPF: inválido - IMPEDITIVO PARA ENVIO!" );
            }
            if ( MyStrings.isNullOrEmpty( model.getEmail() ) || !MyStrings.validarEmail( model.getEmail() ) ) {
                parametros.put( "EMAIL", " E-mail: inválido!" );
            }
            if ( MyStrings.isNullOrEmpty( model.getEndereco().getCep() ) || !MyStrings.validarCEP( model.getEndereco().getCep() ) ) {
                parametros.put( "CEP", " CEP: inválido!" );
            }
            if ( MyStrings.isNullOrEmpty( model.getEndereco().getLogradouro() ) ) {
                parametros.put( "ENDERECO", " Endereço: Não informado!" );
            }
            if ( MyStrings.isNullOrEmpty( model.getEndereco().getBairro() ) ) {
                parametros.put( "BAIRRO", " Bairro: Não informado!" );
            }
            if ( MyStrings.isNullOrEmpty( model.getEndereco().getCidade() ) ) {
                parametros.put( "CIDADE", " Cidade: Não informado!" );
            }
            if ( MyStrings.isNullOrEmpty( model.getEndereco().getEstado() ) ) {
                parametros.put( "ESTADO", " Estado: Não informado!" );
            }
            if ( !parametros.isEmpty() ) {
                sb.append( String.format( "%s - %-55s OBS:", model.getMatricula(), model.getAluno() ) );
                for ( String param : parametros.values() ) {
                    sb.append( param );
                }
                sb.append( String.format( "%n" ) );
            }
        }
        if ( !sb.toString().isEmpty() ) {
            throw new IllegalStateException( sb.toString() );
        }
    }
    
    @Override
    public void validarListaDeBoletos( List<DadosBoletoModel> lista, DadosBoletoFiltroModel boletoFiltroModel ) {
        
        StringBuilder sb = new StringBuilder();
        try {
            validarListaDeBoletos( lista );
        } catch ( Exception e ) {
            sb.append( e.getMessage() );
        }
        List<DadosBoletoModel> listarAlunosPorTurma = repository.listarAlunosPorTurma( boletoFiltroModel );
        for ( DadosBoletoModel aluno : listarAlunosPorTurma ) {
            if ( !lista.contains( aluno ) && SituacaoMatricula.ABERTA.getCodigo().equals( aluno.getSituacaoMensalidade() ) ) {
                sb.append( String.format( "%s - %-55s OBS: ALUNO NÃO ESTÁ NA LISTA! %n", aluno.getMatricula(), aluno.getAluno() ) );
            }
        }
        if ( !sb.toString().isEmpty() ) {
            throw new IllegalStateException( sb.toString() );
        }
    }
    
}
