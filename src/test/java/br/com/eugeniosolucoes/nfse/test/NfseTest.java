/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.nfse.test;

import br.com.eugeniosolucoes.nfse.model.ConsultarLoteRpsEnvio;
import br.com.eugeniosolucoes.nfse.model.ConsultarLoteRpsResposta;
import br.com.eugeniosolucoes.nfse.model.ConsultarSituacaoLoteRpsEnvio;
import br.com.eugeniosolucoes.nfse.model.ConsultarSituacaoLoteRpsResposta;
import br.com.eugeniosolucoes.nfse.model.EnviarLoteRpsEnvio;
import br.com.eugeniosolucoes.nfse.model.EnviarLoteRpsResposta;
import br.com.eugeniosolucoes.nfse.model.TcCpfCnpj;
import br.com.eugeniosolucoes.nfse.model.TcDadosServico;
import br.com.eugeniosolucoes.nfse.model.TcDadosTomador;
import br.com.eugeniosolucoes.nfse.model.TcIdentificacaoPrestador;
import br.com.eugeniosolucoes.nfse.model.TcIdentificacaoRps;
import br.com.eugeniosolucoes.nfse.model.TcIdentificacaoTomador;
import br.com.eugeniosolucoes.nfse.model.TcInfRps;
import br.com.eugeniosolucoes.nfse.model.TcLoteRps;
import br.com.eugeniosolucoes.nfse.model.TcRps;
import br.com.eugeniosolucoes.nfse.model.TcValores;
import br.com.eugeniosolucoes.nfse.servico.NsfeServico;
import br.com.eugeniosolucoes.nfse.servico.impl.NsfeServicoImpl;
import br.com.eugeniosolucoes.service.impl.ArquivoDeRetornoServiceImpl;
import br.com.eugeniosolucoes.view.model.DadosBoletoPagoModel;
import java.io.InputStream;
import java.util.List;
import org.junit.Test;
import static br.com.eugeniosolucoes.nfse.util.Config.PROP;
import br.com.eugeniosolucoes.nfse.util.MunicipioRJ;
import br.com.eugeniosolucoes.nfse.util.XmlUtils;
import static br.com.eugeniosolucoes.nfse.util.XmlUtils.*;
import br.com.eugeniosolucoes.repository.BoletoRepository;
import br.com.eugeniosolucoes.repository.impl.BoletoRepositoryImpl;
import br.com.eugeniosolucoes.repository.impl.NotaRepositoryImpl;
import br.com.eugeniosolucoes.service.NotaService;
import br.com.eugeniosolucoes.service.impl.NotaServiceImpl;
import br.com.eugeniosolucoes.view.model.DadosBoletoModel;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.logging.Level;
import static org.junit.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.com.eugeniosolucoes.repository.NotaRepository;

/**
 *
 * @author eugenio
 */
public class NfseTest {

    private static final Logger LOG = LoggerFactory.getLogger( NfseTest.class );

    public static final String INF_RPS = "InfRps";

    public static final String LOTE_RPS = "LoteRps";

    public static final String SERIE_RPS = "RPS";

    public static final String CODIGO_TRIBUTACAO_MUNICIPIO = "080214";

    public static final String ITEM_LISTA_SERVICO = "0802";

    static final String ARQUIVO = "/arquivos/COBST_L4GR_02_240216P_MOV.TXT";

    static final String ARQUIVO1 = "/arquivos/COBST_L4GR_02_250216P_MOV.TXT";

    static final String ARQUIVO2 = "/arquivos/COBST_L4GR_02_260216P_MOV.TXT";

    private final NsfeServico servico = new NsfeServicoImpl();

    public NfseTest() {
    }

    //@Test
    public void testEnviarLoteRps() throws Exception {

        EnviarLoteRpsEnvio loteRpsEnvio = enviarLoteRps();

        EnviarLoteRpsResposta resposta = servico.enviarLoteRps( loteRpsEnvio );

        String xml = XmlUtils.createXmlFromObject( resposta );

        System.out.println( xml );

    }

    public EnviarLoteRpsEnvio enviarLoteRps() {
        int indexLote = 10;
        int indexRps = indexLote * 2 - 1;
        System.out.println( "lerArquivoDeRetorno" );
        ArquivoDeRetornoServiceImpl instance = new ArquivoDeRetornoServiceImpl();
        String[] arquivos = {ARQUIVO, ARQUIVO1, ARQUIVO2};
        return processarLoteRps( indexLote, arquivos, instance, indexRps );
    }

    public EnviarLoteRpsEnvio processarLoteRps( int indexLote, String[] arquivos, ArquivoDeRetornoServiceImpl instance, int indexRps ) {
        BoletoRepository repository = new BoletoRepositoryImpl();
        EnviarLoteRpsEnvio loteRpsEnvio = new EnviarLoteRpsEnvio();
        TcLoteRps loteRps = loteRpsEnvio.getLoteRps();
        loteRps.setId( LOTE_RPS + indexLote );
        loteRps.setNumeroLote( new BigInteger( String.valueOf( indexLote ) ) );
        loteRps.setCnpj( PROP.getProperty( "Prestardor.Cnpj" ) );
        loteRps.setInscricaoMunicipal( PROP.getProperty( "Prestardor.InscricaoMunicipal" ) );
        TcLoteRps.ListaRps listaRps = loteRps.getListaRps();
        List<TcRps> rps = listaRps.getRps();
        for ( String arquivo : arquivos ) {
            InputStream file = this.getClass().getResourceAsStream( arquivo );
            List<DadosBoletoPagoModel> lerArquivoDeRetorno = instance.lerArquivoDeRetorno( file );

            for ( DadosBoletoPagoModel model : lerArquivoDeRetorno ) {
                instance.processarBaixaDeBoleto( model );
                if ( model.isAtualizado() ) {
                    DadosBoletoModel dados = repository.retornarBoletoPago( model );
                    TcRps tcRps = new TcRps();
                    TcInfRps tcInfRps = new TcInfRps();

                    TcIdentificacaoRps tcIdentificacaoRps = new TcIdentificacaoRps();
                    tcIdentificacaoRps.setNumero( new BigInteger( String.valueOf( indexRps ) ) );
                    tcIdentificacaoRps.setSerie( SERIE_RPS );
                    tcIdentificacaoRps.setTipo( (byte) 1 );
                    tcInfRps.setIdentificacaoRps( tcIdentificacaoRps );

                    tcInfRps.setDataEmissao( createDataXml() );
                    tcInfRps.setId( INF_RPS + indexRps );
                    tcInfRps.setNaturezaOperacao( (byte) 1 );
                    tcInfRps.setOptanteSimplesNacional( (byte) 2 );
                    tcInfRps.setIncentivadorCultural( (byte) 2 );

                    TcDadosServico tcDadosServico = new TcDadosServico();
                    TcValores tcValores = new TcValores();

                    tcValores.setValorServicos( new BigDecimal( dados.getValor() ) );
                    tcValores.setIssRetido( (byte) 2 );
                    tcValores.setBaseCalculo( new BigDecimal( dados.getValor() ) );
                    tcValores.setValorLiquidoNfse( new BigDecimal( dados.getValor() ) );

                    tcDadosServico.setValores( tcValores );
                    tcDadosServico.setItemListaServico( ITEM_LISTA_SERVICO );
                    tcDadosServico.setCodigoTributacaoMunicipio( CODIGO_TRIBUTACAO_MUNICIPIO );
                    tcDadosServico.setDiscriminacao( PROP.getProperty( "Rps.DadosServico.Discriminacao" ) );
                    tcDadosServico.setCodigoMunicipio( MunicipioRJ.RIO_DE_JANEIRO.getCodigo() );
                    tcInfRps.setServico( tcDadosServico );
                    tcInfRps.setStatus( (byte) 1 );

                    TcIdentificacaoPrestador tcIdentificacaoPrestador = new TcIdentificacaoPrestador();

                    tcIdentificacaoPrestador.setCnpj( PROP.getProperty( "Prestardor.Cnpj" ) );
                    tcIdentificacaoPrestador.setInscricaoMunicipal( PROP.getProperty( "Prestardor.InscricaoMunicipal" ) );

                    tcInfRps.setPrestador( tcIdentificacaoPrestador );

                    TcDadosTomador tcDadosTomador = new TcDadosTomador();
                    tcDadosTomador.setRazaoSocial( model.getAluno() );
                    TcIdentificacaoTomador tcIdentificacaoTomador = new TcIdentificacaoTomador();
                    TcCpfCnpj tcCpfCnpj = new TcCpfCnpj();
                    tcCpfCnpj.setCpf( dados.getCpf() );
                    tcIdentificacaoTomador.setCpfCnpj( tcCpfCnpj );
                    tcDadosTomador.setIdentificacaoTomador( tcIdentificacaoTomador );
                    tcInfRps.setTomador( tcDadosTomador );

                    tcRps.setInfRps( tcInfRps );
                    rps.add( tcRps );
                    indexRps++;
                }
            }
            loteRps.setQuantidadeRps( loteRpsEnvio.getLoteRps().getListaRps().getRps().size() );
        }
        return loteRpsEnvio;
    }

    @Test
    public void testConsultaSituacaoLoteRps() {
        ConsultarSituacaoLoteRpsEnvio envio = new ConsultarSituacaoLoteRpsEnvio();
        TcIdentificacaoPrestador prestador = new TcIdentificacaoPrestador();
        prestador.setCnpj( PROP.getProperty( "Prestardor.Cnpj" ) );
        prestador.setInscricaoMunicipal( PROP.getProperty( "Prestardor.InscricaoMunicipal" ) );
        envio.setPrestador( prestador );
        envio.setProtocolo( "00000000000000000000000000000000000000000001642988" );
        ConsultarSituacaoLoteRpsResposta resposta = servico.consultarSituacaoLoteRps( envio );

        String xml = XmlUtils.createXmlFromObject( resposta );

        System.out.println( xml );
    }

    @Test
    public void testConsultaLoteRps() {
        ConsultarLoteRpsEnvio envio = new ConsultarLoteRpsEnvio();
        TcIdentificacaoPrestador prestador = new TcIdentificacaoPrestador();
        prestador.setCnpj( PROP.getProperty( "Prestardor.Cnpj" ) );
        prestador.setInscricaoMunicipal( PROP.getProperty( "Prestardor.InscricaoMunicipal" ) );
        envio.setPrestador( prestador );
        envio.setProtocolo( "00000000000000000000000000000000000000000001642988" );
        ConsultarLoteRpsResposta resposta = servico.consultarLoteRps( envio );
        String xml = XmlUtils.createXmlFromObject( resposta );

        System.out.println( xml );
    }

    @Test
    public void testProximoNumeroLote() {
        NotaRepository repository = new NotaRepositoryImpl();
        assertEquals( 1, repository.retornarProximoNumeroLote() );
    }

    @Test
    public void testProximoNumeroRps() {
        NotaRepository repository = new NotaRepositoryImpl();
        assertEquals( 13620, repository.retornarProximoNumeroRps() );
    }

    @Test
    public void testNotaServico() throws Exception {
        NotaService notaService = new NotaServiceImpl();
        notaService.enviarNsfe( 2015, 5 );
    }

}
