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
public class TrailerCNAB400Test {
    
    public TrailerCNAB400Test() {
    }

    /**
     * Test of getLinhaTrailer method, of class TrailerCNAB400.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetLinhaTrailer() throws Exception {
        System.out.println( "getLinhaTrailer" );
        TrailerCNAB400 instance = new TrailerCNAB400();
        String result = instance.getLinhaTrailer();
        assertEquals( 400, result.length() );
    }
    
}
