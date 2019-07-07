/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.util;

import br.com.eugeniosolucoes.excecoes.TamanhoLinhaException;
import br.com.eugeniosolucoes.excecoes.CepException;
import br.com.eugeniosolucoes.repository.ArquivoRemessaRepository;
import br.com.eugeniosolucoes.repository.impl.ArquivoRemessaRepositoryImpl;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *
 * Registro inválido na linha 1. O tamanho do registro deve ser de 240 posições.
 *
 * Campo inválido na linha 1. Tipo de registro [Header de Arquivo]. O campo
 * [Código de Transmissão], posição 33 a 47, deve ser [345100007570007. Definido
 * em Cadastros > Empresas]. Valor atual [0007570007 ].
 *
 * Campo inválido na linha 1. Tipo de registro [Header de Arquivo]. O campo
 * [Reservado (uso Banco)], posição 48 a 72, deve ser [Brancos]. Valor atual [
 * CURSO].
 *
 * Campo inválido na linha 1. Tipo de registro [Header de Arquivo]. O campo
 * [Reservado (uso Banco)], posição 133 a 142, deve ser [Brancos]. Valor atual [
 * 10401].
 *
 * Campo inválido na linha 1. Tipo de registro [Header de Arquivo]. O campo
 * [Código remessa], posição 143 a 143, deve ser [1]. Valor atual [2].
 *
 * Campo inválido na linha 1. Tipo de registro [Header de Arquivo]. O campo
 * [Data de geração do arquivo], posição 144 a 151, deve ser [DDMMAAAA]. Valor
 * atual [016 ].
 *
 * Campo inválido na linha 1. Tipo de registro [Header de Arquivo]. O campo
 * [Reservado (uso Banco)], posição 152 a 157, deve ser [Brancos]. Valor atual [
 * 00000].
 *
 * Campo inválido na linha 1. Tipo de registro [Header de Arquivo]. O campo [Nº
 * seqüencial do arquivo], posição 158 a 163, deve ser [diferente de zeros,
 * maior que zero]. Valor atual [1040 ].
 *
 * Campo inválido na linha 1. Tipo de registro [Header de Arquivo]. O campo [Nº
 * da versão do layout do arquivo], posição 164 a 166, deve ser [040] . Valor
 * atual [ ].
 *
 * Campo inválido na linha 1. Tipo de registro [Header de Arquivo]. O campo
 * [Reservado (uso Banco)], posição 167 a 240, deve ser [Brancos]. Valor atual [
 * ].
 *
 * Registro inválido na linha 2. O tamanho do registro deve ser de 240 posições.
 *
 * Campo inválido na linha 2. Tipo de registro [Header de Lote]. O campo [Código
 * de Transmissão], posição 54 a 68, deve ser [345100007570007. Definido em
 * Cadastros > Empresas]. Valor atual [0007570007 ].
 *
 * Campo inválido na linha 2. Tipo de registro [Header de Lote]. O campo
 * [Reservado uso Banco], posição 69 a 73, deve ser [Brancos]. Valor atual
 * [CURSO].
 *
 * Campo inválido na linha 2. Tipo de registro [Header de Lote]. O campo [Data
 * da gravação remessa/retorno], posição 192 a 199, deve ser [DDMMAAAA]. Valor
 * atual [016 ].
 *
 * Campo inválido na linha 2. Tipo de registro [Header de Lote]. O campo
 * [Reservado (uso Banco)], posição 200 a 240, deve ser [Brancos]. Valor atual [
 * ].
 *
 * Campo inválido na linha 3. Tipo de registro [Detalhe - Segmento P]. O campo
 * [Número da Conta Corrente], posição 23 a 31, deve ser [013003087. Definido em
 * Cadastros > Empresas]. Valor atual [13003087-].
 *
 * Campo inválido na linha 3. Tipo de registro [Detalhe - Segmento P]. O campo
 * [Dígito Verificador da Conta], posição 32 a 32, deve ser [9. Definido em
 * Cadastros > Empresas]. Valor atual [0].
 *
 * Campo inválido na linha 3. Tipo de registro [Detalhe - Segmento P]. O campo
 * [Conta cobrança], posição 33 a 41, deve ser [013003087. Definido em Cadastros
 * > Empresas]. Valor atual [13003087-].
 *
 * Campo inválido na linha 3. Tipo de registro [Detalhe - Segmento P]. O campo
 * [Dígito da conta cobrança], posição 42 a 42, deve ser [9. Definido em
 * Cadastros > Empresas]. Valor atual [0].
 *
 * Campo inválido na linha 6. Tipo de registro [Detalhe - Segmento P]. O campo
 * [Número da Conta Corrente], posição 23 a 31, deve ser [013003087. Definido em
 * Cadastros > Empresas]. Valor atual [13003087-].
 *
 * Campo inválido na linha 6. Tipo de registro [Detalhe - Segmento P]. O campo
 * [Dígito Verificador da Conta], posição 32 a 32, deve ser [9. Definido em
 * Cadastros > Empresas]. Valor atual [0].
 *
 * Campo inválido na linha 6. Tipo de registro [Detalhe - Segmento P]. O campo
 * [Conta cobrança], posição 33 a 41, deve ser [013003087. Definido em Cadastros
 * > Empresas]. Valor atual [13003087-].
 *
 * Campo inválido na linha 6. Tipo de registro [Detalhe - Segmento P]. O campo
 * [Dígito da conta cobrança], posição 42 a 42, deve ser [9. Definido em
 * Cadastros > Empresas]. Valor atual [0].
 *
 * Campo inválido na linha 9. Tipo de registro [Detalhe - Segmento P]. O campo
 * [Número da Conta Corrente], posição 23 a 31, deve ser [013003087. Definido em
 * Cadastros > Empresas]. Valor atual [13003087-].
 *
 * Campo inválido na linha 9. Tipo de registro [Detalhe - Segmento P]. O campo
 * [Dígito Verificador da Conta], posição 32 a 32, deve ser [9. Definido em
 * Cadastros > Empresas]. Valor atual [0].
 *
 * Campo inválido na linha 9. Tipo de registro [Detalhe - Segmento P]. O campo
 * [Conta cobrança], posição 33 a 41, deve ser [013003087. Definido em Cadastros
 * > Empresas]. Valor atual [13003087-].
 *
 * Campo inválido na linha 9. Tipo de registro [Detalhe - Segmento P]. O campo
 * [Dígito da conta cobrança], posição 42 a 42, deve ser [9. Definido em
 * Cadastros > Empresas]. Valor atual [0].
 *
 * Campo inválido na linha 12. Tipo de registro [Detalhe - Segmento P]. O campo
 * [Número da Conta Corrente], posição 23 a 31, deve ser [013003087. Definido em
 * Cadastros > Empresas]. Valor atual [13003087-].
 *
 * Campo inválido na linha 12. Tipo de registro [Detalhe - Segmento P]. O campo
 * [Dígito Verificador da Conta], posição 32 a 32, deve ser [9. Definido em
 * Cadastros > Empresas]. Valor atual [0].
 *
 * Campo inválido na linha 12. Tipo de registro [Detalhe - Segmento P]. O campo
 * [Conta cobrança], posição 33 a 41, deve ser [013003087. Definido em Cadastros
 * > Empresas]. Valor atual [13003087-].
 *
 * Campo inválido na linha 12. Tipo de registro [Detalhe - Segmento P]. O campo
 * [Dígito da conta cobrança], posição 42 a 42, deve ser [9. Definido em
 * Cadastros > Empresas]. Valor atual [0].
 *
 * Campo inválido na linha 15. Tipo de registro [Detalhe - Segmento P]. O campo
 * [Número da Conta Corrente], posição 23 a 31, deve ser [013003087. Definido em
 * Cadastros > Empresas]. Valor atual [13003087-].
 *
 * Campo inválido na linha 15. Tipo de registro [Detalhe - Segmento P]. O campo
 * [Dígito Verificador da Conta], posição 32 a 32, deve ser [9. Definido em
 * Cadastros > Empresas]. Valor atual [0].
 *
 * Campo inválido na linha 15. Tipo de registro [Detalhe - Segmento P]. O campo
 * [Conta cobrança], posição 33 a 41, deve ser [013003087. Definido em Cadastros
 * > Empresas]. Valor atual [13003087-].
 *
 * Campo inválido na linha 15. Tipo de registro [Detalhe - Segmento P]. O campo
 * [Dígito da conta cobrança], posição 42 a 42, deve ser [9. Definido em
 * Cadastros > Empresas]. Valor atual [0].
 *
 *
 */
/**
 *
 * @author eugenio
 */
public class TratadorArquivoRemessa {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    private static final Logger LOG = Logger.getLogger( TratadorArquivoRemessa.class.getName() );

    static final String INICIO_COD_TRANSMISSAO = "34510";

    static final String CONTA_CORRENTE = "013003087";

    static final String DIGITO_CONTA_CORRENTE = "9";

    static final int TAMANHO = 240;
    
    public static final SimpleDateFormat SUFIXO_ARQUIVO_LOG = new SimpleDateFormat( "_yyyyMMdd_HHmmss" );

    ArquivoRemessaRepository repository = new ArquivoRemessaRepositoryImpl();

    public String corrigirArquivo( File file ) {
        StringBuilder arquivo = new StringBuilder();
        StringBuilder erro = new StringBuilder();
        int cont = 0;
        try ( Scanner scanner = new Scanner( file ) ) {
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                try {
                    if ( cont == 0 ) {
                        adicionarLinha( arquivo, tratarLinha1( linha ) );
                    } else if ( cont == 1 ) {
                        adicionarLinha( arquivo, tratarLinha2( linha ) );
                    } else if ( linha.charAt( 13 ) == 'P' ) {
                        adicionarLinha( arquivo, tratarLinhaP( linha ) );
                    } else if ( linha.charAt( 13 ) == 'R' ) {
                        adicionarLinha( arquivo, tratarLinhaR( linha ) );
                    } else if ( linha.charAt( 13 ) == 'Q' ) {
                        adicionarLinha( arquivo, tratarLinhaQ( linha ) );
                    } else {
                        adicionarLinha( arquivo, linha );
                    }
                } catch ( TamanhoLinhaException e ) {
                    adicionarLinhaErroTamanho( erro, cont + 1 );
                } catch ( CepException ex ) {
                    adicionarLinhaErroCep( erro, cont + 1 );
                }
                cont++;
            }
        } catch ( FileNotFoundException ex ) {
            LOG.log( Level.SEVERE, ex.getMessage(), ex );
            throw new IllegalStateException( "Arquivo não encontrado!" );
        }
        showSizeLine( arquivo );
        if ( !erro.toString().isEmpty() ) {
            throw new IllegalStateException( erro.toString() );
        }
        return arquivo.toString();
    }

    private void adicionarLinha( StringBuilder arquivo, String linhaTratada ) throws TamanhoLinhaException {
        System.out.println( linhaTratada.length() );
        if ( linhaTratada.length() != TAMANHO ) {
            throw new TamanhoLinhaException();
        }
        arquivo.append( String.format( "%s%n", linhaTratada ) );
    }

    private void adicionarLinhaErroTamanho( StringBuilder erro, int linha ) {
        erro.append( String.format( "Linha nº - %d: Não possui 240 colunas.%n", linha ) );
    }

    private void adicionarLinhaErroCep( StringBuilder erro, int linha ) {
        erro.append( String.format( "Linha nº - %d: CEP inválido.%n", linha ) );
    }

    static void showSizeLine( StringBuilder arquivo ) {
        int cont = 0;
        String str = arquivo.toString();
        Scanner scanner = new Scanner( str );
        while (scanner.hasNextLine()) {
            String linha = scanner.nextLine();
            System.out.printf( "%2s %s%n", cont++, linha.length() );
        }
    }

    public String criarNovoArquivo( String arquivo, String conteudo ) throws IOException {
        String nomeArquivo = tratarNomeArquivo( arquivo );
        if ( arquivo != null ) {
            try ( FileWriter writer = new FileWriter( nomeArquivo ) ) {
                writer.append( conteudo );
            }
        } else {
            throw new IllegalStateException( "Problemas na geração do arquivo!" );
        }
        return nomeArquivo;
    }

    public void criarNovoArquivoRemessa( String nomeArquivo, String conteudo ) throws IOException {
        if ( nomeArquivo != null ) {
            try ( FileWriter writer = new FileWriter( nomeArquivo ) ) {
                writer.append( conteudo );
            }
        } else {
            throw new IllegalStateException( "Problemas na geração do arquivo!" );
        }
    }    
    
    static String tratarNomeArquivo( String nome ) {
        int indice = nome.lastIndexOf( ".txt" );
        return nome.substring( 0, indice ).concat( "-sisadonai.txt" );
    }

    static String recuperarCampo( String linha, int i, int f ) {
        return linha.substring( i - 1, f - 1 );
    }

    static void imprimirMarcadores() {
        int cont = 1;
        for ( int i = 1; i <= TAMANHO; i++ ) {
            if ( i % 10 == 0 ) {
                System.out.print( cont % 10 );
                cont++;
            } else {
                System.out.print( " " );
            }
        }
        System.out.println();
        for ( int i = 1; i <= TAMANHO; i++ ) {
            System.out.print( i % 10 );
        }
        System.out.println();
    }

    /**
     * Campo inválido na linha 1. Tipo de registro [Header de Arquivo]. O campo
     * [Código de Transmissão], posição 33 a 47, deve ser [345100007570007.
     * Definido em Cadastros > Empresas]. Valor atual [0007570007 ].
     *
     * @param arquivo
     */
    static String tratarLinha1( String linha ) {
        int posicaoLinha = 32;
        if ( recuperarCampo( linha, 33, 47 ).trim().equals( "0007570007" ) ) {
            LOG.log( Level.INFO, "LINHA 1 ENCONTRADA E ALTERADA\n" );
            return new StringBuilder( linha ).insert( posicaoLinha, INICIO_COD_TRANSMISSAO ).toString();
        }
        return linha;
    }

    /**
     * Campo inválido na linha 2. Tipo de registro [Header de Lote]. O campo
     * [Código de Transmissão], posição 54 a 68, deve ser [345100007570007.
     * Definido em Cadastros > Empresas]. Valor atual [0007570007 ].
     *
     * @param arquivo
     */
    static String tratarLinha2( String linha ) {
        int posicaoLinha = 53;
        if ( recuperarCampo( linha, 54, 68 ).trim().equals( "0007570007" ) ) {
            LOG.log( Level.INFO, "LINHA 2 ENCONTRADA E ALTERADA\n" );
            return new StringBuilder( linha ).insert( posicaoLinha, INICIO_COD_TRANSMISSAO ).toString();
        }
        return linha;
    }

    static String tratarLinhaP( String linha ) {
        String dataVencimento = linha.substring( 77, 85 );
        return linha.replaceAll( "13003087-0", "0130030879" )
                .substring( 0, 117 )
                .concat( "2" ).concat( dataVencimento.concat( linha.substring( 126 ) ) )
                .substring( 0, 135 ).concat( "1" )
                .concat( linha.substring( 136 ) );
    }

    static String tratarLinhaR( String linha ) {
        return linha
                .substring( 0, 65 ).concat( "2" )
                .concat( linha.substring( 66 ) )
                .substring( 0, 86 ).concat( "200" ).concat( linha.substring( 89 ) );
    }

    String tratarLinhaQ( String linha ) throws CepException {
        String cpf = recuperarCampo( linha, 23, 34 );
        String cep = repository.retornarCepDoAluno( cpf );
        if ( MyStrings.isNullOrEmpty( cep ) || cep.length() != 8 ) {
            LOG.log(Level.SEVERE, "CPF: {0} - CEP INVALIDO:{1}", new Object[]{cpf, cep});
            throw new CepException( "CEP inválido: " + cep );
        }
        return linha.substring( 0, 128 ).concat( cep ).concat( linha.substring( 136 ) );
    }

    static void validarLinhas( StringBuilder arquivo ) {
        int cont = 0;
        String str = arquivo.toString();
        try ( Scanner scanner = new Scanner( str ) ) {
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                cont++;
                LOG.log( Level.INFO, String.format( "linha: %3s - tamanho: %3s %n", cont, linha.length() ) );
                if ( linha.length() != TAMANHO ) {
                    throw new IllegalStateException( String.format( "ERRO: linha %d tamanho (%d) inválido!", cont, linha.length() ) );
                }
            }
        }
    }

    static boolean isArquivoValidado( StringBuilder arquivo ) {
        try {
            validarLinhas( arquivo );
        } catch ( Exception e ) {
            LOG.log( Level.SEVERE, e.getMessage(), e );
            return false;
        }
        return true;
    }

}
