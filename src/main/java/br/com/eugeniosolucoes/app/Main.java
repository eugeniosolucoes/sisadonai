/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.app;

import br.com.eugeniosolucoes.view.forms.BoletoForm;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eugenio
 */
public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try {
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new BoletoForm().setVisible(true);
                }
            });
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        } finally {
        }
    }

}
