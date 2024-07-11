package com.UADE.view;

import com.UADE.base.Singleton;
import com.UADE.controller.UsuarioController;
import com.UADE.dto.UsuarioDTO;
import com.UADE.enums.RolSistema;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class NuevoUsuarioUI {
    private JButton guardarButton;
    private JPanel panel1;
    private JTextField txtUsuario;
    private JTextField txtNombre;
    private JTextField txtClave;
    private JTextField txtDNI;
    private JTextField txtEmail;
    private JTextField txtDomicilio;
    private JComboBox<RolSistema> comboRol;
    private JTextField txtNacimiento;

    private final UsuarioController usuc;

    public NuevoUsuarioUI() throws Exception {
        JFrame frame = new JFrame("Nuevo usuario");
        panel1.setBorder(new EmptyBorder(15, 15, 15, 15));
        frame.setContentPane(panel1);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        for (RolSistema rol : RolSistema.values()) {
            comboRol.addItem(rol);
        }

        usuc = Singleton.getInstance().usuarioController;

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String validFields = validateFields();

                if(validFields != null){
                    JOptionPane.showMessageDialog(null, validFields, "Error", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                Integer result = null;
                UsuarioDTO newuser;

                try {
                    newuser = new UsuarioDTO(null, txtUsuario.getText(), txtClave.getText(), txtEmail.getText(), txtNombre.getText(), txtDomicilio.getText(), Integer.valueOf(txtDNI.getText()), new SimpleDateFormat("dd/MM/yyyy").parse(txtNacimiento.getText()), (RolSistema) comboRol.getSelectedItem());
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }

                try {
                    result = usuc.nuevoUsuario(newuser);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                if (result == null) {
                    JOptionPane.showMessageDialog(null, "El usuario ingresado ya existe.", "Error", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Se ha creado el usuario " + txtUsuario.getText(), "Nuevo usuario creado", JOptionPane.INFORMATION_MESSAGE);

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
        if(txtClave.getText().isEmpty()){
            campo = "Clave";
        }
        if(txtUsuario.getText().isEmpty()){
            campo = "Nombre de usuario";
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
