/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.service.impl;

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
import static br.com.eugeniosolucoes.nfse.util.Config.PROP;
import br.com.eugeniosolucoes.nfse.util.MunicipioRJ;
import static br.com.eugeniosolucoes.nfse.util.XmlUtils.createDataXml;
import br.com.eugeniosolucoes.repository.BoletoRepository;
import br.com.eugeniosolucoes.repository.NfseRepository;
import br.com.eugeniosolucoes.repository.impl.BoletoRepositoryImpl;
import br.com.eugeniosolucoes.repository.impl.NfseRepositoryImpl;
import br.com.eugeniosolucoes.service.NfseService;
import br.com.eugeniosolucoes.view.model.DadosBoletoModel;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NfseServiceImpl implements NfseService {

    private static final Logger LOG = LoggerFactory.getLogger( NfseServiceImpl.class );

    public static final String INF_RPS = "InfRps";

    public static final String LOTE_RPS = "LoteRps";

    public static final String SERIE_RPS = "RPS";

    public static final String CODIGO_TRIBUTACAO_MUNICIPIO = "080214";

    public static final String ITEM_LISTA_SERVICO = "0802";

    private final NsfeServico servico = new NsfeServicoImpl();
    
    private final NfseRepository repository = new NfseRepositoryImpl();

    private final BoletoRepository boletoRepository = new BoletoRepositoryImpl();

    @Override
    public void enviarNsfe() throws Exception {
        int ano = LocalDate.now().getYear();
        int mes = LocalDate.now().getMonthOfYear();
        try {
            List<DadosBoletoModel> boletosPagos = boletoRepository.retornarBoletosPagos( ano, mes );
            int indexLote = repository.retornarMaiorNumeroLote();
            int indexRps = repository.retornarMaiorNumeroRps();
            EnviarLoteRpsEnvio envio = processarLoteRps( indexLote, indexRps, boletosPagos );
            EnviarLoteRpsResposta resposta = servico.enviarLoteRps( envio );
            //TODO:
        } catch ( Exception e ) {
            throw new Exception( e );
        }
    }

    public EnviarLoteRpsEnvio processarLoteRps( int indexLote, int indexRps, List<DadosBoletoModel> boletosPagos ) {
        EnviarLoteRpsEnvio loteRpsEnvio = new EnviarLoteRpsEnvio();
        TcLoteRps loteRps = loteRpsEnvio.getLoteRps();
        loteRps.setId( LOTE_RPS + indexLote );
        loteRps.setNumeroLote( new BigInteger( String.valueOf( indexLote ) ) );
        loteRps.setCnpj( PROP.getProperty( "Prestardor.Cnpj" ) );
        loteRps.setInscricaoMunicipal( PROP.getProperty( "Prestardor.InscricaoMunicipal" ) );
        TcLoteRps.ListaRps listaRps = loteRps.getListaRps();
        List<TcRps> rps = listaRps.getRps();
        for ( DadosBoletoModel model : boletosPagos ) {
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

            tcValores.setValorServicos( new BigDecimal( model.getValor() ) );
            tcValores.setIssRetido( (byte) 2 );
            tcValores.setBaseCalculo( new BigDecimal( model.getValor() ) );
            tcValores.setValorLiquidoNfse( new BigDecimal( model.getValor() ) );

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
            tcCpfCnpj.setCpf( model.getCpf() );
            tcIdentificacaoTomador.setCpfCnpj( tcCpfCnpj );
            tcDadosTomador.setIdentificacaoTomador( tcIdentificacaoTomador );
            tcInfRps.setTomador( tcDadosTomador );

            tcRps.setInfRps( tcInfRps );
            rps.add( tcRps );
            indexRps++;
        }
        loteRps.setQuantidadeRps( loteRpsEnvio.getLoteRps().getListaRps().getRps().size() );
        return loteRpsEnvio;
    }

}
