package com.UADE.view;

import com.UADE.base.Singleton;
import com.UADE.controller.PracticaController;
import com.UADE.dto.PracticaDTO;
import com.UADE.dto.ReglaAlfaDTO;
import com.UADE.dto.ReglaNumericaDTO;
import com.UADE.dto.ReglaPracticaDTO;
import com.UADE.enums.Criterio;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NuevaReglaUI {
    private final PracticaController practicasC;
    private JButton guardarReglaButton;
    private JComboBox comboBoxTipo;
    private JComboBox comboBoxCriterio;
    private JTextField valorCriticotxt;
    private JTextField valorReservadotxt;
    private JPanel panel1;

    public NuevaReglaUI(Integer codigoP) throws Exception {
        JFrame frame = new JFrame("Crear regla para practica " + codigoP);
        panel1.setBorder(new EmptyBorder(15, 15, 15, 15));
        frame.setContentPane(panel1);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        practicasC = Singleton.getInstance().practicaController;

        PracticaDTO practicaDTO = practicasC.obtenerDatosPractica(codigoP);

        comboBoxTipo.addItem("Alfa");
        comboBoxTipo.addItem("Numerica");

        List<Criterio> criterios = Arrays.stream(Criterio.values()).toList();
        criterios.forEach(c-> comboBoxCriterio.addItem(c));


        guardarReglaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(comboBoxTipo.getSelectedItem() == "Numerica"){
                    try{
                        Float.parseFloat(valorCriticotxt.getText());
                        Float.parseFloat(valorReservadotxt.getText());
                    }catch(NumberFormatException ex){
                        JOptionPane.showMessageDialog(null, "Por favor, ingrese valores numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                try {
                    ReglaPracticaDTO reglaP;

                    if(comboBoxTipo.getSelectedItem().toString() == "Numerica"){
                        reglaP = new ReglaNumericaDTO(null, Float.parseFloat(valorCriticotxt.getText()), Float.parseFloat(valorReservadotxt.getText()), (Criterio) comboBoxCriterio.getSelectedItem());
                    }else{
                        reglaP = new ReglaAlfaDTO(null, valorCriticotxt.getText(), valorReservadotxt.getText(), (Criterio) comboBoxCriterio.getSelectedItem());
                    }

                    practicasC.nuevaReglaP(reglaP, practicaDTO);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                frame.dispose();

                try {
                    new ReglaUI(practicaDTO.getCodigo());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
