/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.repository.impl;

import br.com.eugeniosolucoes.app.Main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author eugenio
 */
public class AbstractRepository {

    private static final Logger LOG = LoggerFactory.getLogger( AbstractRepository.class );

    static final String ULR = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=C:/Virtual/Virtual.mdb";

    static final String ULR_TESTE = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";

    static {
        try {
            sun.jdbc.odbc.JdbcOdbcDriver.class.newInstance();
        } catch ( InstantiationException | IllegalAccessException ex ) {
            LOG.error( ex.getMessage(), ex );
        }
    }

    private AbstractRepository() {
    }

    public Connection getConnection() throws SQLException {
        // TODO - REMOVER APOS TESTE DE HOMOLOGACAO
        Properties properties = new Properties();
        properties.put( "charSet", "Windows-1252" );
        if ( !Main.isTestMode() ) {
            return DriverManager.getConnection( ULR, properties );
        } else {
            return DriverManager.getConnection( ULR_TESTE + Main.getDBTest(), properties );
        }
    }

    public void fechar( Connection con ) {
        if ( con != null ) {
            try {
                con.close();
            } catch ( SQLException ex ) {
                LOG.error( ex.getMessage(), ex );
            }
        }
    }

    public void fechar( Connection con, Statement s ) {
        if ( s != null ) {
            try {
                s.close();
            } catch ( SQLException ex ) {
                LOG.error( ex.getMessage(), ex );
            }
        }
        fechar( con );
    }

    public void fechar( Connection con, Statement s, ResultSet rs ) {
        if ( rs != null ) {
            try {
                rs.close();
            } catch ( SQLException ex ) {
                LOG.error( ex.getMessage(), ex );
            }
        }
        fechar( con, s );
    }

    public static AbstractRepository getInstance() {
        return AbstractRepositoryHolder.INSTANCE;
    }

    private static class AbstractRepositoryHolder {

        private static final AbstractRepository INSTANCE = new AbstractRepository();

    }
}
