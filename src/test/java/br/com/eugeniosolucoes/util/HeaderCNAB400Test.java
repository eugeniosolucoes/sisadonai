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
public class HeaderCNAB400Test {
    
    public HeaderCNAB400Test() {
    }

    /**
     * Test of getLinha method, of class HeaderCNAB400.
     * @throws br.com.eugeniosolucoes.util.SizeLineException
     */
    @Test
    public void testGetLinhaHeader() throws SizeLineException {
        System.out.println( "getLinhaHeader" );
        HeaderCNAB400 instance = new HeaderCNAB400();
        String result = instance.getLinhaHeader();
        System.out.println( result );
        assertEquals( 400, result.length() );
    }
    
}
