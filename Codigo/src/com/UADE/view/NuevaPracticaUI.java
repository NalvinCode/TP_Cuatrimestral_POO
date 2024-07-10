package com.UADE.view;

import com.UADE.base.Singleton;
import com.UADE.controller.PracticaController;
import com.UADE.dto.ListaPracticasDTO;
import com.UADE.dto.PracticaDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class NuevaPracticaUI {
    private JPanel panel1;
    private JTextField txtNombre;
    private JTextField txtDemora;
    private JButton guardarPrácticaButton;
    private JComboBox<String> comboGrupo;
    private final PracticaController practicasC;

    NuevaPracticaUI() throws Exception {
        JFrame frame = new JFrame("Nueva práctica");
        panel1.setBorder(new EmptyBorder(15, 15, 15, 15));
        frame.setContentPane(panel1);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        practicasC = Singleton.getInstance().practicaController;

        List<ListaPracticasDTO> lista = practicasC.obtenerListaPracticasSimplificada();

        comboGrupo.addItem("Sin grupo");

        for (ListaPracticasDTO i : lista) {
            comboGrupo.addItem(i.getCodigo() + " " + i.getNombre());
        }

        guardarPrácticaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Integer codPr = null;

                try {
                    codPr = practicasC.nuevaPractica(new PracticaDTO(null, txtNombre.getText(), Integer.valueOf(txtDemora.getText()), new ArrayList<>(), new ArrayList<>()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                if (comboGrupo.getSelectedIndex() > 0) {
                    String value = String.valueOf(comboGrupo.getSelectedItem());
                    Integer cod = Integer.valueOf(value.split(" ")[0]);

                    try {
                        practicasC.agregarPracticaASubPractica(codPr, cod);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                try {
                    new ReglaUI(codPr);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                frame.dispose();

            }
        });
    }
}
