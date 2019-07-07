/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.util;

/**
 *
 * @author eugenio
 */
public enum TipoCampo {
    TIPO_DE_REGISTRO( new Posicao( 1, 1 ), new Picture( Picture.Type.N, 1, false ), "0" ), 
    OPERACAO( new Posicao( 2, 2 ), new Picture( Picture.Type.N, 1, false ), "1" ), 
    LITERAL_DE_REMESSAOPERACAO( new Posicao( 3, 9 ), new Picture( Picture.Type.S, 7, false ), "REMESSA" ), 
    CODIGO_DO_SERVICO( new Posicao( 10, 11 ), new Picture( Picture.Type.N, 2, false ), "01" ), 
    LITERAL_DE_SERVICO( new Posicao( 12, 26 ), new Picture( Picture.Type.S, 15, false ), "COBRANCA" ), 
    AGENCIA( new Posicao( 27, 30 ), new Picture( Picture.Type.N, 4, false ), "0" ), 
    ZEROS( new Posicao( 31, 32 ), new Picture( Picture.Type.N, 2, false ), "0" ), 
    CONTA( new Posicao( 33, 37 ), new Picture( Picture.Type.N, 5, false ), "0" ), 
    DAC( new Posicao( 38, 38 ), new Picture( Picture.Type.N, 1, false ), "0" ), 
    BRANCOS( new Posicao( 39, 46 ), new Picture( Picture.Type.S, 8, false ), "0" ), 
    NOME_DA_EMPRESA( new Posicao( 47, 76 ), new Picture( Picture.Type.S, 30, false ), "EMPRESA EXEMPLO" ), 
    CODIGO_DO_BANCO( new Posicao( 77, 79 ), new Picture( Picture.Type.N, 3, false ), "341" ), 
    NOME_DO_BANCO( new Posicao( 80, 94 ), new Picture( Picture.Type.S, 15, false ), "BANCO ITAU SA" ), 
    DATA_DE_GERACAO( new Posicao( 95, 100 ), new Picture( Picture.Type.N, 6, false ), "0" ), 
    BRANCOS_1( new Posicao( 101, 394 ), new Picture( Picture.Type.N, 294, false ), "0" ), 
    NUMERO_SEQUENCIAL( new Posicao( 395, 400 ), new Picture( Picture.Type.N, 6, false ), "0" );

    private final Posicao posicao;

    private final Picture picture;

    private final String conteudo;

    TipoCampo( Posicao posicao, Picture picture, String conteudo ) {
        this.posicao = posicao;
        this.picture = picture;
        this.conteudo = conteudo;
    }

    public Posicao getPosicao() {
        return posicao;
    }

    public Picture getPicture() {
        return picture;
    }

    public String getConteudo() {
        return conteudo;
    }
    
}
