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
            String sql = "INSERT INTO nota_carioca(numero_boleto, numero_rps, numero_lote_rps, data_emissao, protocolo) VALUES(?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString( 1, notaCariocaModel.getNumeroBoleto() );
            ps.setInt( 2, notaCariocaModel.getNumeroRps() );
            ps.setInt( 3, notaCariocaModel.getNumeroLoteRps() );
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
                    + "numero_rps INT NOT NULL,\n"
                    + "numero_lote_rps INT NOT NULL,\n"
                    + "data_emissao DATETIME NOT NULL,\n"
                    + "protocolo TEXT NOT NULL,\n"
                    + "situacao INT,\n"
                    + "codigo_verificacao CHAR(255),\n"
                    + "link_nota TEXT,\n"
                    + "CONSTRAINT pk_NossoNumero PRIMARY KEY (numero_boleto) );";
            ps = con.prepareStatement(sql);
            ps.execute();
        } catch ( SQLException ex ) {
            LOG.info( ex.getMessage() );
        } finally {
            repository.fechar( con, ps );
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
