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
public class HeaderCNAB400 extends AbstractCNAB400 {

    static final List<Campo> CAMPOS_HEADER = new ArrayList<>(16);

    private static int contador = 1;
    
    static {
        CAMPOS_HEADER.add( new Campo( "TIPO_DE_REGISTRO", new Posicao( 1, 1 ), new Picture( Picture.Type.N, 1, false ), "0" ) );
        CAMPOS_HEADER.add( new Campo( "OPERACAO", new Posicao( 2, 2 ), new Picture( Picture.Type.N, 1, false ), "1" ) );
        CAMPOS_HEADER.add( new Campo( "LITERAL_DE_REMESSA", new Posicao( 3, 9 ), new Picture( Picture.Type.S, 7, false ), "REMESSA" ) );
        CAMPOS_HEADER.add( new Campo( "CODIGO_DO_SERVICO", new Posicao( 10, 11 ), new Picture( Picture.Type.N, 2, false ), "01" ) );
        CAMPOS_HEADER.add( new Campo( "LITERAL_DE_SERVICO", new Posicao( 12, 26 ), new Picture( Picture.Type.S, 15, false ), "COBRANCA" ) );
        CAMPOS_HEADER.add( new Campo( "AGENCIA", new Posicao( 27, 30 ), new Picture( Picture.Type.N, 4, false ), "0" ) );
        CAMPOS_HEADER.add( new Campo( "ZEROS", new Posicao( 31, 32 ), new Picture( Picture.Type.N, 2, false ), "0" ) );
        CAMPOS_HEADER.add( new Campo( "CONTA", new Posicao( 33, 37 ), new Picture( Picture.Type.N, 5, false ), "0" ) );
        CAMPOS_HEADER.add( new Campo( "DAC", new Posicao( 38, 38 ), new Picture( Picture.Type.N, 1, false ), "0" ) );
        CAMPOS_HEADER.add( new Campo( "BRANCOS", new Posicao( 39, 46 ), new Picture( Picture.Type.S, 8, false ), " " ) );
        CAMPOS_HEADER.add( new Campo( "NOME_DA_EMPRESA", new Posicao( 47, 76 ), new Picture( Picture.Type.S, 30, false ), "EMPRESA EXEMPLO" ) );
        CAMPOS_HEADER.add( new Campo( "CODIGO_DO_BANCO", new Posicao( 77, 79 ), new Picture( Picture.Type.N, 3, false ), "341" ) );
        CAMPOS_HEADER.add( new Campo( "NOME_DO_BANCO", new Posicao( 80, 94 ), new Picture( Picture.Type.S, 15, false ), "BANCO ITAU SA" ) );
        CAMPOS_HEADER.add( new Campo( "DATA_DE_GERACAO", new Posicao( 95, 100 ), new Picture( Picture.Type.N, 6, false ), "0" ) );
        CAMPOS_HEADER.add( new Campo( "BRANCOS_1", new Posicao( 101, 394 ), new Picture( Picture.Type.S, 294, false ), " " ) );
        CAMPOS_HEADER.add( new Campo( "NUMERO_SEQUENCIAL", new Posicao( 395, 400 ), new Picture( Picture.Type.N, 6, false ), "0" ) );
    }

    public String getLinhaHeader() throws SizeLineException {
        return this.getLinha( CAMPOS_HEADER );
    }
    
    @Override
    public Campo getCampo( String nome ) {
        return recuperarCampo( CAMPOS_HEADER, nome );
    }

}
