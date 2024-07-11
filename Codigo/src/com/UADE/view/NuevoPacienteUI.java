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

public class NuevoPacienteUI {
    private JPanel panel1;
    private JTextField txtNombreCompleto;
    private JTextField txtDomicilio;
    private JTextField txtMail;
    private JComboBox comboSexo;
    private JTextField txtEdad;
    private JButton guardarButton;
    private JTextField nroDni;
    private final PacienteController pacientec;

    public NuevoPacienteUI() throws Exception {
        JFrame frame = new JFrame("Nuevo Paciente");
        panel1.setBorder(new EmptyBorder(15, 15, 15, 15));
        frame.setContentPane(panel1);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        List<Sexo> sexoList = Arrays.stream(Sexo.values()).toList();
        sexoList.forEach(s-> comboSexo.addItem(s));

        pacientec = Singleton.getInstance().pacienteController;

        guardarButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String validFields = validateFields();

                if(validFields != null){
                    JOptionPane.showMessageDialog(null, validFields, "Error", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                Integer codigoNuevoPac = null;
                String nombrePac = null;

                try {
                    PacienteDTO pacdto = new PacienteDTO(null, Integer.valueOf(nroDni.getText()), txtNombreCompleto.getText(), txtDomicilio.getText(), txtMail.getText(), (Sexo) comboSexo.getSelectedItem(), Integer.valueOf(txtEdad.getText()));
                    codigoNuevoPac = pacientec.nuevoPaciente(pacdto);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                if(codigoNuevoPac == null){
                    JOptionPane.showMessageDialog(null, "El DNI ingresado corresponde a un paciente ya registrado", "Error al crear Paciente", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                nombrePac = txtNombreCompleto.getText();
                JOptionPane.showMessageDialog(null, "Se ha creado el paciente " + nombrePac, "Nuevo paciente creado", JOptionPane.INFORMATION_MESSAGE);

                try {
                    new PacientesUI();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                frame.dispose();
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

        try{
            Integer.valueOf(nroDni.getText());
        }catch (NumberFormatException e){
            return "Ingrese un DNI numerico";
        }

        try{
            Integer.valueOf(txtEdad.getText());
        }catch (NumberFormatException e){
            return "Ingrese una edad numerica";
        }

        return null;
    }
}
