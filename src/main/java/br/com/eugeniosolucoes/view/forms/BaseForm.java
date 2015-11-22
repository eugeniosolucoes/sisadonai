/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.view.forms;

import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 *
 * @author eugenio
 */
public class BaseForm extends JFrame {

    public BaseForm() {
        super();
        setupView();
    }

    final void setupView() {
        try {
            javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BoletoForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagens/adonai.png")));
    }
}
