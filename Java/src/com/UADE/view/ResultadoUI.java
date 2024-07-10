package com.UADE.view;

import com.UADE.base.Singleton;
import com.UADE.controller.PeticionController;
import com.UADE.dto.ResultadoPracticaDTO;
import com.UADE.enums.EstadoResultado;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResultadoUI {
    private JButton guardarButton;
    private JTextField txtResultadoNumerico;
    private JTextField txtResultadoLiteral;
    private JTextArea txtTranscripcion;
    private JComboBox<EstadoResultado> comboEstado;
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

        for (EstadoResultado e : EstadoResultado.values()) {
            comboEstado.addItem(e);
        }

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    petic.nuevoResultadoPractica(new ResultadoPracticaDTO(null, codPeticion, codPractica, Float.valueOf(txtResultadoNumerico.getText()), txtResultadoLiteral.getText(), txtTranscripcion.getText(), (EstadoResultado) comboEstado.getSelectedItem()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                frame.dispose();

                try {
                    new PeticionesUI(codPeticion);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

    }
}
