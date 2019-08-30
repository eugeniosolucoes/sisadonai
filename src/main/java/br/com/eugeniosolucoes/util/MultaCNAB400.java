/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.util;

import java.util.ArrayList;
import java.util.List;
import static br.com.eugeniosolucoes.util.Picture.Type.*;

/**
 *
 * @author eugenio
 */
public class MultaCNAB400 extends AbstractCNAB400 {

    static final List<Campo> CAMPOS_DETALHE = new ArrayList<>(6);

    private static int contador = 1;

    static {
        CAMPOS_DETALHE.add( addCampo( "TIPO_DE_REGISTRO", N, 1 ) );
        CAMPOS_DETALHE.add( addCampo( "CODIGO_MULTA", N, 1 ) );
        CAMPOS_DETALHE.add( addCampo( "DATA_MULTA", N, 8 ) );
        CAMPOS_DETALHE.add( addCampo( "MULTA", N, 13 ) );
        CAMPOS_DETALHE.add( addCampo( "BRANCOS", S, 371 ) );
        CAMPOS_DETALHE.add( addCampo( "NUMERO_SEQUENCIAL", N, 6 ) );
    }
    private static Campo addCampo( String nome, Picture.Type type, int length ) {
        Campo campo = new Campo( nome, contador, new Picture( type, length ) );
        contador += length;
        return campo;
    }

    public String getLinhaDetalhe() throws SizeLineException {
        return this.getLinha( CAMPOS_DETALHE );
    }
    @Override
    public Campo getCampo( String nome ) {
        return recuperarCampo( CAMPOS_DETALHE, nome );
    }

}
