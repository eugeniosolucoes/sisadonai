/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.util;

import static br.com.eugeniosolucoes.util.Picture.Type.*;
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
        CAMPOS_TRAILER.add( addCampo( "TIPO_DE_REGISTRO", N, 1, "9" ) );
        CAMPOS_TRAILER.add( addCampo( "BRANCOS", S, 393, " " ) );
        CAMPOS_TRAILER.add( addCampo( "NUMERO_SEQUENCIAL", N, 6, "0" ) );
    }

    public String getLinhaTrailer() throws SizeLineException {
        return this.getLinha( CAMPOS_TRAILER );
    }

    private static Campo addCampo( String nome, Picture.Type type, int length, String valorPadrao ) {
        Campo campo = new Campo( nome, contador, new Picture( type, length ), valorPadrao );
        contador += length;
        return campo;
    }    

    @Override
    public Campo getCampo( String nome ) {
        return recuperarCampo( CAMPOS_TRAILER, nome );
    }

}
