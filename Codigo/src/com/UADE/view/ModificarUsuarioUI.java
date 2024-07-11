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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ModificarUsuarioUI {
    private final UsuarioController usuc;
    private final SucursalController succ;
    private JPanel panel1;
    private JTextField txtUsuario;
    private JTextField txtClave;
    private JTextField txtNombre;
    private JTextField txtDNI;
    private JTextField txtEmail;
    private JTextField txtDomicilio;
    private JTextField txtNacimiento;
    private JComboBox<RolSistema> comboRol;
    private JButton guardarButton;

    public ModificarUsuarioUI(Integer code) throws Exception {

        JFrame frame = new JFrame("Modificar usuario");
        panel1.setBorder(new EmptyBorder(15, 15, 15, 15));
        frame.setContentPane(panel1);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        usuc = Singleton.getInstance().usuarioController;
        succ = Singleton.getInstance().sucursalController;

        UsuarioDTO usermod = usuc.buscarUsuarioPorCodigo(code);

        txtUsuario.setText(usermod.getNombreUsuario());
        txtDNI.setText(String.valueOf(usermod.getDni()));
        txtDomicilio.setText(usermod.getDomicilio());
        txtEmail.setText(usermod.getEmail());
        txtNacimiento.setText(new SimpleDateFormat("dd/MM/yyyy").format(usermod.getFechaDeNacimiento()));
        txtNombre.setText(usermod.getNombreCompleto());
        txtClave.setText("");

        for (RolSistema rol : RolSistema.values()) {
            comboRol.addItem(rol);
        }

        comboRol.setSelectedItem(usermod.getRolSistema());

        List<SucursalDTO> listasuc = succ.obtenerListaSucursales();

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String validFields = validateFields();

                if(validFields != null){
                    JOptionPane.showMessageDialog(null, validFields, "Error", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                usermod.setNombreCompleto(txtNombre.getText());
                usermod.setDni(Integer.valueOf(txtDNI.getText()));
                try {
                    usermod.setFechaDeNacimiento(new SimpleDateFormat("dd/MM/yyyy").parse(txtNacimiento.getText()));
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
                usermod.setEmail(txtEmail.getText());
                usermod.setDomicilio(txtDomicilio.getText());
                usermod.setRolSistema((RolSistema) comboRol.getSelectedItem());

                Boolean result = null;
                try {
                    result = usuc.actualizarUsuario(usermod);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                if (result == null) {
                    JOptionPane.showMessageDialog(null, "Datos invàlidos.", "Error", JOptionPane.INFORMATION_MESSAGE);
                } else if (!result) {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error.", "Error", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Se ha actualizado el usuario " + txtUsuario.getText(), "Usuario modificado", JOptionPane.INFORMATION_MESSAGE);

                    frame.dispose();

                    try {
                        new UsuariosUI();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    private String validateFields(){
        String campo = null;

        if(comboRol.getSelectedItem() == null){
            campo = "Rol";
        }
        if(txtDomicilio.getText().isEmpty()){
            campo = "Domicilio";
        }
        if(txtEmail.getText().isEmpty()){
            campo = "Email";
        }
        if(txtNacimiento.getText().isEmpty()){
            campo = "Fecha de nacimiento";
        }
        if(txtDNI.getText().isEmpty()){
            campo = "DNI";
        }
        if(txtNombre.getText().isEmpty()){
            campo = "Nombre Completo";
        }

        if(campo != null){
            return "Por favor ingrese el campo " + campo;
        }

        try{
            Integer.valueOf(txtDNI.getText());
        }catch (NumberFormatException e){
            return "Ingrese un DNI numerico";
        }

        try {
            new SimpleDateFormat("dd/MM/yyyy").parse(txtNacimiento.getText());
        } catch (ParseException ex) {
            return "El formato de fecha de nacimiento no es correcto. Utilice dd/mm/aaaa";
        }

        if(!txtEmail.getText().contains("@")){
            return "Ingrese un mail valido";
        }

        return null;
    }
}
