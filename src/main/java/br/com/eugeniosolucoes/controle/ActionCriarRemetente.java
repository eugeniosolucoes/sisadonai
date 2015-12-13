/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.controle;

import br.com.eugeniosolucoes.modelo.Remetente;

/**
 *
 * @author alexandre
 */
public class ActionCriarRemetente extends ActionComand implements IAction {

    public ActionCriarRemetente( String comando, Object... parametros ) {
        super( comando, parametros );
    }

    @Override
    public void execute() {
        Remetente obj = (Remetente) this.getParametro( 0 );
    }

}
