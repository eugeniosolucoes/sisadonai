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

    private String curso;

    private String valor;

    private String vencimento;

    public BoletoModel() {
    }

    public BoletoModel( String matricula, String aluno, String curso, String valor, String vencimento ) {
        this.matricula = matricula;
        this.aluno = aluno;
        this.curso = curso;
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

    public String getCurso() {
        return curso;
    }

    public void setCurso( String curso ) {
        this.curso = curso;
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
