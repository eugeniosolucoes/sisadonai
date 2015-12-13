/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.service;

import br.com.eugeniosolucoes.view.model.DadosBoletoFiltroModel;
import br.com.eugeniosolucoes.view.model.DadosBoletoModel;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author eugenio
 */
public interface BoletoService {

    static final SimpleDateFormat DATA_FORMAT = new SimpleDateFormat( "dd/MM/yyyy", new Locale( "pt", "BR" ) );

    static final SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat( "yyyy", new Locale( "pt", "BR" ) );

    static final SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat( "MMMM", new Locale( "pt", "BR" ) );
    
    
    JasperPrint visualizarBoletos( List<DadosBoletoModel> lista );

    DadosBoletoFiltroModel carregarFiltros();

    List<DadosBoletoModel> listarBoletos( DadosBoletoFiltroModel boletoFiltroModel );

    byte[] criarBoletoPDF( DadosBoletoModel dados );    
}
