/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.modelo;

/**
 *
 * @author alexandre
 */
public class Destinatario extends UsuarioDeEmail {

    private static final long serialVersionUID = 1L;    
    
    public Destinatario() {
        email = new Email();
    }

    public Destinatario( String nome ) {
        this();
        this.nome = nome;
    }

    public Destinatario( String nome, Email email ) {
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
}
