/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.controle;

import br.com.eugeniosolucoes.excecoes.ActionException;

/**
 *
 * @author alexandre
 */
public class Controlador {

    private Object[] params;

    public void processar( String cmd, Object... params ) throws ActionException {
        MapeadorComando mapeador = new MapeadorComando( cmd, params );
        mapeador.processaComando();
        this.params = params;
    }

    public Object[] getParametros() {
        return this.params;
    }

    public Object getParametro( int index ) {
        return this.params[index];
    }
}
