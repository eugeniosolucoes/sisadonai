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
public class DetalheCNAB400 extends AbstractCNAB400 {

    static final List<Campo> CAMPOS_DETALHE = new ArrayList<>(46);

    private static int contador = 1;

    static {
        CAMPOS_DETALHE.add( addCampo( "TIPO_DE_REGISTRO", Picture.Type.N, 1 ) );
        CAMPOS_DETALHE.add( addCampo( "CODIGO_DE_INSCRICAO", Picture.Type.N, 2 ) );
        CAMPOS_DETALHE.add( addCampo( "NUMERO_DE_INSCRICAO", Picture.Type.S, 14 ) );
        CAMPOS_DETALHE.add( addCampo( "AGENCIA", Picture.Type.N, 4 ) );
        CAMPOS_DETALHE.add( addCampo( "ZEROS", Picture.Type.N, 2 ) );
        CAMPOS_DETALHE.add( addCampo( "CONTA", Picture.Type.N, 5 ) );
        CAMPOS_DETALHE.add( addCampo( "DAC", Picture.Type.N, 1 ) );
        CAMPOS_DETALHE.add( addCampo( "BRANCOS", Picture.Type.S, 4 ) );
        CAMPOS_DETALHE.add( addCampo( "INTRUCAO_ALEGACAO", Picture.Type.N, 4 ) );
        CAMPOS_DETALHE.add( addCampo( "USO_DA_EMPRESA", Picture.Type.S, 25 ) );
        CAMPOS_DETALHE.add( addCampo( "NOSSO_NUMERO", Picture.Type.N, 8 ) );
        CAMPOS_DETALHE.add( addCampo( "QTDE_DE_MOEDA", Picture.Type.N, 8 + 5 ) );
        CAMPOS_DETALHE.add( addCampo( "NR_DA_CARTEIRA", Picture.Type.N, 3 ) );
        CAMPOS_DETALHE.add( addCampo( "USO_DO_BANCO", Picture.Type.S, 21 ) );
        CAMPOS_DETALHE.add( addCampo( "CARTEIRA", Picture.Type.S, 1 ) );
        CAMPOS_DETALHE.add( addCampo( "COD_DE_OCORRENCIA", Picture.Type.N, 2 ) );
        CAMPOS_DETALHE.add( addCampo( "NR_DO_DOCUMENTO", Picture.Type.S, 10 ) );
        CAMPOS_DETALHE.add( addCampo( "VENCIMENTO", Picture.Type.N, 6 ) );
        CAMPOS_DETALHE.add( addCampo( "VALOR_DO_TITULO", Picture.Type.N, 11 + 2 ) );
        CAMPOS_DETALHE.add( addCampo( "CODIGO_DO_BANCO", Picture.Type.N, 3 ) );
        CAMPOS_DETALHE.add( addCampo( "AGENCIA_COBRADORA", Picture.Type.N, 5 ) );
        CAMPOS_DETALHE.add( addCampo( "ESPECIE", Picture.Type.S, 2 ) );
        CAMPOS_DETALHE.add( addCampo( "ACEITE", Picture.Type.S, 1 ) );
        CAMPOS_DETALHE.add( addCampo( "DATA_DE_EMISSAO", Picture.Type.N, 6 ) );
        CAMPOS_DETALHE.add( addCampo( "INSTRUCAO_1", Picture.Type.S, 2 ) );
        CAMPOS_DETALHE.add( addCampo( "INSTRUCAO_2", Picture.Type.S, 2 ) );
        CAMPOS_DETALHE.add( addCampo( "JUROS_DE_1_DIA", Picture.Type.N, 11 + 2 ) );
        CAMPOS_DETALHE.add( addCampo( "DESCONTO_ATE", Picture.Type.N, 6 ) );
        CAMPOS_DETALHE.add( addCampo( "VALOR_DO_DESCONTO", Picture.Type.N, 11 + 2 ) );
        CAMPOS_DETALHE.add( addCampo( "VALOR_DO_IOF", Picture.Type.N, 11 + 2 ) );
        CAMPOS_DETALHE.add( addCampo( "ABATIMENTO", Picture.Type.N, 11 + 2 ) );
        CAMPOS_DETALHE.add( addCampo( "CODIGO_DE_INSCRICAO_PAGADOR", Picture.Type.N, 2 ) );
        CAMPOS_DETALHE.add( addCampo( "NUMERO_DE_INSCRICAO_PAGADOR", Picture.Type.N, 14 ) );
        CAMPOS_DETALHE.add( addCampo( "NOME_PAGADOR", Picture.Type.S, 30 ) );
        CAMPOS_DETALHE.add( addCampo( "BRANCOS", Picture.Type.S, 10 ) );
        CAMPOS_DETALHE.add( addCampo( "LOGRADOURO", Picture.Type.S, 40 ) );
        CAMPOS_DETALHE.add( addCampo( "BAIRRO", Picture.Type.S, 12 ) );
        CAMPOS_DETALHE.add( addCampo( "CEP", Picture.Type.N, 8 ) );
        CAMPOS_DETALHE.add( addCampo( "CIDADE", Picture.Type.S, 15 ) );
        CAMPOS_DETALHE.add( addCampo( "ESTADO", Picture.Type.S, 2 ) );
        CAMPOS_DETALHE.add( addCampo( "SACADOR", Picture.Type.S, 30 ) );
        CAMPOS_DETALHE.add( addCampo( "BRANCOS_1", Picture.Type.S, 4 ) );
        CAMPOS_DETALHE.add( addCampo( "DATA_DE_MORA", Picture.Type.N, 6 ) );
        CAMPOS_DETALHE.add( addCampo( "PRAZO", Picture.Type.N, 2 ) );
        CAMPOS_DETALHE.add( addCampo( "BRANCOS_2", Picture.Type.S, 1 ) );
        CAMPOS_DETALHE.add( addCampo( "NUMERO_SEQUENCIAL", Picture.Type.N, 6 ) );
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
