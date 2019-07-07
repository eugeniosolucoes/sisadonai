/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author eugenio
 */
public class TrailerCNAB400 extends AbstractCNAB400 {

    static final List<Campo> CAMPOS_TRAILER = new ArrayList<>();

    private static int contador = 1;

    static {
        CAMPOS_TRAILER.add( new Campo( "TIPO_DE_REGISTRO", new Posicao( 1, 1 ), new Picture( Picture.Type.N, 1, false ), "9" ) );
        CAMPOS_TRAILER.add( addCampo( "BRANCOS", Picture.Type.S, 393 ) );
        CAMPOS_TRAILER.add( addCampo( "NUMERO_SEQUENCIAL", Picture.Type.N, 6 ) );
    }

    public String getLinhaTrailer() throws SizeLineException {
        return this.getLinha( CAMPOS_TRAILER );
    }

    public static Campo addCampo( String nome, Picture.Type type, int length ) {
        Campo campo = new Campo( nome, contador, new Picture( type, length ) );
        contador += length;
        return campo;
    }

    @Override
    public Campo getCampo( String nome ) {
        return recuperarCampo( CAMPOS_TRAILER, nome );
    }

}
