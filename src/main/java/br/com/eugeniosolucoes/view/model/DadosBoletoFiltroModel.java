/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.view.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author eugenio
 */
public class DadosBoletoFiltroModel {

    private String ano;

    private String mes;

    private String turma;

    private String matriculaAluno;

    private List<String> anos;

    private final List<String> meses = Arrays.asList(
            "Janeiro",
            "Fevereiro",
            "Março",
            "Abril",
            "Maio",
            "Junho",
            "Julho",
            "Agosto",
            "Setembro",
            "Outubro",
            "Novembro",
            "Dezembro");

    private List<String> turmas;

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public String getMatriculaAluno() {
        return matriculaAluno;
    }

    public void setMatriculaAluno(String matriculaAluno) {
        this.matriculaAluno = matriculaAluno;
    }

    public void setAnos(List<String> anos) {
        this.anos = anos;
    }

    public List<String> getAnos() {
        if (anos == null) {
            anos = new ArrayList<>();
        }
        return anos;
    }

    public List<String> getMeses() {
        return meses;
    }

    public List<String> getTurmas() {
        if (turmas == null) {
            turmas = new ArrayList<>();
        }
        return turmas;
    }

    public void setTurmas(List<String> turmas) {
        this.turmas = turmas;
    }

}
