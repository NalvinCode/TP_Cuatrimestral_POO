package com.UADE.view;

import com.UADE.base.Singleton;
import com.UADE.controller.PracticaController;
import com.UADE.dto.PracticaDTO;
import com.UADE.dto.ReglaPracticaDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReglaUI {
    private final PracticaController practicasC;
    private JList<String> listaReglas;
    private JButton nuevaReglaButton;
    private JButton modificarReglaButton;
    private JButton eliminarReglaButton;
    private JPanel panel1;

    public ReglaUI(Integer codigoP) throws Exception {
        JFrame frame = new JFrame("Reglas de práctica: " + codigoP);
        panel1.setBorder(new EmptyBorder(15, 15, 15, 15));
        frame.setContentPane(panel1);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        practicasC = Singleton.getInstance().practicaController;

        PracticaDTO practicaDTO = practicasC.obtenerDatosPractica(codigoP);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        listaReglas.setModel(listModel);

        for (Integer codR : practicaDTO.getReglasPractica()){
            ReglaPracticaDTO reglaP = practicasC.obtenerDatosRegla(codR);
            listModel.addElement(reglaP.getCodigo() + " " + reglaP.getCriterio());
        }
        nuevaReglaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                frame.dispose();

                try {
                    new NuevaReglaUI(practicaDTO.getCodigo());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });


        modificarReglaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String value = String.valueOf(listaReglas.getSelectedValue());
                    Integer cod = Integer.valueOf(value.split(" ")[0]);

                    frame.dispose();

                    try {
                        new ModificarReglaUI(cod, codigoP);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        eliminarReglaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String value = String.valueOf(listaReglas.getSelectedValue());
                Integer cod = Integer.valueOf(value.split(" ")[0]);

                try {
                    if (!practicasC.eliminarRegla(cod)) {
                        JOptionPane.showMessageDialog(null, "Debe escojer una regla para eliminar.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                frame.dispose();

                try {
                    new ReglaUI(codigoP);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
