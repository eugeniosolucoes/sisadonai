/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.repository.impl;

import br.com.eugeniosolucoes.repository.NfseRepository;
import static br.com.eugeniosolucoes.repository.impl.BoletoRepositoryImpl.LOG;
import br.com.eugeniosolucoes.util.MyStrings;
import br.com.eugeniosolucoes.view.model.DadosBoletoModel;
import br.com.eugeniosolucoes.view.model.DadosBoletoPagoModel;
import br.com.eugeniosolucoes.view.model.EnderecoModel;
import br.com.eugeniosolucoes.view.model.NotaCariocaModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NfseRepositoryImpl implements NfseRepository {

    private static final Logger LOG = LoggerFactory.getLogger( NfseRepositoryImpl.class );

    AbstractRepository repository = AbstractRepository.getInstance();

    public NfseRepositoryImpl() {
        criarTabelaNotaCarioca();
    }

    @Override
    public void registrarEnvio( NotaCariocaModel notaCariocaModel ) {
        throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
    }

    private void criarTabelaNotaCarioca() {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = repository.getConnection();
            StringBuilder sql = new StringBuilder( "CREATE TABLE nota_carioca" )
                    .append( "( numero_boleto CHAR(10) NOT NULL,\n" )
                    .append( "numero_rps INT NOT NULL,\n" )
                    .append( "numero_lote_rps INT NOT NULL,\n" )
                    .append( "data_emissao DATETIME NOT NULL,\n" )
                    .append( "protocolo CHAR(255) NOT NULL,\n" )
                    .append( "situacao INT NOT NULL,\n" )
                    .append( "codigo_verificacao CHAR(255),\n" )
                    .append( "link_nota TEXT,\n" )
                    .append( "CONSTRAINT pk_NossoNumero PRIMARY KEY (numero_boleto) );" );
            ps = con.prepareStatement( sql.toString() );
            ps.execute();
        } catch ( SQLException ex ) {
            LOG.info( ex.getMessage() );
        } finally {
            repository.fechar( con, ps );
        }
    }
}
