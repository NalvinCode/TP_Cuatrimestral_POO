package panels;
import Controllers.PracticaController;
import normalClasses.Practica;
import normalClasses.ReglaPractica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;


public class PracticaPanel extends JPanel {
    private JTextField codigoField, nombreField, horasField;
    private JCheckBox inhabilitadoCheckBox;
    private JButton addButton, updateButton, deleteButton, fetchButton;
    private JList<Practica> practicaList;
    private DefaultListModel<Practica> listModel;
    private PracticaController practicaController;

    public PracticaPanel(PracticaController practicaController) {
        this.practicaController = practicaController;
        initializeUI();
        setupListeners();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(0, 2));

        formPanel.add(new JLabel("Código:"));
        codigoField = new JTextField();
        formPanel.add(codigoField);

        formPanel.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        formPanel.add(nombreField);

        formPanel.add(new JLabel("Cantidad de Horas:"));
        horasField = new JTextField();
        formPanel.add(horasField);

        formPanel.add(new JLabel("Inhabilitado:"));
        inhabilitadoCheckBox = new JCheckBox();
        formPanel.add(inhabilitadoCheckBox);

        add(formPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Agregar");
        updateButton = new JButton("Actualizar");
        deleteButton = new JButton("Eliminar");
        fetchButton = new JButton("Buscar por Código");
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(fetchButton);
        add(buttonPanel, BorderLayout.SOUTH);

        listModel = new DefaultListModel<>();
        practicaList = new JList<>(listModel);
        add(new JScrollPane(practicaList), BorderLayout.CENTER);
    }

    private void setupListeners() {
        addButton.addActionListener(this::addPractica);
        updateButton.addActionListener(this::updatePractica);
        deleteButton.addActionListener(this::deletePractica);
        fetchButton.addActionListener(this::fetchPractica);
    }

    private void addPractica(ActionEvent e) {
        try {
            int codigo = Integer.parseInt(codigoField.getText());
            String nombre = nombreField.getText();
            int horas = Integer.parseInt(horasField.getText());
            boolean inhabilitado = inhabilitadoCheckBox.isSelected();
            ArrayList<ReglaPractica> reglas = new ArrayList<>();  // Simulando, necesitas una manera de añadir reglas

            Practica practica = new Practica(codigo, nombre, horas, inhabilitado, reglas);
            practicaController.altaPractica(practica);
            listModel.addElement(practica);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar práctica: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updatePractica(ActionEvent e) {
        try {
            int codigo = Integer.parseInt(codigoField.getText());
            Practica practica = practicaController.obtenerPractica(codigo);
            practica.setName(nombreField.getText());
            practica.setCantidadHoras(Integer.parseInt(horasField.getText()));
            practica.setInhabilitado(inhabilitadoCheckBox.isSelected());
            // Aquí debes manejar las reglas asociadas si es necesario actualizarlas

            practicaController.modificarPractica(codigo, practica);
            listModel.set(practicaList.getSelectedIndex(), practica);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar práctica: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletePractica(ActionEvent e) {
        try {
            int codigo = Integer.parseInt(codigoField.getText());
            practicaController.bajaPractica(codigo);
            listModel.remove(practicaList.getSelectedIndex());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar práctica: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fetchPractica(ActionEvent e) {
        try {
            int codigo = Integer.parseInt(codigoField.getText());
            Practica practica = practicaController.obtenerPractica(codigo);
            listModel.clear();
            if (practica != null) {
                listModel.addElement(practica);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar práctica: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
