/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.controle;

import br.com.eugeniosolucoes.app.Main;
import br.com.eugeniosolucoes.excecoes.ActionException;
import br.com.eugeniosolucoes.modelo.Destinatario;
import br.com.eugeniosolucoes.modelo.Remetente;
import br.com.eugeniosolucoes.service.BoletoService;
import br.com.eugeniosolucoes.service.impl.BoletoServiceImpl;
import br.com.eugeniosolucoes.util.MyStrings;
import br.com.eugeniosolucoes.view.model.DadosBoletoModel;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import org.joda.time.LocalDateTime;

/**
 *
 * @author alexandre
 */
public class ActionEnviarEmail extends ActionComand implements IAction {

    static final Logger LOG = Logger.getLogger( ActionEnviarEmail.class.getName() );

    static final SimpleDateFormat FORMATO_DATA = new SimpleDateFormat( "dd/MM/yyyy" );

    static final SimpleDateFormat FORMATO_HORA = new SimpleDateFormat( "HH:mm:ss" );

    static final String TIPO_ARQUIVO = "application/pdf";

    static final String TIPO_MSG = "text/html; charset=iso-8859-1";

    static final SimpleDateFormat SUFIXO_ARQUIVO_LOG = new SimpleDateFormat( "_yyyyMMdd_HHmmss" );

    static String SMTP_AUTH_USER;

    static String SMTP_AUTH_PWD;

    static String ARQUIVO_LOG;

    public static final String MSG_ABRIR_LISTA_EMAIL = "Selecione a o arquivo "
            + "contendo a lista de emails";

    public static final String MSG_ABRIR_LOCAL_ANEXO = "Selecione a pasta que "
            + "contém os arquivos a serem enviados";

    BoletoService service = new BoletoServiceImpl();

    public ActionEnviarEmail( String cmd, Object... params ) {
        super( cmd, params );
    }

    @Override
    public void execute() {

        StringBuilder log = new StringBuilder();
        log.append( "[EMAIL--------------------------------------------];" );
        log.append( "[DATA------];" );
        log.append( "[HORA------];" );
        log.append( String.format( "[STATUS-----------------------------]%n" ) );

        JButton btn = null;
        try {
            Remetente obj = (Remetente) serializarByteParaObjeto( new FileInputStream( ActionSalvarRemetente.DATA_FILE ) );

            List<DadosBoletoModel> selecionados = (List<DadosBoletoModel>) this.getParametro( 0 );

            if ( selecionados.isEmpty() ) {
                throw new IllegalStateException( "Nenhum boleto selecionado!" );
            }

            JProgressBar jp = (JProgressBar) this.getParametro( 1 );

            btn = (JButton) this.getParametro( 2 );

            jp.setVisible( true );
            btn.setEnabled( false );

            processarDestinatarios( obj, selecionados, jp );

            int cont = 0;
            for ( Destinatario d : obj.getDestinatarios() ) {
                try {

                    postMail( obj, cont );

                    jp.setString( String.format( "Enviando email %d de %d", cont + 1, obj.getDestinatarios().size() ) );
                    Date data = LocalDateTime.now().toDate();
                    cont++;
                    log.append( String.format( "%-50s ; %-10s ; %-10s ; Envio com sucesso!  %n",
                            d.getEmail().getEndereco(),
                            FORMATO_DATA.format( data ), FORMATO_HORA.format( data ) ) );
                } catch ( Exception e ) {
                    jp.setString( String.format( "FALHA ao enviar email %d de %d", cont + 1, obj.getDestinatarios().size() ) );
                    Date data = LocalDateTime.now().toDate();
                    log.append( String.format( "%-50s ; %-10s ; %-10s ; Falha no envio! CAUSA: %s %n",
                            d.getEmail().getEndereco(),
                            FORMATO_DATA.format( data ), FORMATO_HORA.format( data ),
                            e.getMessage() ) );
                }
            }

            jp.setVisible( false );

            salvarLog( log.toString() );

            jp.setString( "Processando envio..." );

            JOptionPane.showMessageDialog( null, "Término do envio de emails!" );

            if ( Desktop.isDesktopSupported() ) {
                Desktop.getDesktop().open( new File( ARQUIVO_LOG ) );
                //Runtime.getRuntime().exec( "cmd /c START " + ARQUIVO_LOG );
            }

        } catch ( ActionException | IOException | HeadlessException | IllegalStateException e ) {
            MyStrings.exibeMensagem( e.getMessage() );
        } finally {
            if ( btn != null ) {
                btn.setEnabled( true );
            }
        }

    }

    public void postMail( Remetente obj, Integer indiceRemetente ) throws MessagingException {

        SMTP_AUTH_USER = obj.getUsuario();
        SMTP_AUTH_PWD = obj.getSenha();

        boolean debug = false;

        java.security.Security.addProvider( new com.sun.net.ssl.internal.ssl.Provider() );

        //Set the host smtp address
        Properties props = new Properties();
        props.put( "mail.transport.protocol", "smtp" );
        props.put( "mail.smtp.host", obj.getServidor() );
        if ( obj.getServidor().contains( "gmail" ) ) {
            props.put( "mail.smtp.starttls.enable", "true" );
        }
        props.put( "mail.smtp.auth", "true" );
        props.put( "mail.smtp.port", "587" );
        // props.put("mail.smtp.connectiontimeout", "5000");
        //props.put("mail.smtp.dsn.notify", "NOTIFY");

        Authenticator auth = new SMTPAuthenticator();
        Session session = Session.getDefaultInstance( props, auth );

        session.setDebug( debug );

        // create a message
        Message msg = new MimeMessage( session );

        // set the from and to address
        InternetAddress addressFrom = new InternetAddress(
                obj.getEmail().getEndereco()
        );
        try {
            addressFrom.setPersonal( obj.getNome() );
        } catch ( UnsupportedEncodingException ex ) {
            LOG.log( Level.SEVERE, ex.getMessage(), ex );
        }
        msg.setFrom( addressFrom );

        InternetAddress[] addressTo = new InternetAddress[1];

        addressTo[0] = new InternetAddress( obj.getDestinatarios().get( indiceRemetente ).getEmail().getEndereco() );

        msg.setRecipients( Message.RecipientType.TO, addressTo );

        // Create the message part
        BodyPart messageBodyPart = new MimeBodyPart();

        // Fill the message
        messageBodyPart.setText( obj.getEmail().getMensagem() );

        messageBodyPart.setContent( obj.getEmail().getMensagem(), TIPO_MSG );

        // Create a multipar message
        Multipart multipart = new MimeMultipart();

        // Set text message part
        multipart.addBodyPart( messageBodyPart );

        // Part two is attachment
        messageBodyPart = new MimeBodyPart();
        DataSource source = new ByteArrayDataSource( obj.getDestinatarios().get( indiceRemetente ).getEmail().getAnexo().getPdf(), TIPO_ARQUIVO );
        messageBodyPart.setDataHandler( new DataHandler( source ) );
        messageBodyPart.setFileName( obj.getDestinatarios().get( indiceRemetente ).getEmail().getAnexo().getNome() );
        multipart.addBodyPart( messageBodyPart );

        // Send the complete message parts
        msg.setContent( multipart );

        // Setting the Subject and Content Type
        msg.setSubject( obj.getEmail().getAssunto() );
        try {

            Transport.send( msg );
            Thread.sleep( 3000 );
        } catch ( InterruptedException ex ) {
            LOG.log( Level.SEVERE, ex.getMessage(), ex );
        }

    }

    private void processarDestinatarios( Remetente obj, List<DadosBoletoModel> selecionados, JProgressBar jp ) throws ActionException, FileNotFoundException, IOException {
        int cont = 0;
        for ( DadosBoletoModel selecionado : selecionados ) {
            if ( !selecionado.isBoletoValido() ) {
                cont++;
                continue;
            }
            Destinatario dest = new Destinatario();
            if ( MyStrings.validarEmail( selecionado.getEmail().trim() ) ) {
                // TODO - REMOVER APOS TESTE DE HOMOLOGACAO
                if ( Main.isTestMode() ) {
                    dest.getEmail().setEndereco( Main.getEmailTest() );
                } else {
                    dest.getEmail().setEndereco( selecionado.getEmail().trim() );
                }

                dest.getEmail().getAnexo().setNome( String.format( "BOLETO_%s_%s.pdf",
                        BoletoService.MONTH_FORMAT.format( selecionado.getVencimento() ),
                        BoletoService.YEAR_FORMAT.format( selecionado.getVencimento() ) ) );

                byte[] pdf = service.criarBoletoPDF( selecionado );

                dest.getEmail().getAnexo().setPdf( pdf );

                jp.setString( String.format( "Processando email %d de %d", cont + 1, selecionados.size() ) );
            } else {
                throw new ActionException( "A lista possui email(s) inválido(s)!" );
            }
            obj.getDestinatarios().add( dest );
            cont++;
        }

    }

    public void salvarLog( String log ) throws ActionException {
        BufferedWriter out;
        ARQUIVO_LOG = String.format( "log%s.txt", SUFIXO_ARQUIVO_LOG.format( LocalDateTime.now().toDate() ) );
        try {
            out = new BufferedWriter( new FileWriter( ARQUIVO_LOG ) );
            out.write( log );
            out.close();
        } catch ( Exception e ) {
            JOptionPane.showMessageDialog( null, e.getMessage() );
        }
    }

    private class SMTPAuthenticator extends javax.mail.Authenticator {

        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            String username = SMTP_AUTH_USER;
            String password = SMTP_AUTH_PWD;
            return new PasswordAuthentication( username, password );
        }
    }

    public Object serializarByteParaObjeto( InputStream bytes ) throws ActionException {
        ByteArrayOutputStream bytesGrava = new ByteArrayOutputStream();
        Object objeto = null;
        byte[] b = new byte[8];
        try {
            int i = bytes.read( b );
            while (i != -1) {
                bytesGrava.write( b, 0, i );
                i = bytes.read( b );
            }
            ObjectInputStream objetoLeitura = new ObjectInputStream( new ByteArrayInputStream( bytesGrava.toByteArray() ) );
            objeto = objetoLeitura.readObject();
        } catch ( IOException | ClassNotFoundException e ) {
            throw new ActionException( "Houve um erro ao transformar byte para objeto: " + e.toString() );
        }
        return objeto;
    }

}
