package com.UADE.view;

import com.UADE.base.Singleton;
import com.UADE.controller.PeticionController;
import com.UADE.controller.SucursalController;
import com.UADE.dto.SucursalDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EliminarSucursalConPeticionesUI {
    private final SucursalController succ;
    private final PeticionController petc;
    private JPanel panel1;
    private JComboBox<Integer> comboSucursales;
    private JButton confirmarButton;

    public EliminarSucursalConPeticionesUI(Integer codSucursal) throws Exception {
        JFrame frame = new JFrame("Eliminar sucursal " + codSucursal);
        panel1.setBorder(new EmptyBorder(15, 15, 15, 15));
        frame.setContentPane(panel1);
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        succ = Singleton.getInstance().sucursalController;
        petc = Singleton.getInstance().peticionController;

        List<SucursalDTO> listasuc = succ.obtenerListaSucursales();

        for (SucursalDTO suc : listasuc) {
            if (suc.getCodigo().intValue() != codSucursal.intValue()) {
                comboSucursales.addItem(suc.getCodigo());
            }
        }

        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    petc.migrarSucursalPeticiones(codSucursal, (Integer) comboSucursales.getSelectedItem());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                boolean result = false;

                try {
                    result = succ.eliminarSucursal(codSucursal);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                if (!result) {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar la sucursal. Verifique si tiene peticiones finalizadas.", "Error", JOptionPane.INFORMATION_MESSAGE);
                }

                frame.dispose();

                try {
                    new MaestroSucursalesUI();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
