/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.util;

import java.util.Objects;

/**
 *
 * @author eugenio
 */
public class Campo {

    private final String nome;

    private final Posicao posicao;

    private final Picture picture;

    private final String valorPadrao;

    private String valor;

    public Campo( String nome, Posicao posicao, Picture picture, String valorPadrao ) {
        this.nome = nome;
        this.posicao = posicao;
        this.picture = picture;
        this.valorPadrao = valorPadrao;
    }

    public Campo( String nome, int x, Picture picture, String valorPadrao ) {
        this.nome = nome;
        this.picture = picture;
        this.posicao = new Posicao( x, x + picture.getLength() );
        this.valorPadrao = valorPadrao;
    }

    public Campo( String nome, int x, Picture picture ) {
        this.nome = nome;
        this.picture = picture;
        this.posicao = new Posicao( x, x + picture.getLength() );
        this.valorPadrao = Picture.Type.N.equals( picture.getType() ) ? "0" : " ";
    }

    public String getNome() {
        return nome;
    }

    public Posicao getPosicao() {
        return posicao;
    }

    public Picture getPicture() {
        return picture;
    }

    public String getValorPadrao() {
        return valorPadrao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor( String valor ) {
        this.valor = valor;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode( this.nome );
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
        final Campo other = (Campo) obj;
        return Objects.equals( this.nome, other.nome );
    }

    @Override
    public String toString() {
        return "Campo{" + "nome=" + nome + ", posicao=" + posicao + ", picture=" + picture + ", valor=" + valor + '}';
    }

}
