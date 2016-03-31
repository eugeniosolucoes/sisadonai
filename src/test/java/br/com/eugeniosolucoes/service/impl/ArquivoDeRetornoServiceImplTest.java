/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.service.impl;

import br.com.eugeniosolucoes.view.model.DadosBoletoPagoModel;
import java.io.InputStream;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author eugenio
 */
public class ArquivoDeRetornoServiceImplTest {

    static final String ARQUIVO = "/arquivos/COBST_L4GR_02_240216P_MOV.TXT";

    static final String ARQUIVO1 = "/arquivos/COBST_L4GR_02_250216P_MOV.TXT";

    static final String ARQUIVO2 = "/arquivos/COBST_L4GR_02_260216P_MOV.TXT";

    public ArquivoDeRetornoServiceImplTest() {
    }

    /**
     * Test of lerArquivoDeRetorno method, of class ArquivoDeRetornoServiceImpl.
     */
    @Test
    public void testLerArquivoDeRetorno() {
        System.out.println( "lerArquivoDeRetorno" );
        ArquivoDeRetornoServiceImpl instance = new ArquivoDeRetornoServiceImpl();
        String[] arquivos = {ARQUIVO, ARQUIVO1, ARQUIVO2};
        for ( String arquivo : arquivos ) {
            System.out.println( arquivo );
            InputStream file = this.getClass().getResourceAsStream( arquivo );
            List<DadosBoletoPagoModel> lerArquivoDeRetorno = instance.lerArquivoDeRetorno( file );
            for ( DadosBoletoPagoModel model : lerArquivoDeRetorno ) {
                System.out.println( model );
                instance.processarBaixaDeBoleto( model );
            }
        }
    }

    /**
     * Test of processarBaixaDeBoleto method, of class
     * ArquivoDeRetornoServiceImpl.
     */
    //@Test
    public void testProcessarBaixaDeBoleto_DadosBoletoPagoModel() {
        System.out.println( "processarBaixaDeBoleto" );
        DadosBoletoPagoModel boletoPagoModels = null;
        ArquivoDeRetornoServiceImpl instance = new ArquivoDeRetornoServiceImpl();
        instance.processarBaixaDeBoleto( boletoPagoModels );
        //fail( "The test case is a prototype." );
    }

    /**
     * Test of processarBaixaDeBoleto method, of class
     * ArquivoDeRetornoServiceImpl.
     */
    //@Test
    public void testProcessarBaixaDeBoleto_List() {
        System.out.println( "processarBaixaDeBoleto" );
        List<DadosBoletoPagoModel> boletoPagoModels = null;
        ArquivoDeRetornoServiceImpl instance = new ArquivoDeRetornoServiceImpl();
        instance.processarBaixaDeBoleto( boletoPagoModels );
        //fail( "The test case is a prototype." );
    }

}
