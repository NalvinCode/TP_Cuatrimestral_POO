package com.UADE.view;

import com.UADE.base.Singleton;
import com.UADE.controller.PeticionController;
import com.UADE.controller.PracticaController;
import com.UADE.dto.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PeticionesCriticasUI {
    private final PeticionController peticionc;
    private final PracticaController practicac;
    private JPanel panel1;
    private JButton verPeticionButton;
    private JList<String> listaPeticiones;
    private PeticionController peticionController;

    public PeticionesCriticasUI() throws Exception {
        JFrame frame = new JFrame("Peticiones con valores criticos");
        panel1.setBorder(new EmptyBorder(15, 15, 15, 15));
        frame.setContentPane(panel1);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listaPeticiones.setModel(listModel);

        peticionc = Singleton.getInstance().peticionController;
        practicac = Singleton.getInstance().practicaController;

        List<PeticionDTO> lista = peticionc.obtenerListaPeticiones();

        for (PeticionDTO i : lista) {
            List<ResultadoPracticaDTO> resultados = peticionc.obtenerResultadosPeticion(i.getCodigo());
            boolean critico = false;

            for (ResultadoPracticaDTO r : resultados) {
                if(practicac.resultadoCritico(r.getCodPractica(), r)){
                    critico = true;
                    break;
                }
            }

            if (critico)
                listModel.addElement(i.getCodigo() + " " + i.getObraSocial() + " " + i.getFechaInicio());
        }

        verPeticionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String value = String.valueOf(listaPeticiones.getSelectedValue());
                    Integer cod = Integer.valueOf(value.split(" ")[0]);
                    new PeticionUI(cod);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
