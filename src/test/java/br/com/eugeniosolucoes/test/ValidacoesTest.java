/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.test;

import br.com.eugeniosolucoes.service.impl.ArquivoDeRetornoCNAB400ServiceImpl;
import br.com.eugeniosolucoes.service.impl.BoletoServiceImpl;
import br.com.eugeniosolucoes.util.MyStrings;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.apache.poi.hssf.usermodel.HeaderFooter.file;
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

    @Test
    public void testDACNossoNumeroItau4() {
        String calcularDacItau = boletoService.calcularDacItau( "7190", "48759", "109", "00242352" );
        assertEquals( "6", calcularDacItau );
    }

    @Test
    public void testDACNossoNumeroItau5() {
        String calcularDacItau = boletoService.calcularDacItau( "7190", "48759", "109", "00242374" );
        assertEquals( "0", calcularDacItau );
    }

    @Test
    public void testMd5ArquivoRetorno() throws Exception {
        String CN25079A = ArquivoDeRetornoCNAB400ServiceImpl.checksum( MessageDigest.getInstance( "MD5" ), 
                ValidacoesTest.class.getResourceAsStream( "/arquivos/CN25079A.RET" ) );
        String CN26079A = ArquivoDeRetornoCNAB400ServiceImpl.checksum( MessageDigest.getInstance( "MD5" ), 
                ValidacoesTest.class.getResourceAsStream( "/arquivos/CN26079A.RET" ) );
        assertEquals( "57246b4caab50c536a35f8f62a36900a", CN25079A );
        assertEquals( "bb69c31ecad7ef8eaa2ab1ce1533b372", CN26079A );
    }
}
