/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.view.model;

/**
 *
 * @author eugenio
 */
public class BoletoModel {

    private String matricula;

    private String aluno;

    private String turma;
    
    private String nossoNumero;

    private String valor;

    private String vencimento;

    public BoletoModel() {
    }

    public BoletoModel( String matricula, String aluno, String turma, String nossoNumero, String valor, String vencimento ) {
        this.matricula = matricula;
        this.aluno = aluno;
        this.turma = turma;
        this.nossoNumero = nossoNumero;
        this.valor = valor;
        this.vencimento = vencimento;
    }
    
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula( String matricula ) {
        this.matricula = matricula;
    }

    public String getAluno() {
        return aluno;
    }

    public void setAluno( String aluno ) {
        this.aluno = aluno;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma( String turma ) {
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

    public void setValor( String valor ) {
        this.valor = valor;
    }

    public String getVencimento() {
        return vencimento;
    }

    public void setVencimento( String vencimento ) {
        this.vencimento = vencimento;
    }

}
