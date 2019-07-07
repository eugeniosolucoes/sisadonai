/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.util;

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

    
}
