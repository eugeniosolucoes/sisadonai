/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.service.impl;

import br.com.caelum.stella.boleto.Banco;
import br.com.caelum.stella.boleto.Beneficiario;
import br.com.caelum.stella.boleto.Boleto;
import br.com.caelum.stella.boleto.Datas;
import br.com.caelum.stella.boleto.Endereco;
import br.com.caelum.stella.boleto.Pagador;
import br.com.caelum.stella.boleto.bancos.Santander;
import br.com.eugeniosolucoes.repository.BoletoRepository;
import br.com.eugeniosolucoes.repository.impl.BoletoRepositoryImpl;
import br.com.eugeniosolucoes.service.BoletoService;
import br.com.eugeniosolucoes.util.MyGeradorDeBoleto;
import br.com.eugeniosolucoes.view.model.DadosBoletoFiltroModel;
import br.com.eugeniosolucoes.view.model.DadosBoletoModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import net.sf.jasperreports.engine.JasperPrint;
import org.joda.time.LocalDate;

/**
 *
 * @author eugenio
 */
public class BoletoServiceImpl implements BoletoService {

    static final SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy", new Locale("pt", "BR"));

    static final SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat("MMMM", new Locale("pt", "BR"));

    BoletoRepository repository = new BoletoRepositoryImpl();

    @Override
    public DadosBoletoFiltroModel carregarFiltros() {
        LocalDate dataCorrente = LocalDate.now();
        Date proximoMes = dataCorrente.plusMonths(1).toDate();
        DadosBoletoFiltroModel model = new DadosBoletoFiltroModel();
        model.getAnos().addAll(repository.listarAnos());
        model.getTurmas().addAll(repository.listarTurmas());
        model.setAno(YEAR_FORMAT.format(proximoMes));
        model.setMes(MONTH_FORMAT.format(proximoMes));
        if (!model.getTurmas().isEmpty()) {
            model.setTurma(model.getTurmas().get(0));
        }
        return model;
    }

    @Override
    public List<DadosBoletoModel> listarBoletos(DadosBoletoFiltroModel dadosBoletoFiltroModel) {
        return repository.listarBoletos(dadosBoletoFiltroModel);
    }

    @Override
    public JasperPrint visualizarBoletos(List<DadosBoletoModel> lista) {
        if (lista.isEmpty()) {
            throw new IllegalStateException("Nenhum boleto selecionado!");
        }
        List<Boleto> boletos = new ArrayList<>();
        for (DadosBoletoModel dados : lista) {
            Datas datas = Datas.novasDatas()
                    .comDocumento(LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthOfYear(), LocalDate.now().getYear())
                    .comProcessamento(LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthOfYear(), LocalDate.now().getYear())
                    .comVencimento(LocalDate.fromDateFields(dados.getVencimento()).getDayOfMonth(),
                            LocalDate.fromDateFields(dados.getVencimento()).getMonthOfYear(),
                            LocalDate.fromDateFields(dados.getVencimento()).getYear());

            Endereco enderecoBeneficiario = Endereco.novoEndereco()
                    .comLogradouro("Rua da Quitanda, 185")
                    .comBairro("Centro")
                    .comCep("20091-005")
                    .comCidade("Rio de Janeiro")
                    .comUf("RJ");

            //Quem emite o boleto
            Beneficiario beneficiario = Beneficiario.novoBeneficiario()
                    .comNomeBeneficiario("CURSO ADONAI LTDA")
                    .comEndereco(enderecoBeneficiario)
                    .comAgencia("6790").comDigitoAgencia("0")
                    .comCarteira("102")
                    .comNumeroConvenio("5260965")
                    .comNossoNumero("105613749501").comDigitoNossoNumero("4");

            Endereco enderecoPagador = Endereco.novoEndereco()
                    .comLogradouro(dados.getEndereco().getLogradouro())
                    .comBairro(dados.getEndereco().getBairro())
                    .comCep(dados.getEndereco().getCep())
                    .comCidade(dados.getEndereco().getCidade())
                    .comUf(dados.getEndereco().getEstado());

            //Quem paga o boleto
            Pagador pagador = Pagador.novoPagador()
                    .comNome(dados.getAluno())
                    .comDocumento(dados.getCpf())
                    .comEndereco(enderecoPagador);

            Banco banco = new Santander();

            Boleto boleto = Boleto.novoBoleto()
                    .comBanco(banco)
                    .comDatas(datas)
                    .comBeneficiario(beneficiario)
                    .comPagador(pagador)
                    .comValorBoleto(dados.getValor().toString())
                    .comNumeroDoDocumento(dados.getNumeroDocumento())
                    .comInstrucoes("instrucao 1", "instrucao 2", "instrucao 3", "instrucao 4", "instrucao 5")
                    .comLocaisDePagamento("local 1", "local 2");

            boletos.add(boleto);
        }
        if (!boletos.isEmpty()) {
            return new MyGeradorDeBoleto(boletos).geraRelatorio();
        }
        return null;
    }
}
