package com.UADE.view;

import com.UADE.base.Singleton;
import com.UADE.controller.PacienteController;
import com.UADE.dto.PacienteDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PacientesUI {
    private final PacienteController pacic;
    private JList<String> listPacientes;
    private JPanel panel1;
    private JButton nuevoPacienteButton;
    private JButton modificarPacienteButton;
    private JButton borrarPacienteButton;

    public PacientesUI() throws Exception {
        JFrame frame = new JFrame("Gestión de Pacientes");
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

                boolean result = false;

                try {
                    result = pacic.borrarPaciente(cod);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                if (!result) {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar al paciente. Verifique si tiene peticiones finalizadas.", "Error", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                frame.dispose();

                try {
                    new PacientesUI();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

}

