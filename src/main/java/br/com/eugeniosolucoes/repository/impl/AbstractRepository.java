/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eugenio
 */
public class AbstractRepository {

    static final Logger LOG = Logger.getLogger(AbstractRepository.class.getName());

    static final String ULR = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=C:/Virtual/Virtual-teste.mdb";

    static {
        try {
            sun.jdbc.odbc.JdbcOdbcDriver.class.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    private AbstractRepository() {
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(ULR, "", "");
    }

    public void fechar(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        }
    }

    public void fechar(Connection con, Statement s) {
        if (s != null) {
            try {
                s.close();
            } catch (SQLException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        }
        fechar(con);
    }

    public void fechar(Connection con, Statement s, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        }
        fechar(con, s);
    }

    public static AbstractRepository getInstance() {
        return AbstractRepositoryHolder.INSTANCE;
    }

    private static class AbstractRepositoryHolder {

        private static final AbstractRepository INSTANCE = new AbstractRepository();

    }
}
