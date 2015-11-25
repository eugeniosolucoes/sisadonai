/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.view.model;

import java.util.Objects;

/**
 *
 * @author eugenio
 */
public class BoletoModel {

    private String cpf;

    private String matricula;

    private String aluno;

    private String turma;

    private String nossoNumero;

    private String valor;

    private String vencimento;

    private String situacaoMensalidade;

    private String numeroMensalidade;

    public BoletoModel() {
    }

    public BoletoModel(String cpf, String matricula, String aluno, String turma,
            String nossoNumero, String valor, String vencimento,
            String situacaoMensalidade, String numeroMensalidade) {
        this.cpf = cpf;
        this.matricula = matricula;
        this.aluno = aluno;
        this.turma = turma;
        this.nossoNumero = nossoNumero;
        this.valor = valor;
        this.vencimento = vencimento;
        this.situacaoMensalidade = situacaoMensalidade;
        this.numeroMensalidade = numeroMensalidade;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getAluno() {
        return aluno;
    }

    public void setAluno(String aluno) {
        this.aluno = aluno;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public String getNossoNumero() {
        return nossoNumero;
    }

    public void setNossoNumero(String nossoNumero) {
        this.nossoNumero = nossoNumero;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getVencimento() {
        return vencimento;
    }

    public void setVencimento(String vencimento) {
        this.vencimento = vencimento;
    }

    public String getSituacaoMensalidade() {
        return situacaoMensalidade;
    }

    public void setSituacaoMensalidade(String situacaoMensalidade) {
        this.situacaoMensalidade = situacaoMensalidade;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNumeroMensalidade() {
        return numeroMensalidade;
    }

    public void setNumeroMensalidade(String numeroMensalidade) {
        this.numeroMensalidade = numeroMensalidade;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.cpf);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BoletoModel other = (BoletoModel) obj;
        return Objects.equals(this.cpf, other.cpf);
    }

}
