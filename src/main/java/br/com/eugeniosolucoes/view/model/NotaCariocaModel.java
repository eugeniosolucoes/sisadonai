/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.view.model;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author eugenio
 */
public class NotaCariocaModel {

    private String numeroBoleto;
    
    private String nome;
    
    private String dataPagamento;
    
    private String total;

    private int numeroRps;

    private int numeroLoteRps;

    private Date dataEmissao;

    private String protocolo;

    private boolean processado;
    
    public NotaCariocaModel() {
    }

    public NotaCariocaModel( String numeroBoleto, int numeroRps, int numeroLoteRps, Date dataEmissao, String protocolo, boolean processado ) {
        this.numeroBoleto = numeroBoleto;
        this.numeroRps = numeroRps;
        this.numeroLoteRps = numeroLoteRps;
        this.dataEmissao = dataEmissao;
        this.protocolo = protocolo;
        this.processado = processado;
    }
    
    public String getNumeroBoleto() {
        return numeroBoleto;
    }

    public void setNumeroBoleto( String numeroBoleto ) {
        this.numeroBoleto = numeroBoleto;
    }

    public int getNumeroRps() {
        return numeroRps;
    }

    public void setNumeroRps( int numeroRps ) {
        this.numeroRps = numeroRps;
    }

    public int getNumeroLoteRps() {
        return numeroLoteRps;
    }

    public void setNumeroLoteRps( int numeroLoteRps ) {
        this.numeroLoteRps = numeroLoteRps;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao( Date dataEmissao ) {
        this.dataEmissao = dataEmissao;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo( String protocolo ) {
        this.protocolo = protocolo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome( String nome ) {
        this.nome = nome;
    }

    public String getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento( String dataPagamento ) {
        this.dataPagamento = dataPagamento;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal( String total ) {
        this.total = total;
    }

    public boolean isProcessado() {
        return processado;
    }

    public void setProcessado( boolean processado ) {
        this.processado = processado;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.numeroBoleto );
        return hash;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final NotaCariocaModel other = (NotaCariocaModel) obj;
        return Objects.equals(this.numeroBoleto, other.numeroBoleto );
    }

    @Override
    public String toString() {
        return "NotaCariocaModel{" + "numeroBoleto=" + numeroBoleto + ", nome=" + nome + ", dataPagamento=" + dataPagamento + ", total=" + total + ", numeroRps=" + numeroRps + ", numeroLoteRps=" + numeroLoteRps + ", dataEmissao=" + dataEmissao + ", protocolo=" + protocolo + ", processado=" + processado + '}';
    }
    
}
