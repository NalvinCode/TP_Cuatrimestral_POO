package com.UADE.view;

import com.UADE.base.Singleton;
import com.UADE.controller.SucursalController;
import com.UADE.dto.SucursalDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MaestroSucursalesUI {
    private JList<String> listSucursales;
    private JPanel panel1;
    private JButton nuevaSucursalButton;
    private JButton modificarSucursalButton;
    private JButton borrarSucursalButton;
    private final SucursalController sucursalc;

    public MaestroSucursalesUI() throws Exception {
        JFrame frame = new JFrame("Maestro de Sucursales");
        panel1.setBorder(new EmptyBorder(15, 15, 15, 15));
        frame.setContentPane(panel1);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        DefaultListModel<String> listModel = new DefaultListModel<String>();
        listSucursales.setModel(listModel);

        sucursalc = Singleton.getInstance().sucursalController;

        List<SucursalDTO> lista = sucursalc.obtenerListaSucursales();

        for (SucursalDTO i : lista)
            listModel.addElement(i.getCodigo() + " - " + i.getDireccion());

        nuevaSucursalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();

                try {
                    new NuevaSucursalUI();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });

        borrarSucursalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String value = listSucursales.getSelectedValue();
                Integer cod = Integer.valueOf(value.split(" ")[0]);

                try {
                    if (sucursalc.sucursalTienePeticionesActivas(cod)) {
                        try {
                            new EliminarSucursalConPeticionesUI(cod);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        try {
                            sucursalc.eliminarSucursal(cod);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        try {
                            new MaestroSucursalesUI();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                frame.dispose();
            }
        });
        modificarSucursalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String value = listSucursales.getSelectedValue();
                Integer cod = Integer.valueOf(value.split(" ")[0]);

                try {
                    new ModificarSucursalUI(cod);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                frame.dispose();
            }
        });
    }
}
