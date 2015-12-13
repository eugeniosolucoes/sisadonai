/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.controle;

/**
 *
 * @author evaldo
 */
public class ActionComand {

    private final String comando;

    private final Object[] parametros;

    protected String SUCESS;

    protected String ERRO;

    public ActionComand( String comando, Object... parametros ) {
        this.comando = comando;
        this.parametros = parametros;
    }

    public String getComando() {
        return comando;
    }

    public Object[] getParametros() {
        return parametros;
    }

    public Object getParametro( int index ) {
        return parametros[index];
    }

    public void setParametro( Object o, int index ) {
        this.parametros[index] = o;
    }

}
