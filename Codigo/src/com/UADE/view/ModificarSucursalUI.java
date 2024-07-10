package com.UADE.view;

import com.UADE.base.Singleton;
import com.UADE.controller.SucursalController;
import com.UADE.controller.UsuarioController;
import com.UADE.dto.SucursalDTO;
import com.UADE.dto.UsuarioDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ModificarSucursalUI {
    private JButton guardarButton;
    private JPanel panel1;
    private JTextField txtDirecion;
    private JTextField txtTelefono;
    private JComboBox comboBoxResponsable;
    private final SucursalController sucursalc;
    private final UsuarioController usuarioC;

    public ModificarSucursalUI(Integer codigo) throws Exception {
        JFrame frame = new JFrame("Modificar sucursal " + codigo);
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
            comboBoxResponsable.addItem(user.getCodigo() + " " + user.getNombreUsuario());
        }

        SucursalDTO sucdto = sucursalc.obtenerDatosSucursal(codigo);

        txtDirecion.setText(sucdto.getDireccion());
        txtTelefono.setText(sucdto.getTelefono());

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sucdto.setDireccion(txtDirecion.getText());
                    sucdto.setTelefono(txtTelefono.getText());

                    String valueU = (String) comboBoxResponsable.getSelectedItem();
                    Integer codU = Integer.valueOf(valueU.split(" ")[0]);
                    sucdto.setCodUsuarioRespTecnico(codU);

                    sucursalc.actualizarSucursal(sucdto);
                } catch (Exception ex) {
                    ex.printStackTrace();
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
