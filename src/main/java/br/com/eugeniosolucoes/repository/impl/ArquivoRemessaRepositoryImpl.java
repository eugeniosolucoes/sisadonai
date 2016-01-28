/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.repository.impl;

import br.com.eugeniosolucoes.repository.ArquivoRemessaRepository;
import br.com.eugeniosolucoes.util.MyStrings;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eugenio
 */
public class ArquivoRemessaRepositoryImpl implements ArquivoRemessaRepository {

    static final Logger LOG = Logger.getLogger( ArquivoRemessaRepositoryImpl.class.getName() );

    AbstractRepository repository = AbstractRepository.getInstance();

    @Override
    public String retornarCepDoAluno( String cpf ) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String cep = "00000000";
        try {
            con = repository.getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append( "SELECT DISTINCT ep.`CEP_Endereco` FROM ((`PFisicas` pf " );
            sb.append( "INNER JOIN `DocumentosPFisicas` dp ON dp.`Codigo_PFisica` = pf.`Codigo_PFisica` ) " );
            sb.append( "INNER JOIN `EnderecosPFisicas` ep ON ep.`Codigo_PFisica` = pf.`Codigo_PFisica` ) " );
            sb.append( "WHERE 1 = 1 AND dp.`CPF_PFisica` = ? " );

            ps = con.prepareStatement( sb.toString() );

            ps.setString( 1, cpf );

            rs = ps.executeQuery();
            while (rs.next()) {
                cep = rs.getString( "CEP_Endereco" );
            }
        } catch ( SQLException ex ) {
            LOG.log( Level.SEVERE, null, ex );
        } finally {
            repository.fechar( con, ps, rs );
        }
        return cep;
    }

}
