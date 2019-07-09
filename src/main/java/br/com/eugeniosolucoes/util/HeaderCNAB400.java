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
public class HeaderCNAB400 extends AbstractCNAB400 {

    static final List<Campo> CAMPOS_HEADER = new ArrayList<>(16);

    private static int contador = 1;

    static {
        CAMPOS_HEADER.add( addCampo( "TIPO_DE_REGISTRO", N, 1, "0" ) );
        CAMPOS_HEADER.add( addCampo( "OPERACAO", N, 1, "1" ) );
        CAMPOS_HEADER.add( addCampo( "LITERAL_DE_REMESSA", S, 7, "REMESSA" ) );
        CAMPOS_HEADER.add( addCampo( "CODIGO_DO_SERVICO", N, 2, "01" ) );
        CAMPOS_HEADER.add( addCampo( "LITERAL_DE_SERVICO", S, 15, "COBRANCA" ) );
        CAMPOS_HEADER.add( addCampo( "AGENCIA", N, 4, "0" ) );
        CAMPOS_HEADER.add( addCampo( "ZEROS", N, 2, "0" ) );
        CAMPOS_HEADER.add( addCampo( "CONTA", N, 5, "0" ) );
        CAMPOS_HEADER.add( addCampo( "DAC", N, 1, "0" ) );
        CAMPOS_HEADER.add( addCampo( "BRANCOS", S, 8, " " ) );
        CAMPOS_HEADER.add( addCampo( "NOME_DA_EMPRESA", S, 30, "EMPRESA EXEMPLO" ) );
        CAMPOS_HEADER.add( addCampo( "CODIGO_DO_BANCO", N, 3, "341" ) );
        CAMPOS_HEADER.add( addCampo( "NOME_DO_BANCO", S, 15, "BANCO ITAU SA" ) );
        CAMPOS_HEADER.add( addCampo( "DATA_DE_GERACAO", N, 6, "0" ) );
        CAMPOS_HEADER.add( addCampo( "BRANCOS_1", S, 294, " " ) );
        CAMPOS_HEADER.add( addCampo( "NUMERO_SEQUENCIAL", N, 6, "0" ) );
    }

    public String getLinhaHeader() throws SizeLineException {
        return this.getLinha( CAMPOS_HEADER );
    }
    
    private static Campo addCampo( String nome, Picture.Type type, int length, String valorPadrao ) {
        Campo campo = new Campo( nome, contador, new Picture( type, length ), valorPadrao );
        contador += length;
        return campo;
    }    
    
    @Override
    public Campo getCampo( String nome ) {
        return recuperarCampo( CAMPOS_HEADER, nome );
    }

}
