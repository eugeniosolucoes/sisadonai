/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.util;

import br.com.caelum.stella.boleto.Boleto;
import br.com.caelum.stella.boleto.transformer.GeradorDeBoleto;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author eugenio
 */
public class MyGeradorDeBoleto extends GeradorDeBoleto {

    public MyGeradorDeBoleto( Collection<Boleto> boletos ) {
        super( boletos );
    }

    public MyGeradorDeBoleto( Boleto... boletos ) {
        super( boletos );
    }

    public MyGeradorDeBoleto( InputStream template, Map<String, Object> parametros, Boleto... boletos ) {
        super( template, parametros, boletos );
    }

    public MyGeradorDeBoleto( InputStream template, Map<String, Object> parametros, Collection<Boleto> boletos ) {
        super( template, parametros, boletos );
    }

    @Override
    public JasperPrint geraRelatorio() {
        return super.geraRelatorio();
    }

}
