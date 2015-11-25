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
public class EnderecoModel {

    private String logradouro;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    public EnderecoModel() {
    }

    public EnderecoModel(String logradouro, String complemento, String bairro, String cidade, String estado, String cep) {
        this.logradouro = logradouro;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }
    
    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplemento() {
        return complemento == null ? "" : complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro == null ? "" : bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade == null ? "" : cidade.toUpperCase();
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado == null ? "" : estado.toUpperCase();
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep == null ? "" : cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

}
