package com.UADE.view;

import com.UADE.base.Singleton;
import com.UADE.controller.PacienteController;
import com.UADE.dto.PacienteDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MaestroPacientesUI {
    private final PacienteController pacic;
    private JList<String> listPacientes;
    private JPanel panel1;
    private JButton listaPacientesButton;
    private JButton nuevoPacienteButton;
    private JButton modificarPacienteButton;
    private JButton borrarPacienteButton;
    private JButton consultarPeticionesDelPacienteButton;

    public MaestroPacientesUI() throws Exception {
        JFrame frame = new JFrame("Maestro de Pacientes");
        panel1.setBorder(new EmptyBorder(15, 15, 15, 15));
        frame.setContentPane(panel1);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        listPacientes.setModel(listModel);

        pacic = Singleton.getInstance().pacienteController;

        List<PacienteDTO> lista = pacic.obtenerListaPacientes();

        for (PacienteDTO i : lista)
            listModel.addElement(i.getCodigo() + " | " + i.getDni() + " | " + i.getNombreCompleto());

        nuevoPacienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();

                try {
                    new NuevoPacienteUI();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        modificarPacienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();

                String value = listPacientes.getSelectedValue();
                Integer cod = Integer.valueOf(value.split(" ")[0]);

                try {
                    new ModificarPacienteUI(cod);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });


        borrarPacienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String value = listPacientes.getSelectedValue();
                Integer cod = Integer.valueOf(value.split(" ")[0]);

                try {
                    pacic.borrarPaciente(cod);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                frame.dispose();

                try {
                    new MaestroPacientesUI();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });


        consultarPeticionesDelPacienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String value = listPacientes.getSelectedValue();
                Integer cod = Integer.valueOf(value.split(" ")[0]);

                try {
                    new MaestroPeticionesUI(cod);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

}

