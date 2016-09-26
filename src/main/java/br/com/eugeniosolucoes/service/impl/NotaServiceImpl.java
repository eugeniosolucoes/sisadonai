/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.service.impl;

import br.com.eugeniosolucoes.excecoes.RestamBoletosPagosException;
import br.com.eugeniosolucoes.modelo.RpsConfig;
import br.com.eugeniosolucoes.nfse.model.ConsultarLoteRpsEnvio;
import br.com.eugeniosolucoes.nfse.model.ConsultarLoteRpsResposta;
import br.com.eugeniosolucoes.nfse.model.ConsultarSituacaoLoteRpsEnvio;
import br.com.eugeniosolucoes.nfse.model.ConsultarSituacaoLoteRpsResposta;
import br.com.eugeniosolucoes.nfse.model.EnviarLoteRpsEnvio;
import br.com.eugeniosolucoes.nfse.model.EnviarLoteRpsResposta;
import br.com.eugeniosolucoes.nfse.model.TcContato;
import br.com.eugeniosolucoes.nfse.model.TcCpfCnpj;
import br.com.eugeniosolucoes.nfse.model.TcDadosServico;
import br.com.eugeniosolucoes.nfse.model.TcDadosTomador;
import br.com.eugeniosolucoes.nfse.model.TcIdentificacaoPrestador;
import br.com.eugeniosolucoes.nfse.model.TcIdentificacaoRps;
import br.com.eugeniosolucoes.nfse.model.TcIdentificacaoTomador;
import br.com.eugeniosolucoes.nfse.model.TcInfRps;
import br.com.eugeniosolucoes.nfse.model.TcLoteRps;
import br.com.eugeniosolucoes.nfse.model.TcMensagemRetorno;
import br.com.eugeniosolucoes.nfse.model.TcRps;
import br.com.eugeniosolucoes.nfse.model.TcValores;
import br.com.eugeniosolucoes.nfse.servico.NsfeServico;
import br.com.eugeniosolucoes.nfse.servico.impl.NsfeServicoImpl;
import static br.com.eugeniosolucoes.nfse.util.Config.PROP;
import br.com.eugeniosolucoes.nfse.util.MunicipioRJ;
import br.com.eugeniosolucoes.nfse.util.XmlUtils;
import br.com.eugeniosolucoes.repository.BoletoRepository;
import br.com.eugeniosolucoes.repository.impl.BoletoRepositoryImpl;
import br.com.eugeniosolucoes.repository.impl.NotaRepositoryImpl;
import br.com.eugeniosolucoes.view.model.DadosBoletoModel;
import br.com.eugeniosolucoes.view.model.NotaCariocaModel;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static br.com.eugeniosolucoes.nfse.util.XmlUtils.*;
import javax.xml.datatype.XMLGregorianCalendar;
import br.com.eugeniosolucoes.service.NotaService;
import br.com.eugeniosolucoes.repository.NotaRepository;
import br.com.eugeniosolucoes.util.LocalStoreHelper;
import br.com.eugeniosolucoes.util.MyStrings;
import br.com.eugeniosolucoes.view.model.DadosBoletoPagoModel;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

public class NotaServiceImpl implements NotaService {

    private static final Logger LOG = LoggerFactory.getLogger( NotaServiceImpl.class );

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat( "dd/MM/yyyy" );

    public static final String INF_RPS = "InfRps";

    public static final String LOTE_RPS = "LoteRps";

    public static final String SERIE_RPS = "RPS";

    public static final String CODIGO_TRIBUTACAO_MUNICIPIO = "080214";

    public static final String ITEM_LISTA_SERVICO = "0802";

    private final NsfeServico servico = new NsfeServicoImpl();

    private final NotaRepository repository = new NotaRepositoryImpl();

    private final BoletoRepository boletoRepository = new BoletoRepositoryImpl();

    private RpsConfig rpsConfig;

    public NotaServiceImpl() {
        try {
            rpsConfig = (RpsConfig) LocalStoreHelper.restore( new FileInputStream( RPS_CONFIG_FILE ) );
        } catch ( Exception e ) {
            LOG.error( e.getMessage(), e );
            throw new IllegalStateException( e.getMessage() );
        }
    }

    static enum TipoSituacao {

        NAO_RECEBIDO( (byte) 1, "Não Recebido" ),
        NAO_PROCESSADO( (byte) 2, "Não Processado" ),
        PROCESSADO_COM_ERRO( (byte) 3, "Processado com Erro" ),
        PROCESSADO_COM_SUCESSO( (byte) 4, "Processado com Sucesso" );

        private final byte codigo;

        private final String descricao;

        private TipoSituacao( byte codigo, String descricao ) {
            this.codigo = codigo;
            this.descricao = descricao;
        }

        public byte getCodigo() {
            return codigo;
        }

        public String getDescricao() {
            return descricao;
        }

        public static TipoSituacao fromValue( byte value ) {
            for ( TipoSituacao situacao : TipoSituacao.values() ) {
                if ( situacao.codigo == value ) {
                    return situacao;
                }
            }
            return null;
        }

    }

    @Override
    public void enviarNsfe( Date data ) throws RestamBoletosPagosException {
        try {

            validarUltimoEnvio();

            List<DadosBoletoModel> boletosPagos = boletoRepository.retornarBoletosPagos( data );
            if ( !boletosPagos.isEmpty() ) {

                List<NotaCariocaModel> notas = new ArrayList<>();
                int indexLote = repository.retornarProximoNumeroLote();
                int indexRps = repository.retornarProximoNumeroRps();
                XMLGregorianCalendar dataEmissao = createDataXml();
                EnviarLoteRpsEnvio envio = processarLoteRps( dataEmissao, indexLote, indexRps, boletosPagos, notas );
                EnviarLoteRpsResposta resposta = servico.enviarLoteRps( envio );

                validarEnvio( resposta );

                LOG.info( resposta.getProtocolo() );

                ConsultarSituacaoLoteRpsResposta consultaSituacaoResposta = consultarSituacao( resposta.getProtocolo() );

                if ( consultaSituacaoResposta.getSituacao() > 1 ) {
                    for ( NotaCariocaModel nota : notas ) {
                        nota.setProtocolo( resposta.getProtocolo() );
                        repository.registrarEnvio( nota );
                    }
                } else {
                    verificarProcessamento( resposta.getProtocolo() );
                }
                int total = boletoRepository.retornarRestamBoletosPagos( data );
                if ( total > 0 ) {
                    String msg = String.format( "O lote tem um limite de 50 RPS por envio, nesta data (%s) restaram %d RPS a enviar!\nDeseja enviar o próximo lote desta data?",
                            DATE_FORMAT.format( data ), total );
                    throw new RestamBoletosPagosException( msg );
                }
            } else {
                throw new IllegalStateException( "Não existem registros nesta data que atendam a condição para envio!" );
            }
        } catch ( IllegalStateException e ) {
            LOG.info( e.getMessage() );
            if ( e.getMessage().contains( "ClientTransportException" ) ) {
                throw new IllegalStateException( "Falha na conexão com o servidor da Nota Carioca!" );
            }
            throw new IllegalStateException( e.getMessage() );
        }
    }

    @Override
    public Date validarUltimoEnvio() throws IllegalStateException {
        if ( repository.retornarFaltaProcessar() ) {
            String protocolo = repository.retornarUltimoProtocolo();
            if ( protocolo != null ) {
                ConsultarSituacaoLoteRpsResposta situacao = consultarSituacao( protocolo );
                switch ( TipoSituacao.fromValue( situacao.getSituacao() ) ) {
                    case NAO_RECEBIDO:
                        throw new IllegalStateException( String.format( "O Lote de protocolo (%s) não foi recebido, favor tente novamente mais tarde!", protocolo ) );
                    case NAO_PROCESSADO:
                        throw new IllegalStateException( String.format( "O Lote de protocolo (%s) ainda não foi processado favor aguardar processamento para envio do próximo lote.", protocolo ) );
                    case PROCESSADO_COM_ERRO:
                        repository.removerLoteProcessadoComErro( protocolo );
                        verificarProcessamento( protocolo );
                    case PROCESSADO_COM_SUCESSO:
                        repository.atualizarLoteProcessadoComSucesso( protocolo );
                        break;
                }
            }
        }
        try {
            return repository.retornarUltimaDataEnvio();
        } catch ( Exception e ) {
            return null;
        }
    }

    private ConsultarSituacaoLoteRpsResposta consultarSituacao( String protocolo ) {
        ConsultarSituacaoLoteRpsEnvio consultaSituacaoEnvio = new ConsultarSituacaoLoteRpsEnvio();
        TcIdentificacaoPrestador prestador = criarPrestador();
        consultaSituacaoEnvio.setPrestador( prestador );
        consultaSituacaoEnvio.setProtocolo( protocolo );
        ConsultarSituacaoLoteRpsResposta consultaSituacaoResposta = servico.consultarSituacaoLoteRps( consultaSituacaoEnvio );
        return consultaSituacaoResposta;
    }

    private void validarEnvio( EnviarLoteRpsResposta resposta ) throws IllegalStateException {
        StringBuilder sb = new StringBuilder();
        if ( resposta.getProtocolo() == null ) {
            if ( resposta.getListaMensagemRetorno() != null ) {
                for ( TcMensagemRetorno mensagemRetorno : resposta.getListaMensagemRetorno().getMensagemRetorno() ) {
                    sb.append( criarMsgRetorno( mensagemRetorno ) ).append( "\n" );
                }
            }
            LOG.info( sb.toString() );
            throw new IllegalStateException( "Falha no envio do Lote RPS, favor entrar em contato com o analista de suporte do SisAdonai!\n" + sb.toString() );
        }
    }

    private void verificarProcessamento( String protocolo ) throws IllegalStateException {
        StringBuilder sb = new StringBuilder();
        ConsultarLoteRpsEnvio envio = new ConsultarLoteRpsEnvio();
        TcIdentificacaoPrestador prestador = criarPrestador();
        envio.setPrestador( prestador );
        envio.setProtocolo( protocolo );
        ConsultarLoteRpsResposta resposta = servico.consultarLoteRps( envio );

        if ( resposta.getListaMensagemRetorno() != null ) {
            for ( TcMensagemRetorno mensagemRetorno : resposta.getListaMensagemRetorno().getMensagemRetorno() ) {
                sb.append( criarMsgRetorno( mensagemRetorno ) ).append( "\n" );
            }
            LOG.info( sb.toString() );
            throw new IllegalStateException( String.format( "O Lote de protocolo (%s) foi processado com erros, favor entrar em contato com o analista de suporte do SisAdonai!%nDetalhes:%n%s", protocolo, sb.toString() ) );
        }
    }

    private TcIdentificacaoPrestador criarPrestador() {
        TcIdentificacaoPrestador prestador = new TcIdentificacaoPrestador();
        prestador.setCnpj( PROP.getProperty( "Prestador.Cnpj" ) );
        prestador.setInscricaoMunicipal( PROP.getProperty( "Prestador.InscricaoMunicipal" ) );
        return prestador;
    }

    private static String criarMsgRetorno( TcMensagemRetorno mensagemRetorno ) {
        return String.format( "Problema envio RPS: %s\nmensagem: %s\ncorrecao:%s ",
                mensagemRetorno.getCodigo(),
                mensagemRetorno.getMensagem(),
                mensagemRetorno.getCorrecao() );
    }

    private EnviarLoteRpsEnvio processarLoteRps( XMLGregorianCalendar dataEmissao, int indexLote, int indexRps, List<DadosBoletoModel> boletosPagos, List<NotaCariocaModel> notas ) {
        TcIdentificacaoPrestador tcIdentificacaoPrestador = criarPrestador();
        EnviarLoteRpsEnvio loteRpsEnvio = new EnviarLoteRpsEnvio();
        TcLoteRps loteRps = loteRpsEnvio.getLoteRps();
        loteRps.setId( LOTE_RPS + indexLote );
        loteRps.setNumeroLote( new BigInteger( String.valueOf( indexLote ) ) );
        loteRps.setCnpj( tcIdentificacaoPrestador.getCnpj() );
        loteRps.setInscricaoMunicipal( tcIdentificacaoPrestador.getInscricaoMunicipal() );
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

            //TODO: DATA PAGAMENTO DO BOLETO OU CARTAO
            tcInfRps.setDataEmissao( XmlUtils.createDataXml( model.getVencimento() ) );
            tcInfRps.setId( INF_RPS + indexRps );
            tcInfRps.setNaturezaOperacao( Byte.parseByte( PROP.getProperty( "Rps.NaturezaOperacao" ) ) );
            tcInfRps.setOptanteSimplesNacional( Byte.parseByte( PROP.getProperty( "Rps.OptanteSimplesNacional" ) ) );
            tcInfRps.setIncentivadorCultural( Byte.parseByte( PROP.getProperty( "Rps.IncentivadorCultural" ) ) );

            TcDadosServico tcDadosServico = new TcDadosServico();
            TcValores tcValores = new TcValores();

            BigDecimal value = new BigDecimal( model.getValor() );
            Formatter valor = new Formatter( Locale.US ).format( "%.2f", value );
            tcValores.setValorServicos( new BigDecimal( valor.toString() ) );
            tcValores.setIssRetido( (byte) 2 );
            tcValores.setBaseCalculo( new BigDecimal( valor.toString() ) );
            tcValores.setValorLiquidoNfse( new BigDecimal( valor.toString() ) );

            tcDadosServico.setValores( tcValores );
            tcDadosServico.setItemListaServico( ITEM_LISTA_SERVICO );
            tcDadosServico.setCodigoTributacaoMunicipio( CODIGO_TRIBUTACAO_MUNICIPIO );
            tcDadosServico.setDiscriminacao( rpsConfig.getDiscriminacaoDoServico() );
            tcDadosServico.setCodigoMunicipio( MunicipioRJ.RIO_DE_JANEIRO.getCodigo() );
            tcInfRps.setServico( tcDadosServico );
            tcInfRps.setStatus( (byte) 1 );

            tcInfRps.setPrestador( tcIdentificacaoPrestador );

            TcDadosTomador tcDadosTomador = new TcDadosTomador();
            tcDadosTomador.setRazaoSocial( model.getAluno() );
            TcContato tcContato = new TcContato();
            tcContato.setEmail( model.getEmail() );
            tcDadosTomador.setContato( tcContato );
            TcIdentificacaoTomador tcIdentificacaoTomador = new TcIdentificacaoTomador();
            TcCpfCnpj tcCpfCnpj = new TcCpfCnpj();
            tcCpfCnpj.setCpf( model.getCpf() );
            tcIdentificacaoTomador.setCpfCnpj( tcCpfCnpj );
            tcDadosTomador.setIdentificacaoTomador( tcIdentificacaoTomador );
            tcInfRps.setTomador( tcDadosTomador );

            tcRps.setInfRps( tcInfRps );
            rps.add( tcRps );
            NotaCariocaModel nota = new NotaCariocaModel( model.getNossoNumero(), indexRps, indexLote, dataEmissao.toGregorianCalendar().getTime(), null, true );
            notas.add( nota );
            indexRps++;
        }
        loteRps.setQuantidadeRps( loteRpsEnvio.getLoteRps().getListaRps().getRps().size() );
        return loteRpsEnvio;
    }

    @Override
    public List<NotaCariocaModel> listarRpsEnviados( Date data ) {
        SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy" );
        List<NotaCariocaModel> listarRspEnviados = repository.listarRspEnviados( data );
        for ( NotaCariocaModel model : listarRspEnviados ) {
            DadosBoletoPagoModel pago = boletoRepository.retornarBoletoPago( model.getNumeroBoleto() );
            model.setNome( pago.getAluno() );
            model.setDataPagamento( sdf.format( pago.getPagamento() ) );
            model.setTotal( String.format( "%.2f", pago.getValor() ) );
            model.setFormaPagamento( pago.getFormaPagamento() );
        }
        return listarRspEnviados;
    }

    @Override
    public void registrarRpsAvulso( NotaCariocaModel model ) {
        try {
            String nossoNumero = MyStrings.padLeft( 10, Integer.valueOf( model.getNumeroBoleto() ) );
            model.setNumeroBoleto( nossoNumero );
            model.setDataEmissao( new Date() );
            model.setNumeroLoteRps( 0 );
            model.setProcessado( true );
            model.setProtocolo( RPS_AVULSO );

            StringBuilder sb = new StringBuilder();

            boolean deveExistirNossoNumero = boletoRepository.verificarExisteNossoNumero( model.getNumeroBoleto() );
            if ( !deveExistirNossoNumero ) {
                sb.append( "Nosso Número não encontrado no sistema!" ).append( "\n" );
            }
            boolean naoDeveExistirNumeroRps = repository.verificarExisteNumeroRps( model.getNumeroRps() );
            if ( naoDeveExistirNumeroRps ) {
                sb.append( "Número RPS já registrado!" ).append( "\n" );
            }

            boolean naoDeveExistirNaTabelaNotaCarioca = repository.verificarExisteNumeroBoleto( nossoNumero );

            if ( naoDeveExistirNaTabelaNotaCarioca ) {
                sb.append( "Nosso Número já registrado como enviado!" ).append( "\n" );
            }

            if ( !sb.toString().isEmpty() ) {
                throw new IllegalStateException( sb.toString() );
            }

            repository.registrarRpsAvulso( model );
        } catch ( NumberFormatException | IllegalStateException e ) {
            if ( e instanceof NumberFormatException ) {
                throw new IllegalStateException( "Nosso Número inválido!" );
            }
            throw new IllegalStateException( e.getMessage() );
        }
    }

    @Override
    public boolean excluirRpsAvulso( String numeroBoleto ) {
        return repository.excluirRpsAvulso( numeroBoleto );
    }

}
