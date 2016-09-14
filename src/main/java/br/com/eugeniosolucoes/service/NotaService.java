/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.service;

/**
 *
 * @author eugenio
 */
public interface NotaService {

    void enviarNsfe() throws Exception;

    void enviarNsfe( int ano, int mes ) throws Exception;
}
