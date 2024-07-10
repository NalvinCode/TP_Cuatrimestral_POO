package com.UADE.view;

import com.UADE.base.Singleton;
import com.UADE.controller.PacienteController;
import com.UADE.dto.PacienteDTO;
import com.UADE.enums.Sexo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NuevoPacienteUI {
    private JPanel panel1;
    private JTextField txtNombreCompleto;
    private JTextField txtDomicilio;
    private JTextField txtMail;
    private JTextField txtSexo;
    private JTextField txtEdad;
    private JButton guardarButton;
    private JTextField nroDni;
    private JRadioButton femeninoRadioButton;
    private JRadioButton masculinoRadioButton;
    private final PacienteController pacientec;

    public NuevoPacienteUI() throws Exception {
        JFrame frame = new JFrame("Nuevo Paciente");
        panel1.setBorder(new EmptyBorder(15, 15, 15, 15));
        frame.setContentPane(panel1);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        pacientec = Singleton.getInstance().pacienteController;

        guardarButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                Sexo sexo = null;

                if (femeninoRadioButton.isSelected()) {
                    sexo = (Sexo.FEMENINO);
                } else if (masculinoRadioButton.isSelected()) {
                    sexo = (Sexo.MASCULINO);
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione el sexo", "Error", JOptionPane.INFORMATION_MESSAGE);
                }

                Integer codigoNuevoPac = null;
                String nombrePac = null;

                try {
                    PacienteDTO pacdto = new PacienteDTO(null, Integer.valueOf(nroDni.getText()), txtNombreCompleto.getText(), txtDomicilio.getText(), txtMail.getText(), sexo, Integer.valueOf(txtEdad.getText()));
                    codigoNuevoPac = pacientec.nuevoPaciente(pacdto);
                    nombrePac = txtNombreCompleto.getText();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                JOptionPane.showMessageDialog(null, "Se ha creado el paciente " + nombrePac, "Nuevo paciente creado", JOptionPane.INFORMATION_MESSAGE);

                try {
                    new MaestroPacientesUI();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                frame.dispose();
            }
        });


    }
}
