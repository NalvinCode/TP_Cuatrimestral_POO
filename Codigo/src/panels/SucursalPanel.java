package panels;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import Controllers.SucursalController;
import normalClasses.Sucursal;
import normalClasses.Usuario;

public class SucursalPanel extends JPanel {
    private JTextField numeroField, direccionField;
    private JComboBox<Usuario> responsableCombo;
    private JButton addButton, updateButton, deleteButton, fetchButton;
    private JList<Sucursal> sucursalList;
    private DefaultListModel<Sucursal> listModel;
    private SucursalController sucursalController;

    public SucursalPanel(SucursalController sucursalController) {
        this.sucursalController = sucursalController;
        initializeUI();
        setupListeners();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        sucursalList = new JList<>(listModel);
        add(new JScrollPane(sucursalList), BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(0, 2));

        formPanel.add(new JLabel("Número:"));
        numeroField = new JTextField();
        formPanel.add(numeroField);

        formPanel.add(new JLabel("Dirección:"));
        direccionField = new JTextField();
        formPanel.add(direccionField);

        formPanel.add(new JLabel("Responsable:"));
        // Suponiendo que tienes una manera de listar usuarios disponibles:
        responsableCombo = new JComboBox<>();  // Deberías llenarlo con los usuarios disponibles
        formPanel.add(responsableCombo);

        add(formPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Agregar");
        updateButton = new JButton("Actualizar");
        deleteButton = new JButton("Eliminar");
        fetchButton = new JButton("Buscar por Número");
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(fetchButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupListeners() {
        addButton.addActionListener(this::addSucursal);
        updateButton.addActionListener(this::updateSucursal);
        deleteButton.addActionListener(this::deleteSucursal);
        fetchButton.addActionListener(this::fetchSucursal);
    }

    private void addSucursal(ActionEvent e) {
        try {
            long numero = Long.parseLong(numeroField.getText());
            String direccion = direccionField.getText();
            Usuario responsable = (Usuario) responsableCombo.getSelectedItem();

            Sucursal sucursal = new Sucursal(numero, responsable, direccion);
            sucursalController.altaSucursal(sucursal);
            listModel.addElement(sucursal);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar sucursal: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateSucursal(ActionEvent e) {
        int selectedIndex = sucursalList.getSelectedIndex();
        if (selectedIndex != -1) {
            try {
                Sucursal selectedSucursal = sucursalList.getSelectedValue();
                selectedSucursal.setDireccion(direccionField.getText());
                selectedSucursal.setResponsable((Usuario) responsableCombo.getSelectedItem());

                sucursalController.modificarSucursal(selectedSucursal.getNumero(), selectedSucursal);
                listModel.set(selectedIndex, selectedSucursal);  // Update the JList
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al actualizar sucursal: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteSucursal(ActionEvent e) {
        int selectedIndex = sucursalList.getSelectedIndex();
        if (selectedIndex != -1) {
            try {
                Sucursal selectedSucursal = sucursalList.getSelectedValue();
                sucursalController.bajaSucursal(selectedSucursal.getNumero(), null); // Asignar null o lógica para reubicación
                listModel.removeElementAt(selectedIndex);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar sucursal: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void fetchSucursal(ActionEvent e) {
        try {
            long numero = Long.parseLong(numeroField.getText());
            Sucursal sucursal = sucursalController.obtenerSucursal(numero);
            if (sucursal != null) {
                listModel.clear();
                listModel.addElement(sucursal);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar sucursal: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
