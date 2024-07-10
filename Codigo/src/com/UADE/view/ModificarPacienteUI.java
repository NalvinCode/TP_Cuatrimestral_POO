package com.UADE.view;

import com.UADE.base.Singleton;
import com.UADE.controller.PacienteController;
import com.UADE.dto.PacienteDTO;
import com.UADE.enums.Sexo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModificarPacienteUI {
    private JPanel panel1;
    private JTextField nroDni;
    private JTextField txtNombreCompleto;
    private JTextField txtDomicilio;
    private JTextField txtMail;
    private JTextField txtEdad;
    private JRadioButton femeninoRadioButton;
    private JRadioButton masculinoRadioButton;
    private JButton guardarButton;
    private final PacienteController pacientec;

    public ModificarPacienteUI(Integer codigo) throws Exception {
        pacientec = Singleton.getInstance().pacienteController;

        PacienteDTO pacdto = pacientec.obtenerPaciente(codigo);
        txtNombreCompleto.setText(pacdto.getNombreCompleto());

        //Puse antes lo otro para poder poner el nombre de titulo
        JFrame frame = new JFrame("Modificar paciente " + txtNombreCompleto.getText());
        panel1.setBorder(new EmptyBorder(15, 15, 15, 15));
        frame.setContentPane(panel1);
        frame.setSize(600, 300);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);


        nroDni.setText(String.valueOf(pacdto.getDni()));

        txtDomicilio.setText(pacdto.getDomicilio());
        txtMail.setText(pacdto.getEmail());
        if (pacdto.getSexo() == Sexo.FEMENINO) {
            femeninoRadioButton.setSelected(true);
        } else if (pacdto.getSexo() == Sexo.MASCULINO) {
            masculinoRadioButton.setSelected(true);
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione el sexo", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
        txtEdad.setText(String.valueOf(pacdto.getEdad()));


        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pacdto.setDni(Integer.valueOf(nroDni.getText()));
                pacdto.setNombreCompleto(txtNombreCompleto.getText());
                pacdto.setDomicilio(txtDomicilio.getText());
                pacdto.setEmail(txtMail.getText());
                if (femeninoRadioButton.isSelected()) {
                    pacdto.setSexo(Sexo.FEMENINO);
                } else if (masculinoRadioButton.isSelected()) {
                    pacdto.setSexo(Sexo.MASCULINO);
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione el sexo", "Error", JOptionPane.INFORMATION_MESSAGE);
                }
                pacdto.setEdad(Integer.valueOf(txtEdad.getText()));

                try {
                    pacientec.actualizarPaciente(pacdto);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                JOptionPane.showMessageDialog(null, "Se ha modificado el paciente " + txtNombreCompleto.getText(), "Nuevo paciente creado", JOptionPane.INFORMATION_MESSAGE);

                frame.dispose();

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
