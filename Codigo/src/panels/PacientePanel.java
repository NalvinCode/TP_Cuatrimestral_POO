package panels;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import enums.Estado;
import enums.ObraSocial;
import normalClasses.Paciente;
import Controllers.PacienteController;
import normalClasses.Peticion;
import normalClasses.Resultado;

import java.time.LocalDateTime;


public class PacientePanel extends JPanel {
    private JTextField dniField, nameField, domicilioField, mailField, edadField;
    private JComboBox<Character> sexoCombo;
    private JComboBox<ObraSocial> obraSocialCombo;
    private JButton addButton, updateButton, deleteButton, fetchButton, listAllButton, addPeticionButton;
    private JList<Paciente> pacienteList;
    private DefaultListModel<Paciente> listModel;
    private PacienteController pacienteController;



    public PacientePanel(PacienteController pacienteController) {
        this.pacienteController = pacienteController;
        initializeUI();
        setupListeners();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        pacienteList = new JList<>(listModel);
        add(new JScrollPane(pacienteList), BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(0, 2));

        formPanel.add(new JLabel("DNI:"));
        dniField = new JTextField();
        formPanel.add(dniField);

        formPanel.add(new JLabel("Nombre:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Domicilio:"));
        domicilioField = new JTextField();
        formPanel.add(domicilioField);

        formPanel.add(new JLabel("Correo Electrónico:"));
        mailField = new JTextField();
        formPanel.add(mailField);

        formPanel.add(new JLabel("Edad:"));
        edadField = new JTextField();
        formPanel.add(edadField);

        formPanel.add(new JLabel("Sexo:"));
        sexoCombo = new JComboBox<>(new Character[]{'M', 'F'});
        formPanel.add(sexoCombo);

        formPanel.add(new JLabel("Obra Social:"));
        obraSocialCombo = new JComboBox<>(ObraSocial.values());
        formPanel.add(obraSocialCombo);

        add(formPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Agregar");
        updateButton = new JButton("Actualizar");
        deleteButton = new JButton("Eliminar");
        fetchButton = new JButton("Buscar por DNI");
        listAllButton = new JButton("listar Todos");
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(fetchButton);
        buttonPanel.add(listAllButton);
        add(buttonPanel, BorderLayout.SOUTH);
        pacienteList = new JList<>(listModel);
        pacienteList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pacienteList.addListSelectionListener(this::onPacienteSelected);
        add(new JScrollPane(pacienteList), BorderLayout.CENTER);
    }



    private void onPacienteSelected(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            Paciente selectedPaciente = pacienteList.getSelectedValue();
            if (selectedPaciente != null) {
                showPacienteDetails(selectedPaciente);
            }
        }
    }

    private void updatePacienteList() {
        listModel.clear();  // Limpia la lista actual
        List<Paciente> todosLosPacientes = pacienteController.obtenerTodosLosPacientes();
        for (Paciente paciente : todosLosPacientes) {
            listModel.addElement(paciente);  // Añade cada paciente a la lista visual
        }
    }


    private void setupListeners() {
        addButton.addActionListener(this::addPaciente);
        updateButton.addActionListener(this::updatePaciente);
        deleteButton.addActionListener(this::deletePaciente);
        fetchButton.addActionListener(this::fetchPaciente);
        listAllButton.addActionListener(e -> updatePacienteList());
    }


    private void addPaciente(ActionEvent e) {
        try {
            int dni = Integer.parseInt(dniField.getText());
            String name = nameField.getText();
            String domicilio = domicilioField.getText();
            String mail = mailField.getText();
            int edad = Integer.parseInt(edadField.getText());
            char sexo = (char) sexoCombo.getSelectedItem();
            ObraSocial obraSocial = (ObraSocial) obraSocialCombo.getSelectedItem();

            Paciente paciente = new Paciente(dni, name, domicilio, mail, sexo, edad, obraSocial);
            pacienteController.altaPaciente(paciente);
            listModel.addElement(paciente);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar paciente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updatePaciente(ActionEvent e) {
        int selectedIndex = pacienteList.getSelectedIndex();
        if (selectedIndex != -1) {
            try {
                Paciente selectedPaciente = pacienteList.getSelectedValue();
                selectedPaciente.setName(nameField.getText());
                selectedPaciente.setDomicilio(domicilioField.getText());
                selectedPaciente.setMail(mailField.getText());
                selectedPaciente.setEdad(Integer.parseInt(edadField.getText()));
                selectedPaciente.setSexo((char) sexoCombo.getSelectedItem());
                selectedPaciente.setObraSocial((ObraSocial) obraSocialCombo.getSelectedItem());

                pacienteController.modificarPaciente(selectedPaciente.getDNI(), selectedPaciente);
                listModel.set(selectedIndex, selectedPaciente);  // Update the JList
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al actualizar paciente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showPacienteDetails(Paciente paciente) {
        if (paciente != null) {
            dniField.setText(String.valueOf(paciente.getDNI()));
            nameField.setText(paciente.getName());
            domicilioField.setText(paciente.getDomicilio());
            mailField.setText(paciente.getMail());
            edadField.setText(String.valueOf(paciente.getEdad()));
            sexoCombo.setSelectedItem(paciente.getSexo());
            obraSocialCombo.setSelectedItem(paciente.getObraSocial());
        }
    }

    private void deletePaciente(ActionEvent e) {
        int selectedIndex = pacienteList.getSelectedIndex();
        if (selectedIndex != -1) {
            try {
                Paciente selectedPaciente = pacienteList.getSelectedValue();
                pacienteController.bajaPaciente(selectedPaciente.getDNI());
                listModel.removeElementAt(selectedIndex);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar paciente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void fetchPaciente(ActionEvent e) {
        try {
            int dni = Integer.parseInt(dniField.getText());
            Paciente paciente = pacienteController.obtenerPaciente(dni);
            if (paciente != null) {
                listModel.clear();
                listModel.addElement(paciente);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar paciente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}