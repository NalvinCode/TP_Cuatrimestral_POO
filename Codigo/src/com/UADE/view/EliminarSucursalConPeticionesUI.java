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
    private JComboBox<String> comboSucursales;
    private JButton confirmarButton;

    public EliminarSucursalConPeticionesUI(Integer codSucursal) throws Exception {
        JFrame frame = new JFrame("Eliminar sucursal " + codSucursal);
        panel1.setBorder(new EmptyBorder(15, 15, 15, 15));
        frame.setContentPane(panel1);
        frame.setSize(350, 300);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        succ = Singleton.getInstance().sucursalController;
        petc = Singleton.getInstance().peticionController;

        List<SucursalDTO> listasuc = succ.obtenerListaSucursales();

        for (SucursalDTO suc : listasuc) {
            if (suc.getCodigo().intValue() != codSucursal.intValue()) {
                comboSucursales.addItem(suc.getCodigo() + " " + suc.getDireccion());
            }
        }

        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(comboSucursales.getSelectedItem() == null){
                        JOptionPane.showMessageDialog(null, "Elija una sucursal donde migrar las peticiones.", "Error", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    String value = String.valueOf(comboSucursales.getSelectedItem());
                    Integer cod = Integer.valueOf(value.split(" ")[0]);

                    petc.migrarSucursalPeticiones(codSucursal, cod);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    succ.eliminarSucursal(codSucursal);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                frame.dispose();

                try {
                    new SucursalesUI();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
