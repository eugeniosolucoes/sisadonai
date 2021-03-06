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
import br.com.eugeniosolucoes.nfse.model.TcIdentificacaoPrestador;
import br.com.eugeniosolucoes.nfse.servico.NsfeServico;
import br.com.eugeniosolucoes.nfse.servico.impl.NsfeServicoImpl;
import org.junit.Test;
import static br.com.eugeniosolucoes.nfse.util.Config.PROP;
import br.com.eugeniosolucoes.nfse.util.XmlUtils;
import br.com.eugeniosolucoes.repository.impl.NotaRepositoryImpl;
import static org.junit.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.com.eugeniosolucoes.repository.NotaRepository;
import br.com.eugeniosolucoes.service.NotaService;
import br.com.eugeniosolucoes.service.impl.NotaServiceImpl;
import br.com.eugeniosolucoes.view.model.NotaCariocaModel;
import java.util.List;
import org.joda.time.LocalDate;
import org.joda.time.format.ISODateTimeFormat;

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

    private final NsfeServico servico = new NsfeServicoImpl();

    private final NotaService notaService = new NotaServiceImpl();

    //@Test
    public void testEnviarNsfe() throws Exception {
        notaService.enviarNsfe( LocalDate.parse( "20160804", ISODateTimeFormat.basicDate() ).toDate() );
    }

    @Test
    public void testConsultaSituacaoLoteRps() {
        ConsultarSituacaoLoteRpsEnvio envio = new ConsultarSituacaoLoteRpsEnvio();
        TcIdentificacaoPrestador prestador = new TcIdentificacaoPrestador();
        prestador.setCnpj( PROP.getProperty( "Prestador.Cnpj" ) );
        prestador.setInscricaoMunicipal( PROP.getProperty( "Prestador.InscricaoMunicipal" ) );
        envio.setPrestador( prestador );
        envio.setProtocolo( "00000000000000000000000000000000000000000001643764" );
        ConsultarSituacaoLoteRpsResposta resposta = servico.consultarSituacaoLoteRps( envio );

        String xml = XmlUtils.createXmlFromObject( resposta );

        System.out.println( xml );
    }

    @Test
    public void testConsultaLoteRps() {
        // PROXIMOS LOTE/RPS DE TESTE: 122/1422
        ConsultarLoteRpsEnvio envio = new ConsultarLoteRpsEnvio();
        TcIdentificacaoPrestador prestador = new TcIdentificacaoPrestador();
        prestador.setCnpj( PROP.getProperty( "Prestador.Cnpj" ) );
        prestador.setInscricaoMunicipal( PROP.getProperty( "Prestador.InscricaoMunicipal" ) );
        envio.setPrestador( prestador );
        envio.setProtocolo( "00000000000000000000000000000000000000000001645157" );
        ConsultarLoteRpsResposta resposta = servico.consultarLoteRps( envio );
        String xml = XmlUtils.createXmlFromObject( resposta );

        System.out.println( xml );
    }

    @Test
    public void testProximoNumeroLote() {
        NotaRepository repository = new NotaRepositoryImpl();
        assertNotNull( repository.retornarProximoNumeroLote() );
    }

    @Test
    public void testProximoNumeroRps() {
        NotaRepository repository = new NotaRepositoryImpl();
        assertNotNull( repository.retornarProximoNumeroRps() );
    }

    @Test
    public void testlistarRspEnviados() throws Exception {
        List<NotaCariocaModel> listarRspEnviados = notaService.listarRpsEnviados( LocalDate.parse( "2016098", ISODateTimeFormat.basicDate() ).toDate() );
        for(NotaCariocaModel ncm : listarRspEnviados){
            System.out.println( ncm );
        }
    }
    
}
