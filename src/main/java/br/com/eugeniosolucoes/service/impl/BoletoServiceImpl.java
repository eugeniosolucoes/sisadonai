/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.service.impl;

import br.com.eugeniosolucoes.repository.BoletoRepository;
import br.com.eugeniosolucoes.repository.impl.BoletoRepositoryImpl;
import br.com.eugeniosolucoes.service.BoletoService;
import br.com.eugeniosolucoes.view.model.BoletoFiltroModel;
import br.com.eugeniosolucoes.view.model.BoletoModel;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import org.joda.time.LocalDate;

/**
 *
 * @author eugenio
 */
public class BoletoServiceImpl implements BoletoService {

    static final SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat( "yyyy", new Locale( "pt", "BR" ) );

    static final SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat( "MMMM", new Locale( "pt", "BR" ) );

    BoletoRepository repository = new BoletoRepositoryImpl();

    @Override
    public BoletoFiltroModel getBoletoFiltroModel() {
        BoletoFiltroModel model = new BoletoFiltroModel();
        model.getAnos().addAll( repository.listarAnos() );
        model.getTurmas().addAll( repository.listarTurmas() );
        model.setAno( YEAR_FORMAT.format( LocalDate.now().toDate() ) );
        model.setMes( MONTH_FORMAT.format( LocalDate.now().toDate() ) );
        if(!model.getTurmas().isEmpty()) {
            model.setTurma(model.getTurmas().get(0));
        }
        return model;
    }

    @Override
    public List<BoletoModel> getBoletosModel( BoletoFiltroModel boletoFiltroModel ) {
        return repository.listarBoletos( boletoFiltroModel );
    }

}
