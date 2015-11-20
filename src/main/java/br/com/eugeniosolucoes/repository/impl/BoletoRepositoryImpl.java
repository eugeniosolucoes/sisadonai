/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.repository.impl;

import br.com.eugeniosolucoes.view.model.BoletoModel;
import br.com.eugeniosolucoes.view.model.BoletoFiltroModel;
import br.com.eugeniosolucoes.repository.BoletoRepository;
import br.com.eugeniosolucoes.util.MyStrings;
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

    static final Logger LOG = Logger.getLogger(BoletoRepositoryImpl.class.getName());

    static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));

    AbstractRepository repository = AbstractRepository.getInstance();

    @Override
    public List<String> listarAnos() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = repository.getConnection();
            ps = con.prepareStatement("SELECT DISTINCT YEAR(m.`Data_Vencimento`) AS ANO FROM `Mensalidades` m ");
            rs = ps.executeQuery();
            List<String> list = new ArrayList<>();
            while (rs.next()) {
                list.add(rs.getString("ANO"));
            }
            return list;
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } finally {
            repository.fechar(con, ps, rs);
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
            ps = con.prepareStatement("SELECT DISTINCT t.`Nome_Turma` FROM `TurmasEscola` t ORDER BY t.`Nome_Turma`");
            rs = ps.executeQuery();
            List<String> list = new ArrayList<>();
            while (rs.next()) {
                list.add(rs.getString("Nome_Turma"));
            }
            return list;
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } finally {
            repository.fechar(con, ps, rs);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<BoletoModel> listarBoletos(BoletoFiltroModel boletoFiltroDTO) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = repository.getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append( "SELECT DISTINCT " );
            sb.append( "pf.`Nome_PFisica`, ");
            sb.append( "pf.`Codigo_PFisica`, ");
            sb.append( "tu.`Nome_Turma`, ");
            sb.append( "mens.`Data_Vencimento`, ");
            sb.append( "mens.`Valor_Mensalidade`, ");
            sb.append( "mens.`Nosso_Numero` ");
            sb.append( "FROM (((((((`PFisicas` pf ");
            sb.append( "INNER JOIN `Matriculas` m ON m.`Codigo_PFisica` = pf.`Codigo_PFisica`) ");
            sb.append( "INNER JOIN `PeriodosLetivos` pl ON pl.`Codigo_Periodo_Letivo` = m.`Codigo_Periodo_Letivo`) ");
            sb.append( "INNER JOIN `Cursos` c ON c.`Codigo_Curso` = m.`Codigo_Curso`) ");
            sb.append( "INNER JOIN `Series` s ON s.`Codigo_Serie` = m.`Codigo_Serie`) ");
            sb.append( "INNER JOIN `Turnos` t ON t.`Codigo_Turno` = m.`Codigo_Turno`) ");
            sb.append( "INNER JOIN `TurmasEscola` tu ON tu.`Codigo_Curso` = c.`Codigo_Curso` AND tu.`Codigo_Serie`= s.`Codigo_Serie` AND tu.`Codigo_Turno` = t.`Codigo_Turno`) ");
            sb.append( "INNER JOIN `Mensalidades` mens ON mens.`Codigo_Periodo_Letivo` = pl.`Codigo_Periodo_Letivo`  ");
            sb.append( "AND mens.`Codigo_Curso` = c.`Codigo_Curso`  ");
            sb.append( "AND mens.`Codigo_Serie` = s.`Codigo_Serie`  ");
            sb.append( "AND mens.`Codigo_PFisica` = pf.`Codigo_PFisica` ) ");
            sb.append( "WHERE 1 = 1 ");
            sb.append( "AND pl.`Codigo_Periodo_Letivo` = ? ");
            sb.append( "AND MONTH(mens.`Data_Vencimento`) = ? ");
            sb.append( "AND tu.`Nome_Turma` = ? ");
            if (!MyStrings.isNullOrEmpty(boletoFiltroDTO.getMatriculaAluno())) {
                sb.append("AND ( m.Codigo_PFisica LIKE ? OR pf.Nome_PFisica LIKE ? ) ");
            }
            sb.append( "AND m.`Codigo_Situacao_Aluno` = '01' ");
            sb.append( "ORDER BY pf.`Nome_PFisica`; ");
            ps = con.prepareStatement(sb.toString());
            ps.setString(1, boletoFiltroDTO.getAno());
            ps.setInt(2, boletoFiltroDTO.getMeses().indexOf(boletoFiltroDTO.getMes()) + 1);
            ps.setString(3, boletoFiltroDTO.getTurma());
            if (!MyStrings.isNullOrEmpty(boletoFiltroDTO.getMatriculaAluno())) {
                ps.setString(4, String.format("%s", "%" + boletoFiltroDTO.getMatriculaAluno().trim().toUpperCase() + "%"));
                ps.setString(5, String.format("%s", "%" + boletoFiltroDTO.getMatriculaAluno().trim().toUpperCase() + "%"));
            }
            rs = ps.executeQuery();
            List<BoletoModel> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new BoletoModel(
                        rs.getString("Codigo_PFisica"),
                        rs.getString("Nome_PFisica"), rs.getString("Nome_Turma"),
                        rs.getString("Nosso_Numero"),
                        String.format("R$ %.2f", rs.getDouble("Valor_Mensalidade")), 
                        DATE_FORMAT.format(rs.getDate("Data_Vencimento"))));
            }
            return list;
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } finally {
            repository.fechar(con, ps, rs);
        }
        return Collections.EMPTY_LIST;
    }

}
