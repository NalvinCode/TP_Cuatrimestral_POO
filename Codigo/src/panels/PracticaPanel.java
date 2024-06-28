package panels;
import Controllers.PracticaController;
import normalClasses.Practica;
import normalClasses.ReglaAlfa;
import normalClasses.ReglaNumerica;
import normalClasses.ReglaPractica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;


public class PracticaPanel extends JPanel {
    private JTextField codigoField, nombreField, horasField;
    private JCheckBox inhabilitadoCheckBox;
    private JButton addButton, updateButton, deleteButton, fetchButton, addruleButton;
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
        addruleButton = new JButton("Agregar Regla");
        updateButton = new JButton("Actualizar");
        deleteButton = new JButton("Eliminar");
        fetchButton = new JButton("Buscar por Código");
        buttonPanel.add(addruleButton);  // Agregar el botón de agregar regla (deberás implementar su lógica
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
        addruleButton.addActionListener(this::addRegla);
    }

    private void addRegla(ActionEvent e) {
        Practica selectedPractica = practicaList.getSelectedValue();
        if (selectedPractica == null) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una práctica para añadir una regla.", "Ninguna práctica seleccionada", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Creación del diálogo
        JDialog addReglaDialog = new JDialog();
        addReglaDialog.setTitle("Añadir Regla");
        addReglaDialog.setLayout(new GridLayout(0, 2));
        addReglaDialog.setSize(300, 200);

        // Elementos del UI
        JComboBox<String> tipoReglaComboBox = new JComboBox<>(new String[]{"Numérica", "Alfa"});
        JTextField valorCriticoField = new JTextField();
        JTextField valorReservadoField = new JTextField();
        JButton saveButton = new JButton("Guardar Regla");

        addReglaDialog.add(new JLabel("Tipo de Regla:"));
        addReglaDialog.add(tipoReglaComboBox);
        addReglaDialog.add(new JLabel("Valor Crítico:"));
        addReglaDialog.add(valorCriticoField);
        addReglaDialog.add(new JLabel("Valor Reservado:"));
        addReglaDialog.add(valorReservadoField);
        addReglaDialog.add(saveButton);

        // Listener para el botón guardar
        saveButton.addActionListener(ev -> {
            try {
                String tipoRegla = (String) tipoReglaComboBox.getSelectedItem();
                ReglaPractica regla = null;
                if ("Numérica".equals(tipoRegla)) {
                    float valorCritico = Float.parseFloat(valorCriticoField.getText());
                    float valorReservado = Float.parseFloat(valorReservadoField.getText());
                    regla = new ReglaNumerica(valorCritico, valorReservado);
                } else if ("Alfa".equals(tipoRegla)) {
                    String valorCritico = valorCriticoField.getText();
                    String valorReservado = valorReservadoField.getText();
                    regla = new ReglaAlfa(valorCritico, valorReservado);
                }

                if (regla != null) {
                    selectedPractica.getReglas().add(regla);
                    JOptionPane.showMessageDialog(addReglaDialog, "Regla añadida exitosamente.");
                    listModel.setElementAt(selectedPractica, practicaList.getSelectedIndex()); // Refrescar la lista
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(addReglaDialog, "Error en el formato de los valores numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(addReglaDialog, "Error al añadir regla: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            addReglaDialog.dispose();
        });

        addReglaDialog.setLocationRelativeTo(this);
        addReglaDialog.setVisible(true);
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