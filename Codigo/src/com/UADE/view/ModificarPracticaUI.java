package com.UADE.view;

import com.UADE.base.Singleton;
import com.UADE.controller.PracticaController;
import com.UADE.dto.PracticaDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

public class ModificarPracticaUI {
    private final PracticaController practicasC;
    private JPanel panel1;
    private JTextField txtNombre;
    private JTextField txtDemora;
    private JButton guardarModificaciónButton;
    private JComboBox<String> comboGrupo;
    private JButton modificarReglasButton;
    private JList<String> listaPracticas;
    private Integer oldGrupo = null;
    private Integer oldGrupoIndex = null;

    public ModificarPracticaUI(Integer codigo) throws Exception {
        JFrame frame = new JFrame("Modificar práctica " + codigo);
        panel1.setBorder(new EmptyBorder(15, 15, 15, 15));
        frame.setContentPane(panel1);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        practicasC = Singleton.getInstance().practicaController;

        PracticaDTO practicaDTO = practicasC.obtenerDatosPractica(codigo);

        txtNombre.setText(practicaDTO.getNombre());
        txtDemora.setText(practicaDTO.getTiempoEstimado().toString());

        List<PracticaDTO> lista = practicasC.obtenerListaPracticas();

        comboGrupo.addItem("Sin grupo");

        for (PracticaDTO i : lista) {
            if (i.getCodigo().intValue() != practicaDTO.getCodigo().intValue()) { // No es si misma
                comboGrupo.addItem(i.getCodigo() + " " + i.getNombre());
            }

            if (oldGrupo == null) {
                for (Integer codpr : i.getCodSubPracticas()) { // Para cada subpractica de todas las practicas
                    if (practicaDTO.getCodigo().intValue() == codpr.intValue()) { // Si esta practica esta como subpractica de otra
                        oldGrupo = i.getCodigo(); // Guardo la practica que contiene a esta como subpractica
                        oldGrupoIndex = comboGrupo.getItemCount() - 1; // Resto 1 dado que inicia en 0
                        break;
                    }
                }
            }
        }

        if (oldGrupo != null) {
            comboGrupo.setSelectedIndex(oldGrupoIndex); // Selecciono
        }

        guardarModificaciónButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PracticaDTO practicaActDTO = practicasC.obtenerDatosPractica(codigo);

                practicaActDTO.setNombre(txtNombre.getText());
                practicaActDTO.setTiempoEstimado(Integer.valueOf(txtDemora.getText()));

                if (oldGrupoIndex == null || comboGrupo.getSelectedIndex() != oldGrupoIndex) {
                    if (oldGrupo != null) {
                        try {
                            practicasC.retirarPracticaDeSubPractica(practicaActDTO.getCodigo(), oldGrupo);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    String value = String.valueOf(comboGrupo.getSelectedItem());

                    if (!Objects.equals(value, "Sin grupo")) {
                        Integer cod = Integer.valueOf(value.split(" ")[0]);

                        try {
                            practicasC.agregarPracticaASubPractica(practicaActDTO.getCodigo(), cod);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                try {
                    practicasC.actualizarPractica(practicaActDTO);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                frame.dispose();

                try {
                    new PracticasUI();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });


        modificarReglasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    try {
                        new ReglaUI(practicaDTO.getCodigo());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
