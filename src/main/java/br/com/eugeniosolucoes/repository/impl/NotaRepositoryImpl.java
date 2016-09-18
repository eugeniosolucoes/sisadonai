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
import java.util.ArrayList;
import java.util.Collections;
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
            String sql = "INSERT INTO nota_carioca(numero_boleto, numero_lote_rps, numero_rps, data_emissao, protocolo) VALUES(?,?,?,?,?)";
            ps = con.prepareStatement( sql );
            ps.setString( 1, notaCariocaModel.getNumeroBoleto() );
            ps.setInt( 2, notaCariocaModel.getNumeroLoteRps() );
            ps.setInt( 3, notaCariocaModel.getNumeroRps() );
            ps.setDate( 4, new java.sql.Date( notaCariocaModel.getDataEmissao().getTime() ) );
            ps.setString( 5, notaCariocaModel.getProtocolo() );
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
                        rs.getString( "protocolo" ) ) );
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
            return result == null ? 1 : (int) result + 1;
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
}
