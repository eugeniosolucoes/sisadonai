/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.repository.impl;

import br.com.eugeniosolucoes.nfse.util.Config;
import br.com.eugeniosolucoes.view.model.NotaCariocaModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.com.eugeniosolucoes.repository.NotaRepository;
import static br.com.eugeniosolucoes.service.NotaService.RPS_AVULSO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotaRepositoryImpl implements NotaRepository {

    private static final Logger LOG = LoggerFactory.getLogger( NotaRepositoryImpl.class );

    AbstractRepository repository = AbstractRepository.getInstance();

    public NotaRepositoryImpl() {
        criarTabelaNotaCarioca();
    }

    @Override
    public void registrarEnvio( NotaCariocaModel notaCariocaModel ) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = repository.getConnection();
            String sql = "INSERT INTO nota_carioca(numero_boleto, numero_lote_rps, numero_rps, data_emissao, protocolo, processado) VALUES(?,?,?,?,?,?)";
            ps = con.prepareStatement( sql );
            ps.setString( 1, notaCariocaModel.getNumeroBoleto() );
            ps.setInt( 2, notaCariocaModel.getNumeroLoteRps() );
            ps.setInt( 3, notaCariocaModel.getNumeroRps() );
            ps.setDate( 4, new java.sql.Date( notaCariocaModel.getDataEmissao().getTime() ) );
            ps.setString( 5, notaCariocaModel.getProtocolo() );
            ps.setByte( 6, (byte) 0 );
            ps.executeUpdate();
        } catch ( Exception ex ) {
            throw new IllegalStateException( ex );
        } finally {
            repository.fechar( con, ps );
        }
    }

    @Override
    public void registrarRpsAvulso( NotaCariocaModel notaCariocaModel ) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = repository.getConnection();
            String sql = "INSERT INTO nota_carioca(numero_boleto, numero_lote_rps, numero_rps, data_emissao, protocolo, processado) VALUES(?,?,?,?,?,?)";
            ps = con.prepareStatement( sql );
            ps.setString( 1, notaCariocaModel.getNumeroBoleto() );
            ps.setInt( 2, notaCariocaModel.getNumeroLoteRps() );
            ps.setInt( 3, notaCariocaModel.getNumeroRps() );
            ps.setDate( 4, new java.sql.Date( notaCariocaModel.getDataEmissao().getTime() ) );
            ps.setString( 5, notaCariocaModel.getProtocolo() );
            ps.setByte( 6, (byte) 1 );
            ps.executeUpdate();
        } catch ( Exception ex ) {
            throw new IllegalStateException( ex );
        } finally {
            repository.fechar( con, ps );
        }
    }

    private void criarTabelaNotaCarioca() {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = repository.getConnection();
            String sql = "CREATE TABLE nota_carioca( numero_boleto CHAR(10) NOT NULL,\n"
                    + "numero_lote_rps INT NOT NULL,\n"
                    + "numero_rps INT NOT NULL,\n"
                    + "data_emissao DATETIME NOT NULL,\n"
                    + "protocolo TEXT NOT NULL,\n"
                    + "processado BIT,\n"
                    + "CONSTRAINT pk_NossoNumero PRIMARY KEY (numero_boleto) );";
            ps = con.prepareStatement( sql );
            ps.execute();
        } catch ( SQLException ex ) {
            LOG.debug( ex.getMessage() );
        } finally {
            repository.fechar( con, ps );
        }
    }

    @Override
    public List<NotaCariocaModel> listarRspEnviados( Date data ) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = repository.getConnection();
            String sql = "SELECT * FROM `nota_carioca` WHERE data_emissao = ?;";
            ps = con.prepareStatement( sql );
            ps.setDate( 1, new java.sql.Date( data.getTime() ) );
            rs = ps.executeQuery();
            List<NotaCariocaModel> list = new ArrayList<>();
            while (rs.next()) {
                list.add( new NotaCariocaModel(
                        rs.getString( "numero_boleto" ),
                        rs.getInt( "numero_lote_rps" ),
                        rs.getInt( "numero_rps" ),
                        rs.getDate( "data_emissao" ),
                        rs.getString( "protocolo" ),
                        rs.getByte( "processado" ) == 1 ) );
            }
            return list;
        } catch ( Exception ex ) {
            throw new IllegalStateException( ex );
        } finally {
            repository.fechar( con, ps, rs );
        }
    }

    @Override
    public int retornarProximoNumeroLote() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = repository.getConnection();
            String sql = "SELECT MAX(`numero_lote_rps`) AS MAX_NUM_LOT_RPS FROM `nota_carioca`;";
            ps = con.prepareStatement( sql );
            rs = ps.executeQuery();
            Object result = null;
            while (rs.next()) {
                result = rs.getObject( "MAX_NUM_LOT_RPS" );
            }
            return result == null ? Integer.parseInt( Config.PROP.getProperty( "Numero.Lote.Rps.Inicial" ) ) : (int) result + 1;
        } catch ( Exception ex ) {
            throw new IllegalStateException( ex );
        } finally {
            repository.fechar( con, ps, rs );
        }
    }

    @Override
    public int retornarProximoNumeroRps() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = repository.getConnection();
            String sql = "SELECT MAX(`numero_rps`) AS MAX_NUM_RPS FROM `nota_carioca`;";
            ps = con.prepareStatement( sql );
            rs = ps.executeQuery();
            Object result = null;
            while (rs.next()) {
                result = rs.getObject( "MAX_NUM_RPS" );
            }
            return result == null ? Integer.parseInt( Config.PROP.getProperty( "Numero.Rps.Inicial" ) ) : (int) result + 1;
        } catch ( SQLException | NumberFormatException ex ) {
            throw new IllegalStateException( ex );
        } finally {
            repository.fechar( con, ps, rs );
        }
    }

    @Override
    public boolean retornarFaltaProcessar() {
        String sql = "SELECT DISTINCT 1 AS FALTA_PROCESSAR FROM `nota_carioca` nc WHERE EXISTS(SELECT * FROM `nota_carioca` nc1 WHERE nc1.`processado` = 0 )";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = repository.getConnection();
            ps = con.prepareStatement( sql );
            rs = ps.executeQuery();
            Object result = null;
            while (rs.next()) {
                result = rs.getObject( "FALTA_PROCESSAR" );
            }
            return result != null;
        } catch ( SQLException | NumberFormatException ex ) {
            throw new IllegalStateException( ex );
        } finally {
            repository.fechar( con, ps, rs );
        }
    }

    @Override
    public String retornarUltimoProtocolo() {
        String sql = "SELECT DISTINCT `protocolo` FROM `nota_carioca` WHERE `processado` = 0";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = repository.getConnection();
            ps = con.prepareStatement( sql );
            rs = ps.executeQuery();
            String result = null;
            while (rs.next()) {
                result = rs.getString( "protocolo" );
            }
            return result;
        } catch ( SQLException ex ) {
            throw new IllegalStateException( ex );
        } finally {
            repository.fechar( con, ps, rs );
        }
    }

    @Override
    public void atualizarLoteProcessadoComSucesso( String protocolo ) {
        String sql = "UPDATE `nota_carioca` SET `processado` = 1  WHERE `protocolo` = ?";
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = repository.getConnection();
            ps = con.prepareStatement( sql );
            ps.setString( 1, protocolo );
            ps.executeUpdate();
        } catch ( SQLException ex ) {
            throw new IllegalStateException( ex );
        } finally {
            repository.fechar( con, ps );
        }
    }

    @Override
    public void removerLoteProcessadoComErro( String protocolo ) {
        String sql = "DELETE FROM `nota_carioca` WHERE `protocolo` = ?";
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = repository.getConnection();
            ps = con.prepareStatement( sql );
            ps.setString( 1, protocolo );
            ps.executeUpdate();
        } catch ( SQLException ex ) {
            throw new IllegalStateException( ex );
        } finally {
            repository.fechar( con, ps );
        }
    }

    @Override
    public Date retornarUltimaDataEnvio() {
        String sql = String.format( "SELECT MAX(`data_emissao`) FROM `nota_carioca` WHERE protocolo <> '%s'", RPS_AVULSO );
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        java.util.Date result = null;
        try {
            con = repository.getConnection();
            ps = con.prepareStatement( sql );
            rs = ps.executeQuery();
            while (rs.next()) {
                result = new Date( rs.getDate( 1 ).getTime() );
            }
        } catch ( Exception ex ) {
            LOG.error( ex.getMessage(), ex );
        } finally {
            repository.fechar( con, ps, rs );
        }
        return result;
    }

    @Override
    public boolean verificarExisteNumeroRps( int numeroRps ) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Object result = null;
        try {
            con = repository.getConnection();
            String sql = "SELECT DISTINCT 1 AS RESULTADO FROM `nota_carioca` n "
                    + "WHERE EXISTS(SELECT * FROM `nota_carioca` n1 "
                    + "WHERE n1.`numero_rps` = ?);";
            ps = con.prepareStatement( sql );
            ps.setInt( 1, numeroRps );
            rs = ps.executeQuery();
            while (rs.next()) {
                result = rs.getObject( "RESULTADO" );
            }
        } catch ( Exception ex ) {
            throw new IllegalStateException( ex );
        } finally {
            repository.fechar( con, ps, rs );
        }
        return result != null;
    }

    @Override
    public boolean verificarExisteNumeroBoleto( String numeroBoleto ) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Object result = null;
        try {
            con = repository.getConnection();
            String sql = "SELECT `numero_boleto` FROM `nota_carioca` WHERE `numero_boleto` = ?;";
            ps = con.prepareStatement( sql );
            ps.setString( 1, numeroBoleto );
            rs = ps.executeQuery();
            while (rs.next()) {
                result = rs.getObject( 1 );
            }
        } catch ( Exception ex ) {
            throw new IllegalStateException( ex );
        } finally {
            repository.fechar( con, ps, rs );
        }
        return result != null;
    }

    @Override
    public boolean excluirRpsAvulso( String numeroBoleto ) {
        Connection con = null;
        PreparedStatement ps = null;
        int result = 0;
        try {
            con = repository.getConnection();
            String sql = String.format("DELETE FROM `nota_carioca` WHERE `numero_boleto` = ? AND protocolo = '%s';", RPS_AVULSO);
            ps = con.prepareStatement( sql );
            ps.setString( 1, numeroBoleto );
            result = ps.executeUpdate();
        } catch ( Exception ex ) {
            throw new IllegalStateException( ex );
        } finally {
            repository.fechar( con, ps );
        }
        return result == 1;
    }  
    
}
