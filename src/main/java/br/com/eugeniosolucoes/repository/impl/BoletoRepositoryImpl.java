/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.repository.impl;

import br.com.eugeniosolucoes.view.model.DadosBoletoModel;
import br.com.eugeniosolucoes.view.model.DadosBoletoFiltroModel;
import br.com.eugeniosolucoes.repository.BoletoRepository;
import br.com.eugeniosolucoes.util.MyStrings;
import br.com.eugeniosolucoes.view.model.EnderecoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eugenio
 */
public class BoletoRepositoryImpl implements BoletoRepository {

    static final Logger LOG = Logger.getLogger( BoletoRepositoryImpl.class.getName() );

    AbstractRepository repository = AbstractRepository.getInstance();

    @Override
    public List<String> listarAnos() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = repository.getConnection();
            ps = con.prepareStatement( "SELECT DISTINCT YEAR(m.`Data_Vencimento`) AS ANO FROM `Mensalidades` m " );
            rs = ps.executeQuery();
            List<String> list = new ArrayList<>();
            while (rs.next()) {
                list.add( rs.getString( "ANO" ) );
            }
            return list;
        } catch ( SQLException ex ) {
            LOG.log( Level.SEVERE, null, ex );
        } finally {
            repository.fechar( con, ps, rs );
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<String> listarTurmas() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = repository.getConnection();
            ps = con.prepareStatement( "SELECT DISTINCT t.`Nome_Turma` FROM `TurmasEscola` t ORDER BY t.`Nome_Turma`" );
            rs = ps.executeQuery();
            List<String> list = new ArrayList<>();
            while (rs.next()) {
                list.add( rs.getString( "Nome_Turma" ) );
            }
            return list;
        } catch ( SQLException ex ) {
            LOG.log( Level.SEVERE, null, ex );
        } finally {
            repository.fechar( con, ps, rs );
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<DadosBoletoModel> listarBoletos( DadosBoletoFiltroModel boletoFiltroDTO ) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = repository.getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append( "SELECT DISTINCT " );
            sb.append( "dp.CPF_PFisica, " );
            sb.append( "pf.`Nome_PFisica`, " );
            sb.append( "epf.`Email_Site`, " );
            sb.append( "pf.`Codigo_PFisica`, " );
            sb.append( "tu.`Nome_Turma`, " );
            sb.append( "mens.`Data_Vencimento`, " );
            sb.append( "mens.`Valor_Mensalidade`, " );
            sb.append( "mens.`Nosso_Numero`, " );
            sb.append( "mens.`Codigo_Situacao_Mensalidade`, " );
            sb.append( "mens.`Numero_Mensalidade`,  " );
            sb.append( "tabmens.`Qtde_Mensalidades`, " );
            sb.append( "mens.`Percentual_Multa`,  " );
            sb.append( "mens.`Percentual_Juros`,  " );
            sb.append( "ep.`Endereco`,   " );
            sb.append( "ep.`Complemento_Endereco`,   " );
            sb.append( "bai.`Nome_Bairro`,   " );
            sb.append( "cid.`Nome_Cidade`,   " );
            sb.append( "est.`Sigla_Estado`,  " );
            sb.append( "ep.`CEP_Endereco`  " );
            sb.append( "FROM ((((((((((((((`PFisicas` pf " );
            sb.append( "INNER JOIN `Matriculas` m ON m.`Codigo_PFisica` = pf.`Codigo_PFisica`) " );
            sb.append( "INNER JOIN `PeriodosLetivos` pl ON pl.`Codigo_Periodo_Letivo` = m.`Codigo_Periodo_Letivo`) " );
            sb.append( "INNER JOIN `Cursos` c ON c.`Codigo_Curso` = m.`Codigo_Curso`) " );
            sb.append( "INNER JOIN `Series` s ON s.`Codigo_Serie` = m.`Codigo_Serie`) " );
            sb.append( "INNER JOIN `Turnos` t ON t.`Codigo_Turno` = m.`Codigo_Turno`) " );
            sb.append( "INNER JOIN `EmailSitePFisicas` epf ON epf.`Codigo_PFisica` = pf.`Codigo_PFisica`) " );
            sb.append( "INNER JOIN `TurmasEscola` tu ON tu.`Codigo_Curso` = c.`Codigo_Curso` AND tu.`Codigo_Serie`= s.`Codigo_Serie` AND tu.`Codigo_Turno` = t.`Codigo_Turno`) " );
            sb.append( "INNER JOIN `Mensalidades` mens ON mens.`Codigo_Periodo_Letivo` = pl.`Codigo_Periodo_Letivo`  " );
            sb.append( "AND mens.`Codigo_Curso` = c.`Codigo_Curso`  " );
            sb.append( "AND mens.`Codigo_Serie` = s.`Codigo_Serie`  " );
            sb.append( "AND mens.`Codigo_PFisica` = pf.`Codigo_PFisica` ) " );
            sb.append( "INNER JOIN `TabelasMensalidades` tabmens ON tabmens.`Codigo_Periodo_Letivo` = pl.`Codigo_Periodo_Letivo` " );
            sb.append( "AND tabmens.`Codigo_Curso` = c.`Codigo_Curso` " );
            sb.append( "AND tabmens.`Codigo_Serie` = s.`Codigo_Serie` ) " );
            sb.append( "INNER JOIN `DocumentosPFisicas` dp ON dp.`Codigo_PFisica` = pf.`Codigo_PFisica`) " );
            sb.append( "INNER JOIN `EnderecosPFisicas` ep ON ep.`Codigo_PFisica` = pf.`Codigo_PFisica` ) " );
            sb.append( "INNER JOIN `Estados` est ON est.`Codigo_Estado` = ep.`Codigo_Estado`  ) " );
            sb.append( "INNER JOIN `Cidades` cid ON cid.`Codigo_Cidade` = ep.`Codigo_Cidade` AND cid.`Codigo_Estado` = ep.`Codigo_Estado` ) " );
            sb.append( "LEFT  JOIN `Bairros` bai ON bai.`Codigo_Bairro` = ep.`Codigo_Bairro` AND bai.`Codigo_Cidade` = ep.`Codigo_Cidade` AND bai.`Codigo_Estado` = ep.`Codigo_Estado` ) " );
            sb.append( "WHERE 1 = 1 " );
            sb.append( "AND YEAR(mens.`Data_Vencimento`) = ? " );
            sb.append( "AND MONTH(mens.`Data_Vencimento`) = ? " );
            sb.append( "AND tu.`Nome_Turma` = ? " );
            if ( !MyStrings.isNullOrEmpty( boletoFiltroDTO.getMatriculaAluno() ) ) {
                sb.append( "AND ( m.Codigo_PFisica LIKE ? OR pf.Nome_PFisica LIKE ? ) " );
            }
            sb.append( "AND m.`Codigo_Situacao_Aluno` = '01' " );
            sb.append( "ORDER BY pf.`Nome_PFisica`; " );
            ps = con.prepareStatement( sb.toString() );
            ps.setInt( 1, Integer.valueOf( boletoFiltroDTO.getAno() ) );
            ps.setInt( 2, boletoFiltroDTO.getMeses().indexOf( boletoFiltroDTO.getMes() ) + 1 );
            ps.setString( 3, boletoFiltroDTO.getTurma() );
            if ( !MyStrings.isNullOrEmpty( boletoFiltroDTO.getMatriculaAluno() ) ) {
                ps.setString( 4, String.format( "%s", "%" + boletoFiltroDTO.getMatriculaAluno().trim().toUpperCase() + "%" ) );
                ps.setString( 5, String.format( "%s", "%" + boletoFiltroDTO.getMatriculaAluno().trim().toUpperCase() + "%" ) );
            }
            rs = ps.executeQuery();
            List<DadosBoletoModel> list = new ArrayList<>();
            while (rs.next()) {
                list.add( new DadosBoletoModel(
                        rs.getString( "CPF_PFisica" ),
                        rs.getString( "Codigo_PFisica" ),
                        rs.getString( "Nome_PFisica" ), rs.getString( "Nome_Turma" ),
                        rs.getString( "Nosso_Numero" ),
                        rs.getDouble( "Valor_Mensalidade" ),
                        rs.getDate( "Data_Vencimento" ),
                        rs.getString( "Codigo_Situacao_Mensalidade" ),
                        rs.getString( "Numero_Mensalidade" ),
                        rs.getString( "Qtde_Mensalidades" ),
                        rs.getDouble( "Percentual_Multa" ),
                        rs.getDouble( "Percentual_Juros" ),
                        new EnderecoModel(
                                rs.getString( "Endereco" ),
                                rs.getString( "Complemento_Endereco" ),
                                rs.getString( "Nome_Bairro" ),
                                rs.getString( "Nome_Cidade" ),
                                rs.getString( "Sigla_Estado" ),
                                rs.getString( "CEP_Endereco" )
                        ), rs.getString( "Email_Site" ) ) );
            }
            return list;
        } catch ( SQLException ex ) {
            LOG.log( Level.SEVERE, null, ex );
        } finally {
            repository.fechar( con, ps, rs );
        }
        return Collections.EMPTY_LIST;
    }

}
