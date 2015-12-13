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
public class Email implements Serializable {

    private String endereco;

    private String assunto;

    private String mensagem;

    private transient Anexo anexo;

    public Email() {
        anexo = new Anexo();
    }

    public Email( String endereco ) {
        this();
        this.endereco = endereco;
    }

    public Email( String endereco, String assunto ) {
        this( endereco );
        this.assunto = assunto;
    }

    public Email( String endereco, String assunto, String mensagem ) {
        this( endereco, assunto );
        this.mensagem = mensagem;
    }

    public Email( String endereco, String assunto, String mensagem, Anexo anexo ) {
        this( endereco, assunto, mensagem );
        this.anexo = anexo;
    }

    public Anexo getAnexo() {
        return anexo;
    }

    public void setAnexo( Anexo anexo ) {
        this.anexo = anexo;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto( String assunto ) {
        this.assunto = assunto;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco( String endereco ) {
        this.endereco = endereco;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem( String mensagem ) {
        this.mensagem = mensagem;
    }
}
