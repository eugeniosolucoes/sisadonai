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
public class DetalheCNAB400 extends AbstractCNAB400 {

    static final List<Campo> CAMPOS_DETALHE = new ArrayList<>(46);

    private static int contador = 1;

    static {
        CAMPOS_DETALHE.add( addCampo( "TIPO_DE_REGISTRO", N, 1 ) );
        CAMPOS_DETALHE.add( addCampo( "CODIGO_DE_INSCRICAO", N, 2 ) );
        CAMPOS_DETALHE.add( addCampo( "NUMERO_DE_INSCRICAO", S, 14 ) );
        CAMPOS_DETALHE.add( addCampo( "AGENCIA", N, 4 ) );
        CAMPOS_DETALHE.add( addCampo( "ZEROS", N, 2 ) );
        CAMPOS_DETALHE.add( addCampo( "CONTA", N, 5 ) );
        CAMPOS_DETALHE.add( addCampo( "DAC", N, 1 ) );
        CAMPOS_DETALHE.add( addCampo( "BRANCOS", S, 4 ) );
        CAMPOS_DETALHE.add( addCampo( "INTRUCAO_ALEGACAO", N, 4 ) );
        CAMPOS_DETALHE.add( addCampo( "USO_DA_EMPRESA", S, 25 ) );
        CAMPOS_DETALHE.add( addCampo( "NOSSO_NUMERO", N, 8 ) );
        CAMPOS_DETALHE.add( addCampo( "QTDE_DE_MOEDA", N, 8 + 5 ) );
        CAMPOS_DETALHE.add( addCampo( "NR_DA_CARTEIRA", N, 3 ) );
        CAMPOS_DETALHE.add( addCampo( "USO_DO_BANCO", S, 21 ) );
        CAMPOS_DETALHE.add( addCampo( "CARTEIRA", S, 1 ) );
        CAMPOS_DETALHE.add( addCampo( "COD_DE_OCORRENCIA", N, 2 ) );
        CAMPOS_DETALHE.add( addCampo( "NR_DO_DOCUMENTO", S, 10 ) );
        CAMPOS_DETALHE.add( addCampo( "VENCIMENTO", N, 6 ) );
        CAMPOS_DETALHE.add( addCampo( "VALOR_DO_TITULO", N, 11 + 2 ) );
        CAMPOS_DETALHE.add( addCampo( "CODIGO_DO_BANCO", N, 3 ) );
        CAMPOS_DETALHE.add( addCampo( "AGENCIA_COBRADORA", N, 5 ) );
        CAMPOS_DETALHE.add( addCampo( "ESPECIE", S, 2 ) );
        CAMPOS_DETALHE.add( addCampo( "ACEITE", S, 1 ) );
        CAMPOS_DETALHE.add( addCampo( "DATA_DE_EMISSAO", N, 6 ) );
        CAMPOS_DETALHE.add( addCampo( "INSTRUCAO_1", S, 2 ) );
        CAMPOS_DETALHE.add( addCampo( "INSTRUCAO_2", S, 2 ) );
        CAMPOS_DETALHE.add( addCampo( "JUROS_DE_1_DIA", N, 11 + 2 ) );
        CAMPOS_DETALHE.add( addCampo( "DESCONTO_ATE", N, 6 ) );
        CAMPOS_DETALHE.add( addCampo( "VALOR_DO_DESCONTO", N, 11 + 2 ) );
        CAMPOS_DETALHE.add( addCampo( "VALOR_DO_IOF", N, 11 + 2 ) );
        CAMPOS_DETALHE.add( addCampo( "ABATIMENTO", N, 11 + 2 ) );
        CAMPOS_DETALHE.add( addCampo( "CODIGO_DE_INSCRICAO_PAGADOR", N, 2 ) );
        CAMPOS_DETALHE.add( addCampo( "NUMERO_DE_INSCRICAO_PAGADOR", N, 14 ) );
        CAMPOS_DETALHE.add( addCampo( "NOME_PAGADOR", S, 30 ) );
        CAMPOS_DETALHE.add( addCampo( "BRANCOS", S, 10 ) );
        CAMPOS_DETALHE.add( addCampo( "LOGRADOURO", S, 40 ) );
        CAMPOS_DETALHE.add( addCampo( "BAIRRO", S, 12 ) );
        CAMPOS_DETALHE.add( addCampo( "CEP", N, 8 ) );
        CAMPOS_DETALHE.add( addCampo( "CIDADE", S, 15 ) );
        CAMPOS_DETALHE.add( addCampo( "ESTADO", S, 2 ) );
        CAMPOS_DETALHE.add( addCampo( "SACADOR", S, 30 ) );
        CAMPOS_DETALHE.add( addCampo( "BRANCOS_1", S, 4 ) );
        CAMPOS_DETALHE.add( addCampo( "DATA_DE_MORA", N, 6 ) );
        CAMPOS_DETALHE.add( addCampo( "PRAZO", N, 2 ) );
        CAMPOS_DETALHE.add( addCampo( "BRANCOS_2", S, 1 ) );
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
