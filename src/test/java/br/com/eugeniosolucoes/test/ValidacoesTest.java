/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.test;

import br.com.eugeniosolucoes.service.impl.BoletoServiceImpl;
import br.com.eugeniosolucoes.util.MyStrings;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author eugenio
 */
public class ValidacoesTest {

    private final BoletoServiceImpl boletoService = new BoletoServiceImpl();
    
    @Test
    public void testValidarEmailComAcentos() {
        String email = "eugenio@eugeniosoluções.com.br";
        assertTrue( MyStrings.validarEmail( email ) );
    }

    @Test
    public void testValidarEmailSemAcentos() {
        String email = "eugenio@eugeniosolucoes.com.br";
        assertTrue( MyStrings.validarEmail( email ) );
    }

    @Test
    public void testCEPValido() {
        String cep = "20770050";
        assertTrue( MyStrings.validarCEP( cep ) );
    }

    @Test
    public void testCEPInvalidoComHifen() {
        String cep = "07700-500";
        assertFalse( MyStrings.validarCEP( cep ) );
    }

    @Test
    public void testCEPInvalidoComLetra() {
        String cep = "20770A00";
        assertFalse( MyStrings.validarCEP( cep ) );
    }

    @Test
    public void testRemoveAcentos() {
        assertEquals( "eugenio", MyStrings.removerAcentos( "eugênio" ) );
        assertEquals( "eugeniosolucoes", MyStrings.removerAcentos( "eugeniosoluções" ) );
        assertEquals( "acao", MyStrings.removerAcentos( "ação" ) );
        assertEquals( "", MyStrings.removerAcentos( "" ) );
        assertNull( null, MyStrings.removerAcentos( null ) );

    }

    @Test
    public void testDACNossoNumeroItau() {
        String calcularDacItau = boletoService.calcularDacItau( "0057", "72192", "109", "98712345" );
        assertEquals( "8", calcularDacItau );
    }
    
    @Test
    public void testDACNossoNumeroItau2() {
        String calcularDacItau = boletoService.calcularDacItau( "0057", "12345", "110", "12345678" );
        assertEquals( "8", calcularDacItau );
    } 
    
    @Test
    public void testDACNossoNumeroItau3() {
        String calcularDacItau = boletoService.calcularDacItau( "0057", "72192", "198", "98712345" );
        assertEquals( "1", calcularDacItau );
    } 
    
}
