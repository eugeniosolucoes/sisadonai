/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.util;

import static br.com.eugeniosolucoes.util.ArquivoRemessaCNAB400.*;
import java.math.BigDecimal;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author eugenio
 */
public class DetalheCNAB400Test {
    
    public DetalheCNAB400Test() {
    }

    /**
     * Test of getLinhaDetalhe method, of class DetalheCNAB400.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetLinhaDetalhe() throws Exception {
        System.out.println( "getLinhaDetalhe" );
        DetalheCNAB400 instance = new DetalheCNAB400();
        String result = instance.getLinhaDetalhe();
        assertEquals( 400, result.length() );
    }
 
    @Test
    public void testJurosDeMora() throws Exception {
        DetalheCNAB400 instance = new DetalheCNAB400();
        instance.getCampo( "JUROS_DE_1_DIA" ).setValor( tratarValorJuros( new BigDecimal( "370" ) ) );
        Campo campo = instance.getCampo( "JUROS_DE_1_DIA" );
        String valor = String.format( ( "%0" + ( campo.getPicture().getLength() ) + "d" ), Long.valueOf( campo.getValor() ) );
        assertEquals( "0000000000012", valor );
    }
    
    @Test
    public void testMulta() throws Exception {
        MultaCNAB400 instance = new MultaCNAB400();
        instance.getCampo( "MULTA" ).setValor( tratarValorMulta(new BigDecimal( "370" ) ) );
        Campo campo = instance.getCampo( "MULTA" );
        String valor = String.format( ( "%0" + ( campo.getPicture().getLength() ) + "d" ), Long.valueOf( campo.getValor() ) );
        assertEquals( "0000000000740", valor );
    }

    @Test
    public void testJurosDeMora1() throws Exception {
        DetalheCNAB400 instance = new DetalheCNAB400();
        instance.getCampo( "JUROS_DE_1_DIA" ).setValor( tratarValorJuros( new BigDecimal( "340" ) ) );
        Campo campo = instance.getCampo( "JUROS_DE_1_DIA" );
        String valor = String.format( ( "%0" + ( campo.getPicture().getLength() ) + "d" ), Long.valueOf( campo.getValor() ) );
        assertEquals( "0000000000011", valor );
    }
    
    @Test
    public void testMulta1() throws Exception {
        MultaCNAB400 instance = new MultaCNAB400();
        instance.getCampo( "MULTA" ).setValor( tratarValorMulta(new BigDecimal( "340" ) ) );
        Campo campo = instance.getCampo( "MULTA" );
        String valor = String.format( ( "%0" + ( campo.getPicture().getLength() ) + "d" ), Long.valueOf( campo.getValor() ) );
        assertEquals( "0000000000680", valor );
    }    
}
