/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.repository.impl;

import br.com.eugeniosolucoes.view.model.DadosBoletoFiltroModel;
import br.com.eugeniosolucoes.view.model.DadosBoletoModel;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author eugenio
 */
public class BoletoRepositoryImplTest {

    public BoletoRepositoryImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of listarAlunosPorTurma method, of class BoletoRepositoryImpl.
     */
    @Test
    public void testListarAlunosPorTurma() {
        System.out.println( "listarAlunosPorTurma" );
        DadosBoletoFiltroModel boletoFiltroDTO = new DadosBoletoFiltroModel();

        // CODIGO DA TURMA 
        boletoFiltroDTO.setTurma( "008" );
        // ANO DO BOLETO
        boletoFiltroDTO.setAno( "2016" );
        // MES DO BOLETO
        boletoFiltroDTO.setMes( "Março" );

        BoletoRepositoryImpl instance = new BoletoRepositoryImpl();
        List<DadosBoletoModel> result = instance.listarAlunosPorTurma( boletoFiltroDTO );

        for ( DadosBoletoModel model : result ) {
            System.out.println( model );
        }

        // 32 ALUNOS NA TURMA QC/ADM III/SAB
        Assert.assertEquals( 30, result.size() );

    }

}
