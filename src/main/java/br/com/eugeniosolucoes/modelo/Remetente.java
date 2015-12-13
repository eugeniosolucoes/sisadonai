/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alexandre
 */
public class Remetente extends UsuarioDeEmail {

    private static final long serialVersionUID = 1L;

    private String servidor;

    private String usuario;

    private String senha;

    private List<Destinatario> destinatarios;

    public Remetente() {
        email = new Email();
        destinatarios = new ArrayList<>();
    }

    public Remetente( String nome ) {
        this();
        this.nome = nome;
    }

    public Remetente( String nome, Email email ) {
        this( nome );
        this.email = email;
    }

    @Override
    public Email getEmail() {
        return email;
    }

    @Override
    public void setEmail( Email email ) {
        this.email = email;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public void setNome( String nome ) {
        this.nome = nome;
    }

    public List<Destinatario> getDestinatarios() {
        if ( destinatarios == null ) {
            destinatarios = new ArrayList<>();
        }
        return destinatarios;
    }

    public void setDestinatarios( List<Destinatario> destinatarios ) {
        this.destinatarios = destinatarios;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha( String senha ) {
        this.senha = senha;
    }

    public String getServidor() {
        return servidor;
    }

    public void setServidor( String servidor ) {
        this.servidor = servidor;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario( String usuario ) {
        this.usuario = usuario;
    }

}
