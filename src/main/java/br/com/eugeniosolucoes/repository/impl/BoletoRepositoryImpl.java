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
import br.com.eugeniosolucoes.view.model.DadosBoletoPagoModel;
import br.com.eugeniosolucoes.view.model.EnderecoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eugenio
 */
public class BoletoRepositoryImpl implements BoletoRepository {

    static final Logger LOG = Logger.getLogger( BoletoRepositoryImpl.class.getName() );

    AbstractRepository repository = AbstractRepository.getInstance();

    static final SimpleDateFormat DB_DATE_FORMAT = new SimpleDateFormat( "MM/dd/yyyy hh:mm a" );

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
            sb.append( "LEFT JOIN `EmailSitePFisicas` epf ON epf.`Codigo_PFisica` = pf.`Codigo_PFisica`) " );
            sb.append( "INNER JOIN `TurmasEscola` tu ON tu.`Codigo_Curso` = c.`Codigo_Curso` AND tu.`Codigo_Serie`= s.`Codigo_Serie` AND tu.`Codigo_Turno` = t.`Codigo_Turno`) " );
            sb.append( "INNER JOIN `Mensalidades` mens ON mens.`Codigo_Periodo_Letivo` = pl.`Codigo_Periodo_Letivo`  " );
            sb.append( "AND mens.`Codigo_Curso` = c.`Codigo_Curso`  " );
            sb.append( "AND mens.`Codigo_Serie` = s.`Codigo_Serie`  " );
            sb.append( "AND mens.`Codigo_PFisica` = pf.`Codigo_PFisica` ) " );
            sb.append( "INNER JOIN `TabelasMensalidades` tabmens ON tabmens.`Codigo_Periodo_Letivo` = pl.`Codigo_Periodo_Letivo` " );
            sb.append( "AND tabmens.`Codigo_Curso` = c.`Codigo_Curso` " );
            sb.append( "AND tabmens.`Codigo_Serie` = s.`Codigo_Serie` ) " );
            sb.append( "INNER JOIN `DocumentosPFisicas` dp ON dp.`Codigo_PFisica` = pf.`Codigo_PFisica`) " );
            sb.append( "LEFT JOIN `EnderecosPFisicas` ep ON ep.`Codigo_PFisica` = pf.`Codigo_PFisica` ) " );
            sb.append( "LEFT JOIN `Estados` est ON est.`Codigo_Estado` = ep.`Codigo_Estado`  ) " );
            sb.append( "LEFT JOIN `Cidades` cid ON cid.`Codigo_Cidade` = ep.`Codigo_Cidade` AND cid.`Codigo_Estado` = ep.`Codigo_Estado` ) " );
            sb.append( "LEFT JOIN `Bairros` bai ON bai.`Codigo_Bairro` = ep.`Codigo_Bairro` AND bai.`Codigo_Cidade` = ep.`Codigo_Cidade` AND bai.`Codigo_Estado` = ep.`Codigo_Estado` ) " );
            sb.append( "WHERE 1 = 1 " );
            sb.append( "AND YEAR(mens.`Data_Vencimento`) = ? " );
            sb.append( "AND MONTH(mens.`Data_Vencimento`) = ? " );
            sb.append( "AND tu.`Nome_Turma` = ? " );
            if ( !MyStrings.isNullOrEmpty( boletoFiltroDTO.getMatriculaAluno() ) ) {
                sb.append( "AND ( m.Codigo_PFisica LIKE ? OR pf.Nome_PFisica LIKE ? ) " );
            }
            sb.append( "AND m.`Codigo_Situacao_Aluno` = '01' " );
            sb.append( "AND mens.`Codigo_Situacao_Mensalidade` = '1' " );
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
            Set<DadosBoletoModel> list = new HashSet<>();
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
                        ), rs.getString( MyStrings.removerAcentos( "Email_Site" ) ) ) );
            }
            List<DadosBoletoModel> result = new ArrayList<>( list );
            Collections.sort( result );
            return result;
        } catch ( SQLException ex ) {
            LOG.log( Level.SEVERE, null, ex );
        } finally {
            repository.fechar( con, ps, rs );
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<DadosBoletoModel> listarAlunosPorTurma( DadosBoletoFiltroModel boletoFiltroDTO ) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = repository.getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append( "SELECT DISTINCT " );
            sb.append( "    dp.`CPF_PFisica`, " );
            sb.append( "    pf.`Nome_PFisica`, " );
            sb.append( "    pf.`Codigo_PFisica`, " );
            sb.append( "    tu.`Nome_Turma`, " );
            sb.append( "    m.`Codigo_Situacao_Aluno` " );
            sb.append( "FROM ((((((((`PFisicas` pf " );
            sb.append( "INNER JOIN `DocumentosPFisicas` dp ON dp.`Codigo_PFisica` = pf.`Codigo_PFisica`) " );
            sb.append( "INNER JOIN `Matriculas` m ON m.`Codigo_PFisica` = pf.`Codigo_PFisica`) " );
            sb.append( "INNER JOIN `PeriodosLetivos` pl ON pl.`Codigo_Periodo_Letivo` = m.`Codigo_Periodo_Letivo`) " );
            sb.append( "INNER JOIN `Cursos` c ON c.`Codigo_Curso` = m.`Codigo_Curso`) " );
            sb.append( "INNER JOIN `Series` s ON s.`Codigo_Serie` = m.`Codigo_Serie`) " );
            sb.append( "INNER JOIN `Turnos` t ON t.`Codigo_Turno` = m.`Codigo_Turno`) " );
            sb.append( "INNER JOIN `TurmasEscola` tu ON tu.`Codigo_Curso` = c.`Codigo_Curso` AND tu.`Codigo_Serie`= s.`Codigo_Serie` AND tu.`Codigo_Turno` = t.`Codigo_Turno`) " );
            sb.append( "LEFT  JOIN `Mensalidades` mens ON mens.`Codigo_Periodo_Letivo` = pl.`Codigo_Periodo_Letivo` )  " );
//            sb.append( "AND mens.`Codigo_Curso` = c.`Codigo_Curso`  " );
//            sb.append( "AND mens.`Codigo_Serie` = s.`Codigo_Serie`  " );
//            sb.append( "AND mens.`Codigo_PFisica` = pf.`Codigo_PFisica` ) " );
            sb.append( "WHERE 1 = 1  " );
            sb.append( "AND tu.`Nome_Turma` = ? " );
            sb.append( "AND YEAR(mens.`Data_Vencimento`) = ? " );
            sb.append( "AND MONTH(mens.`Data_Vencimento`) = ? " );
            sb.append( "AND m.`Codigo_Situacao_Aluno` = '01' " );
            sb.append( "ORDER BY pf.`Nome_PFisica`;" );

            ps = con.prepareStatement( sb.toString() );
            ps.setString( 1, boletoFiltroDTO.getTurma() );
            ps.setInt( 2, Integer.valueOf( boletoFiltroDTO.getAno() ) );
            ps.setInt( 3, boletoFiltroDTO.getMeses().indexOf( boletoFiltroDTO.getMes() ) + 1 );

            rs = ps.executeQuery();
            List<DadosBoletoModel> list = new ArrayList<>();
            while (rs.next()) {
                DadosBoletoModel dadosBoletoModel = new DadosBoletoModel();
                dadosBoletoModel.setCpf( rs.getString( "CPF_PFisica" ) );
                dadosBoletoModel.setAluno( rs.getString( "Nome_PFisica" ) );
                dadosBoletoModel.setMatricula( rs.getString( "Codigo_PFisica" ) );
                dadosBoletoModel.setTurma( rs.getString( "Nome_Turma" ) );
                dadosBoletoModel.setSituacaoMensalidade( "Codigo_Situacao_Mensalidade" );

                list.add( dadosBoletoModel );
            }
            return list;
        } catch ( SQLException ex ) {
            LOG.log( Level.SEVERE, null, ex );
        } finally {
            repository.fechar( con, ps, rs );
        }
        return Collections.EMPTY_LIST;
    }

    /**
     *
     * @param boletoPagoModels
     */
    @Override
    public void processarBaixaDeBoleto( DadosBoletoPagoModel boletoPagoModels ) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = repository.getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append( "UPDATE `Mensalidades` SET " );
            sb.append( "`Codigo_Situacao_Mensalidade` = '2', " );
            sb.append( "`Data_Pagamento` = ?, " );
            sb.append( "`Codigo_Forma_Pagamento` = '03', " );
            sb.append( "`Valor_Baixa` = ? " );
            sb.append( "WHERE `Codigo_PFisica` = ? " );
            sb.append( "AND `Nosso_Numero` = ? " );
            ps = con.prepareStatement( sb.toString() );
            ps.setDate( 1, new java.sql.Date( boletoPagoModels.getPagamento().getTime() ) );
            ps.setDouble( 2, boletoPagoModels.getValor() );
            ps.setString( 3, boletoPagoModels.getMatricula() );
            ps.setString( 4, boletoPagoModels.getNossoNumero() );
            int resultado = ps.executeUpdate();
            boletoPagoModels.setAtualizado( resultado != 0 );
        } catch ( SQLException ex ) {
            LOG.log( Level.SEVERE, null, ex );
        } finally {
            repository.fechar( con, ps );
        }
    }

    @Override
    public DadosBoletoModel retornarBoletoPago( DadosBoletoPagoModel boletoPagoModels ) {
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
            sb.append( "LEFT JOIN `EmailSitePFisicas` epf ON epf.`Codigo_PFisica` = pf.`Codigo_PFisica`) " );
            sb.append( "INNER JOIN `TurmasEscola` tu ON tu.`Codigo_Curso` = c.`Codigo_Curso` AND tu.`Codigo_Serie`= s.`Codigo_Serie` AND tu.`Codigo_Turno` = t.`Codigo_Turno`) " );
            sb.append( "INNER JOIN `Mensalidades` mens ON mens.`Codigo_Periodo_Letivo` = pl.`Codigo_Periodo_Letivo`  " );
            sb.append( "AND mens.`Codigo_Curso` = c.`Codigo_Curso`  " );
            sb.append( "AND mens.`Codigo_Serie` = s.`Codigo_Serie`  " );
            sb.append( "AND mens.`Codigo_PFisica` = pf.`Codigo_PFisica` ) " );
            sb.append( "INNER JOIN `TabelasMensalidades` tabmens ON tabmens.`Codigo_Periodo_Letivo` = pl.`Codigo_Periodo_Letivo` " );
            sb.append( "AND tabmens.`Codigo_Curso` = c.`Codigo_Curso` " );
            sb.append( "AND tabmens.`Codigo_Serie` = s.`Codigo_Serie` ) " );
            sb.append( "INNER JOIN `DocumentosPFisicas` dp ON dp.`Codigo_PFisica` = pf.`Codigo_PFisica`) " );
            sb.append( "LEFT JOIN `EnderecosPFisicas` ep ON ep.`Codigo_PFisica` = pf.`Codigo_PFisica` ) " );
            sb.append( "LEFT JOIN `Estados` est ON est.`Codigo_Estado` = ep.`Codigo_Estado`  ) " );
            sb.append( "LEFT JOIN `Cidades` cid ON cid.`Codigo_Cidade` = ep.`Codigo_Cidade` AND cid.`Codigo_Estado` = ep.`Codigo_Estado` ) " );
            sb.append( "LEFT JOIN `Bairros` bai ON bai.`Codigo_Bairro` = ep.`Codigo_Bairro` AND bai.`Codigo_Cidade` = ep.`Codigo_Cidade` AND bai.`Codigo_Estado` = ep.`Codigo_Estado` ) " );
            sb.append( "WHERE mens.`Codigo_Situacao_Mensalidade` = '2' " );
            sb.append( "AND mens.`Nosso_Numero` = ? " );
            ps = con.prepareStatement( sb.toString() );
            ps.setString( 1, boletoPagoModels.getMatricula() );
            ps.setString( 2, boletoPagoModels.getNossoNumero() );
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
                        ), rs.getString( MyStrings.removerAcentos( "Email_Site" ) ) ) );
            }
            return list.get( 0 );
        } catch ( Exception ex ) {
            throw new IllegalStateException( ex );
        } finally {
            repository.fechar( con, ps, rs );
        }
    }

    @Override
    public List<DadosBoletoModel> retornarBoletosPagos( int ano, int mes ) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = repository.getConnection();
            String sql = "SELECT DISTINCT TOP 100 dp.CPF_PFisica, \n"
                    + "            pf.`Nome_PFisica`, \n"
                    + "            epf.`Email_Site`, \n"
                    + "            pf.`Codigo_PFisica`, \n"
                    + "            mens.`Data_Vencimento`,\n"
                    + "            mens.`Data_Pagamento`,\n"
                    + "            mens.`Valor_Mensalidade`, \n"
                    + "            mens.`Nosso_Numero`, \n"
                    + "            mens.`Codigo_Situacao_Mensalidade`, \n"
                    + "            mens.`Numero_Mensalidade`,  \n"
                    + "            mens.`Percentual_Multa`,  \n"
                    + "            mens.`Percentual_Juros`  \n"
                    + "            FROM ((((`PFisicas` pf \n"
                    + "            INNER JOIN `Matriculas` m ON m.`Codigo_PFisica` = pf.`Codigo_PFisica`) \n"
                    + "            LEFT JOIN `EmailSitePFisicas` epf ON epf.`Codigo_PFisica` = pf.`Codigo_PFisica`) \n"
                    + "            INNER JOIN `Mensalidades` mens ON mens.`Codigo_PFisica` = pf.`Codigo_PFisica` ) \n"
                    + "            INNER JOIN `DocumentosPFisicas` dp ON dp.`Codigo_PFisica` = pf.`Codigo_PFisica`) \n"
                    + "            WHERE mens.`Codigo_Situacao_Mensalidade` = '2' \n"
                    + "            AND (mens.`Codigo_Forma_Pagamento` = '03' OR mens.`Codigo_Forma_Pagamento` = '01') \n"
                    + "            AND mens.`Nosso_Numero` <> '0000000000'\n"
                    + "            AND mens.`Data_Pagamento` IS NOT NULL\n"
                    + "            AND YEAR(mens.`Data_Vencimento`) = ?\n"
                    + "            AND MONTH(mens.`Data_Vencimento`) = ?\n"
                    + "            AND mens.`Nosso_Numero` NOT IN (SELECT `numero_boleto` FROM `nota_carioca`); ";
            ps = con.prepareStatement( sql );
            ps.setInt( 1, ano );
            ps.setInt( 2, mes );
            rs = ps.executeQuery();
            List<DadosBoletoModel> list = new ArrayList<>();
            while (rs.next()) {
                list.add( new DadosBoletoModel(
                        rs.getString( "CPF_PFisica" ),
                        rs.getString( "Codigo_PFisica" ),
                        rs.getString( "Nome_PFisica" ), 
                        null,
                        rs.getString( "Nosso_Numero" ),
                        rs.getDouble( "Valor_Mensalidade" ),
                        rs.getDate( "Data_Vencimento" ),
                        rs.getString( "Codigo_Situacao_Mensalidade" ),
                        rs.getString( "Numero_Mensalidade" ),
                        null,
                        rs.getDouble( "Percentual_Multa" ),
                        rs.getDouble( "Percentual_Juros" ), null, rs.getString( MyStrings.removerAcentos( "Email_Site" ) ) ) );
            }
            return list;
        } catch ( Exception ex ) {
            throw new IllegalStateException( ex );
        } finally {
            repository.fechar( con, ps, rs );
        }
    }

    @Override
    public List<DadosBoletoModel> retornarBoletosPagos( Date data ) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = repository.getConnection();
            String sql = "SELECT DISTINCT TOP 100 dp.CPF_PFisica, \n"
                    + "            pf.`Nome_PFisica`, \n"
                    + "            epf.`Email_Site`, \n"
                    + "            pf.`Codigo_PFisica`, \n"
                    + "            mens.`Data_Vencimento`,\n"
                    + "            mens.`Data_Pagamento`,\n"
                    + "            mens.`Valor_Mensalidade`, \n"
                    + "            mens.`Nosso_Numero`, \n"
                    + "            mens.`Codigo_Situacao_Mensalidade`, \n"
                    + "            mens.`Numero_Mensalidade`,  \n"
                    + "            mens.`Percentual_Multa`,  \n"
                    + "            mens.`Percentual_Juros`  \n"
                    + "            FROM ((((`PFisicas` pf \n"
                    + "            INNER JOIN `Matriculas` m ON m.`Codigo_PFisica` = pf.`Codigo_PFisica`) \n"
                    + "            LEFT JOIN `EmailSitePFisicas` epf ON epf.`Codigo_PFisica` = pf.`Codigo_PFisica`) \n"
                    + "            INNER JOIN `Mensalidades` mens ON mens.`Codigo_PFisica` = pf.`Codigo_PFisica` ) \n"
                    + "            INNER JOIN `DocumentosPFisicas` dp ON dp.`Codigo_PFisica` = pf.`Codigo_PFisica`) \n"
                    + "            WHERE mens.`Codigo_Situacao_Mensalidade` = '2' \n"
                    + "            AND (mens.`Codigo_Forma_Pagamento` = '03' OR mens.`Codigo_Forma_Pagamento` = '01') \n"
                    + "            AND mens.`Nosso_Numero` <> '0000000000'\n"
                    + "            AND mens.`Data_Pagamento` IS NOT NULL\n"
                    + "            AND mens.`Data_Pagamento` = ?\n"
                    + "            AND mens.`Nosso_Numero` NOT IN (SELECT `numero_boleto` FROM `nota_carioca`); ";
            ps = con.prepareStatement( sql );
            ps.setDate( 1, new java.sql.Date( data.getTime()) );
            rs = ps.executeQuery();
            List<DadosBoletoModel> list = new ArrayList<>();
            while (rs.next()) {
                list.add( new DadosBoletoModel(
                        rs.getString( "CPF_PFisica" ),
                        rs.getString( "Codigo_PFisica" ),
                        rs.getString( "Nome_PFisica" ), 
                        null,
                        rs.getString( "Nosso_Numero" ),
                        rs.getDouble( "Valor_Mensalidade" ),
                        rs.getDate( "Data_Vencimento" ),
                        rs.getString( "Codigo_Situacao_Mensalidade" ),
                        rs.getString( "Numero_Mensalidade" ),
                        null,
                        rs.getDouble( "Percentual_Multa" ),
                        rs.getDouble( "Percentual_Juros" ), null, rs.getString( MyStrings.removerAcentos( "Email_Site" ) ) ) );
            }
            return list;
        } catch ( Exception ex ) {
            throw new IllegalStateException( ex );
        } finally {
            repository.fechar( con, ps, rs );
        }
    }

}
