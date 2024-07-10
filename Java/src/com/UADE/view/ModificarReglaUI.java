package com.UADE.view;

import com.UADE.base.Singleton;
import com.UADE.controller.PracticaController;
import com.UADE.dto.PracticaDTO;
import com.UADE.dto.ReglaAlfaDTO;
import com.UADE.dto.ReglaNumericaDTO;
import com.UADE.dto.ReglaPracticaDTO;
import com.UADE.enums.Criterio;
import com.UADE.model.ReglaAlfa;
import com.UADE.model.ReglaPractica;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModificarReglaUI {
    private final PracticaController practicasC;
    private JButton guardarReglaButton;
    private JComboBox comboBoxTipo;
    private JComboBox comboBoxCriterio;
    private JTextField valorCriticotxt;
    private JTextField valorReservadotxt;
    private JPanel panel1;
    private final ReglaPracticaDTO reglaP;

    public ModificarReglaUI(Integer codigoR, Integer codigoP) throws Exception {
        JFrame frame = new JFrame("Modificar regla: " + codigoR);
        panel1.setBorder(new EmptyBorder(15, 15, 15, 15));
        frame.setContentPane(panel1);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        practicasC = Singleton.getInstance().practicaController;

        reglaP = practicasC.obtenerDatosRegla(codigoR);

        comboBoxTipo.addItem("Alfa");
        comboBoxTipo.addItem("Numerica");

        List<Criterio> criterios = Arrays.stream(Criterio.values()).toList();
        criterios.forEach(c-> comboBoxCriterio.addItem(c));

        if(reglaP instanceof ReglaAlfaDTO){
            comboBoxTipo.setSelectedItem("Alfa");
            valorCriticotxt.setText(((ReglaAlfaDTO) reglaP).getValorCritico());
            valorReservadotxt.setText(((ReglaAlfaDTO) reglaP).getValorReservado());
        }

        if(reglaP instanceof ReglaNumericaDTO){
            comboBoxTipo.setSelectedItem("Numerica");
            valorCriticotxt.setText(((ReglaNumericaDTO) reglaP).getValorCritico().toString());
            valorReservadotxt.setText(((ReglaNumericaDTO) reglaP).getValorReservado().toString());
        }

        comboBoxCriterio.setSelectedItem(reglaP.getCriterio());

        comboBoxTipo.setEnabled(false);

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
                    ReglaPracticaDTO newReglaP;

                    if(comboBoxTipo.getSelectedItem().toString() == "Numerica"){
                        newReglaP = new ReglaNumericaDTO(reglaP.getCodigo(), Float.parseFloat(valorCriticotxt.getText()), Float.parseFloat(valorReservadotxt.getText()), (Criterio) comboBoxCriterio.getSelectedItem());
                    }else{
                        newReglaP = new ReglaAlfaDTO(reglaP.getCodigo(), valorCriticotxt.getText(), valorReservadotxt.getText(), (Criterio) comboBoxCriterio.getSelectedItem());
                    }

                    practicasC.actualizarRegla(newReglaP);
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
