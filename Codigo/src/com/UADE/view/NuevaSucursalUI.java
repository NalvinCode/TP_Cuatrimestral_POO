package com.UADE.view;

import com.UADE.base.Singleton;
import com.UADE.controller.SucursalController;
import com.UADE.controller.UsuarioController;
import com.UADE.dto.SucursalDTO;
import com.UADE.dto.UsuarioDTO;
import com.UADE.enums.RolSistema;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class NuevaSucursalUI {
    private JPanel panel1;
    private JButton guardarButton;
    private JTextField txtDireccion;
    private JTextField txtTelefono;
    private JComboBox comboBoxResponsable;
    private final SucursalController sucursalc;
    private final UsuarioController usuarioC;

    public NuevaSucursalUI() throws Exception {
        JFrame frame = new JFrame("Nueva sucursal");
        panel1.setBorder(new EmptyBorder(15, 15, 15, 15));
        frame.setContentPane(panel1);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        sucursalc = Singleton.getInstance().sucursalController;
        usuarioC = Singleton.getInstance().usuarioController;

        List<UsuarioDTO> usuarios = usuarioC.obtenerListaUsuarios();

        for(UsuarioDTO user : usuarios){
            if(user.getRolSistema() == RolSistema.ADMINISTRADOR){
                comboBoxResponsable.addItem(user.getCodigo() + " " + user.getNombreUsuario());
            }
        }

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String validFields = validateFields();

                if(validFields != null){
                    JOptionPane.showMessageDialog(null, validFields, "Error", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                Integer codigoNuevaSuc = null;

                String valueU = (String) comboBoxResponsable.getSelectedItem();
                Integer codU = Integer.valueOf(valueU.split(" ")[0]);

                try {
                    codigoNuevaSuc = sucursalc.nuevaSucursal(new SucursalDTO(null, txtDireccion.getText(), txtTelefono.getText(), codU));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                JOptionPane.showMessageDialog(null, "Se ha creado la sucursal " + codigoNuevaSuc, "Nueva sucursal creada", JOptionPane.INFORMATION_MESSAGE);

                try {
                    new SucursalesUI();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                frame.dispose();
            }
        });
    }

    private String validateFields(){
        String campo = null;

        if(comboBoxResponsable.getSelectedItem() == null){
            campo = "Responsable Técnico";
        }
        if(txtTelefono.getText().isEmpty()){
            campo = "Telefono";
        }
        if(txtDireccion.getText().isEmpty()){
            campo = "Dirección";
        }

        if(campo != null){
            return "Por favor ingrese el campo " + campo;
        }

        try{
            Integer.valueOf(txtTelefono.getText());
        }catch (NumberFormatException e){
            return "Ingrese un Telefono numérico";
        }

        return null;
    }
}
