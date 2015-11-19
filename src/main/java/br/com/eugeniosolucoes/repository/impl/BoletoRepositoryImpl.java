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
    public List<String> listarCursos() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = repository.getConnection();
            ps = con.prepareStatement("SELECT DISTINCT c.`Nome_Curso` FROM `Cursos` c ORDER BY c.`Nome_Curso`");
            rs = ps.executeQuery();
            List<String> list = new ArrayList<>();
            while (rs.next()) {
                list.add(rs.getString("Nome_Curso"));
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
            sb.append("SELECT DISTINCT Matriculas.Codigo_PFisica, PFisicas.Nome_PFisica, Cursos.Nome_Curso, Mensalidades.Valor_Mensalidade, Mensalidades.Data_Vencimento ");
            sb.append("FROM (((PFisicas INNER JOIN Matriculas ON PFisicas.Codigo_PFisica = Matriculas.Codigo_PFisica) INNER JOIN Mensalidades ON PFisicas.Codigo_PFisica = Mensalidades.Codigo_PFisica) INNER JOIN Cursos ON Matriculas.Codigo_Curso = Cursos.Codigo_Curso) INNER JOIN CursosPeriodoLetivo ON Cursos.Codigo_Curso = CursosPeriodoLetivo.Codigo_Curso ");
            sb.append("WHERE YEAR(Mensalidades.Data_Vencimento) = ? AND MONTH(Mensalidades.Data_Vencimento) = ? ");
            sb.append("AND Cursos.Nome_Curso = ? ");
            if (!MyStrings.isNullOrEmpty(boletoFiltroDTO.getMatriculaAluno())) {
                sb.append("AND ( Matriculas.Codigo_PFisica LIKE ? OR PFisicas.Nome_PFisica LIKE ? ) ");
            }
            sb.append("ORDER BY Cursos.Nome_Curso, Matriculas.Codigo_PFisica;");
            ps = con.prepareStatement(sb.toString());
            ps.setString(1, boletoFiltroDTO.getAno());
            ps.setInt(2, boletoFiltroDTO.getMeses().indexOf(boletoFiltroDTO.getMes()) + 1);
            ps.setString(3, boletoFiltroDTO.getCurso());
            if (!MyStrings.isNullOrEmpty(boletoFiltroDTO.getMatriculaAluno())) {
                ps.setString(4, String.format( "%s", "%" + boletoFiltroDTO.getMatriculaAluno().trim().toUpperCase() + "%" ));
                ps.setString(5, String.format( "%s", "%" + boletoFiltroDTO.getMatriculaAluno().trim().toUpperCase() + "%"));
            }
            rs = ps.executeQuery();
            List<BoletoModel> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new BoletoModel(rs.getString("Codigo_PFisica"),
                        rs.getString("Nome_PFisica"), rs.getString("Nome_Curso"),
                        String.format("R$ %.2f", rs.getDouble("Valor_Mensalidade")), DATE_FORMAT.format(rs.getDate("Data_Vencimento"))));
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
