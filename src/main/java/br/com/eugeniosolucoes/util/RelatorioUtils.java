/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author eugenio
 */
public class RelatorioUtils {
    
    private static final Logger LOG = LoggerFactory.getLogger( RelatorioUtils.class );    
    
    public static <E> byte[] gerarArquivoExcel( String pathArquivoJasper, List<E> registrosDTO, Map<String, Object> parametros ) {
        
        try {
            // aponta para o respectivo jasper
            InputStream is = RelatorioUtils.class.getResourceAsStream( pathArquivoJasper );
            JasperPrint impressao = JasperFillManager.fillReport( is, null, new JRBeanCollectionDataSource( registrosDTO ) );
            
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            
            JRXlsExporter exporterXLS = new JRXlsExporter();
            exporterXLS.setExporterInput( new SimpleExporterInput( impressao ) );
            exporterXLS.setExporterOutput( new SimpleOutputStreamExporterOutput( output ) );
            
            SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
            configuration.setOnePagePerSheet( false );
            configuration.setWhitePageBackground( false );
            configuration.setRemoveEmptySpaceBetweenColumns( true );
            configuration.setRemoveEmptySpaceBetweenRows( true );
            configuration.setDetectCellType( true );
            configuration.setForcePageBreaks( Boolean.FALSE );
            configuration.setWrapText( Boolean.FALSE );
            configuration.setMaxRowsPerSheet( 3000 );
//          // Para evitar a inclusao de uma linha vazia inteira caso uma das celulas tenha uma altura maior que 1 linha - bug #155
            //configuration.setCollapseRowSpan( true );
            exporterXLS.setConfiguration( configuration );
            exporterXLS.exportReport();
            return output.toByteArray();
        } catch ( JRException ex ) {
            LOG.error( ex.getMessage(), ex );
        } catch ( Exception ex ) {
            LOG.error( ex.getMessage(), ex );
        }
        return null;
    }
    
}
