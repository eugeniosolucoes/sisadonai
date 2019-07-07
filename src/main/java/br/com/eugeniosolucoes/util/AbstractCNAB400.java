/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.util;

import java.util.List;

/**
 *
 * @author eugenio
 */
public abstract class AbstractCNAB400 {

    private static final int SIZE_LINE = 400;

    public String getLinha( List<Campo> campos ) throws SizeLineException {
        String linha = "";
        for ( Campo campo : campos ) {
            linha += processarCampo( campo );
        }
        if ( linha.length() != SIZE_LINE ) {
            throw new SizeLineException( String.format( "Erro ao processar linha(%d)!", linha.length() ) );
        }
        return linha;
    }

    public String processarCampo( Campo campo ) {
        String s = "";
        int maxLength = campo.getPicture().getLength();
        boolean substituir = !MyStrings.isNullOrEmpty( campo.getValor() );
        String padrao = campo.getValorPadrao();
        switch ( campo.getPicture().type ) {
            case S:
                if ( substituir && campo.getValor().length() > maxLength ) {
                    s = String.format( ( "%-" + ( campo.getPicture().getLength() ) + "s" ), substituir ? campo.getValor().substring( 0, maxLength ) : padrao );
                } else {
                    s = String.format( ( "%-" + ( campo.getPicture().getLength() ) + "s" ), substituir ? campo.getValor() : padrao );
                }
                break;
            case N:
                if ( substituir && campo.getValor().length() > maxLength ) {
                    s = String.format( ( "%0" + ( campo.getPicture().getLength() ) + "d" ), substituir ? Integer.valueOf( campo.getValor().substring( 0, maxLength ) ) : Integer.valueOf( padrao ) );
                } else {
                    s = String.format( ( "%0" + ( campo.getPicture().getLength() ) + "d" ), substituir ? Integer.valueOf( campo.getValor() ) : Integer.valueOf( padrao ) );
                }
                break;
        }
        if(s.length() != campo.getPicture().getLength()){
            throw new IllegalStateException( "ERRO NO CAMPO: " + campo );
        }
        return s;
    }

    public abstract Campo getCampo( String nome );

    public Campo recuperarCampo( List<? extends Campo> listaCampo, String nome ) {
        Campo campo = new Campo( nome, null, null, null );
        return listaCampo.get( listaCampo.indexOf( campo ) );
    }

}
