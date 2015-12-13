/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.modelo;

import java.io.Serializable;

/**
 *
 * @author alexandre
 */
public abstract class UsuarioDeEmail implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String nome;

    protected Email email;

    public abstract Email getEmail();

    public abstract String getNome();

    public abstract void setEmail( Email email );

    public abstract void setNome( String nome );
}
