/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.controle;

import br.com.eugeniosolucoes.excecoes.ActionException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author evaldo
 */
public class MapeadorComando {

    private final Map<String, IAction> mapeadorComando = new HashMap<>();

    private final String comando;

    public MapeadorComando( String comando, Object... parametros ) {
        this.comando = comando;

        mapeadorComando.put( "CMD_SALVAR_REMETENTE", new ActionSalvarRemetente( comando, parametros ) );
        mapeadorComando.put( "CMD_RETORNAR_REMETENTE", new ActionRetornarRemetente( comando, parametros ) );
        mapeadorComando.put( "CMD_ENVIAR_EMAIL", new ActionEnviarEmail( comando, parametros ) );

    }

    public void processaComando() throws ActionException {

        IAction cmd = null;

        if ( comando != null ) {
            Object o = mapeadorComando.get( comando );
            if ( o != null ) {
                cmd = (IAction) o;
                cmd.execute();
            } else {
                throw new ActionException( "Comando de ação inválido!" );
            }
        }
    }

}
