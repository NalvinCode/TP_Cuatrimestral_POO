package com.UADE.view;

import com.UADE.base.Singleton;
import com.UADE.controller.PeticionController;
import com.UADE.dto.ResultadoPracticaDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResultadoUI {
    private JButton guardarButton;
    private JTextField txtResultadoNumerico;
    private JTextField txtResultadoLiteral;
    private JTextArea txtTranscripcion;
    private JPanel panel1;

    private PeticionController petic;

    public ResultadoUI(Integer codPeticion, Integer codPractica) throws Exception {
        JFrame frame = new JFrame("Cargar resultado de la petición " + codPeticion);
        panel1.setBorder(new EmptyBorder(15, 15, 15, 15));
        frame.setContentPane(panel1);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        petic = Singleton.getInstance().peticionController;

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String validFields = validateFields();

                if(validFields != null){
                    JOptionPane.showMessageDialog(null, validFields, "Error", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                try {
                    petic.nuevoResultadoPractica(new ResultadoPracticaDTO(null, codPractica, codPeticion, Float.valueOf(txtResultadoNumerico.getText()), txtResultadoLiteral.getText(), txtTranscripcion.getText()));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                frame.dispose();

                try {
                    new PeticionUI(codPeticion);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

    }

    private String validateFields(){
        String campo = null;

        if(txtTranscripcion.getText().isEmpty()){
            campo = "Transcripcion";
        }

        if(campo != null){
            return "Por favor ingrese el campo " + campo;
        }

        if(txtResultadoLiteral.getText().isEmpty() && txtResultadoNumerico.getText().isEmpty()){
            return "Debe cargar al menos 1 resultado";
        }

        try{
            Float.valueOf(txtResultadoNumerico.getText());
        }catch (NumberFormatException e){
            return "Resultado numérico debe contener solo numeros";
        }

        return null;
    }
}
