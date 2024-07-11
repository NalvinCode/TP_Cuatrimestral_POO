package com.UADE.view;

import com.UADE.base.Singleton;
import com.UADE.controller.PacienteController;
import com.UADE.dto.PacienteDTO;
import com.UADE.enums.Sexo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class ModificarPacienteUI {
    private JPanel panel1;
    private JTextField nroDni;
    private JTextField txtNombreCompleto;
    private JTextField txtDomicilio;
    private JTextField txtMail;
    private JTextField txtEdad;
    private JButton guardarButton;
    private JComboBox comboSexo;
    private final PacienteController pacientec;

    public ModificarPacienteUI(Integer codigo) throws Exception {
        pacientec = Singleton.getInstance().pacienteController;

        PacienteDTO pacdto = pacientec.obtenerPaciente(codigo);

        JFrame frame = new JFrame("Modificar paciente " + txtNombreCompleto.getText());
        panel1.setBorder(new EmptyBorder(15, 15, 15, 15));
        frame.setContentPane(panel1);
        frame.setSize(600, 300);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        List<Sexo> sexoList = Arrays.stream(Sexo.values()).toList();
        sexoList.forEach(s-> comboSexo.addItem(s));

        nroDni.setText(String.valueOf(pacdto.getDni()));
        txtNombreCompleto.setText(pacdto.getNombreCompleto());
        txtDomicilio.setText(pacdto.getDomicilio());
        txtMail.setText(pacdto.getEmail());
        txtEdad.setText(String.valueOf(pacdto.getEdad()));
        comboSexo.setSelectedItem(pacdto.getSexo());


        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String validFields = validateFields();

                if(validFields != null){
                    JOptionPane.showMessageDialog(null, validFields, "Error", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                pacdto.setDni(Integer.valueOf(nroDni.getText()));
                pacdto.setNombreCompleto(txtNombreCompleto.getText());
                pacdto.setDomicilio(txtDomicilio.getText());
                pacdto.setEmail(txtMail.getText());
                pacdto.setSexo((Sexo) comboSexo.getSelectedItem());
                pacdto.setEdad(Integer.valueOf(txtEdad.getText()));

                boolean pacAct = true;

                try {
                    pacAct = pacientec.actualizarPaciente(pacdto);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                if(!pacAct){
                    JOptionPane.showMessageDialog(null, "El DNI ingresado corresponde a un paciente ya registrado", "Error al crear Paciente", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(null, "Se ha modificado el paciente " + txtNombreCompleto.getText(), "Nuevo paciente creado", JOptionPane.INFORMATION_MESSAGE);

                frame.dispose();

                try {
                    new PacientesUI();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });


    }

    private String validateFields(){
        String campo = null;

        if(txtEdad.getText().isEmpty()){
            campo = "Edad";
        }
        if(comboSexo.getSelectedItem() == null){
            campo = "Domicilio";
        }
        if(txtMail.getText().isEmpty()){
            campo = "Mail";
        }
        if(txtDomicilio.getText().isEmpty()){
            campo = "Domicilio";
        }
        if(txtNombreCompleto.getText().isEmpty()){
            campo = "Nombre completo";
        }
        if(nroDni.getText().isEmpty()){
            campo = "DNI";
        }

        if(campo != null){
            return "Por favor ingrese el campo " + campo;
        }

        return null;
    }
}
