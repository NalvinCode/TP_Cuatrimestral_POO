package panels;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import Controllers.PeticionController;
import enums.Estado;
import enums.ObraSocial;
import normalClasses.Peticion;
import normalClasses.Resultado;

public class PeticionPanel extends JPanel {
    private JComboBox<ObraSocial> obraSocialComboBox;
    private JTextField fechaCargaField, fechaEntregaField, idField;
    private JComboBox<Estado> estadoComboBox;
    private JButton addButton, updateButton, deleteButton, fetchByIdButton;
    private JList<Peticion> peticionesList;
    private DefaultListModel<Peticion> listModel;
    private PeticionController peticionController;

    public PeticionPanel(PeticionController sucursalController) {
        this.peticionController = peticionController;
        initializeUI();
        setupListeners();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(0, 2));

        formPanel.add(new JLabel("ID (para actualizar/eliminar):"));
        idField = new JTextField();
        formPanel.add(idField);

        obraSocialComboBox = new JComboBox<>(ObraSocial.values());
        formPanel.add(new JLabel("Obra Social:"));
        formPanel.add(obraSocialComboBox);

        fechaCargaField = new JTextField();
        formPanel.add(new JLabel("Fecha de Carga (yyyy-MM-ddTHH:mm):"));
        formPanel.add(fechaCargaField);

        fechaEntregaField = new JTextField();
        formPanel.add(new JLabel("Fecha de Entrega (yyyy-MM-ddTHH:mm):"));
        formPanel.add(fechaEntregaField);

        estadoComboBox = new JComboBox<>(Estado.values());
        formPanel.add(new JLabel("Estado:"));
        formPanel.add(estadoComboBox);

        add(formPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Agregar");
        updateButton = new JButton("Actualizar");
        deleteButton = new JButton("Eliminar");
        fetchByIdButton = new JButton("Buscar por ID");
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(fetchByIdButton);
        add(buttonPanel, BorderLayout.SOUTH);

        listModel = new DefaultListModel<>();
        peticionesList = new JList<>(listModel);
        add(new JScrollPane(peticionesList), BorderLayout.CENTER);
    }

    private void setupListeners() {
        addButton.addActionListener(this::addPeticion);
        updateButton.addActionListener(this::updatePeticion);
        deleteButton.addActionListener(this::deletePeticion);
        fetchByIdButton.addActionListener(this::fetchPeticionById);
    }

    private void addPeticion(ActionEvent e) {
        try {
            ObraSocial obraSocial = (ObraSocial) obraSocialComboBox.getSelectedItem();
            LocalDateTime fechaCarga = LocalDateTime.parse(fechaCargaField.getText(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            LocalDateTime fechaEntrega = LocalDateTime.parse(fechaEntregaField.getText(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            Estado estado = (Estado) estadoComboBox.getSelectedItem();
            ArrayList<Resultado> resultados = new ArrayList<>();  // Suponiendo que necesitas manejar los resultados de alguna manera

            Peticion peticion = new Peticion(obraSocial, fechaCarga, fechaEntrega, estado, resultados);
            peticionController.altaPeticion(peticion);
            listModel.addElement(peticion);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar petición: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updatePeticion(ActionEvent e) {
        try {
            int id = Integer.parseInt(idField.getText());
            ObraSocial obraSocial = (ObraSocial) obraSocialComboBox.getSelectedItem();
            LocalDateTime fechaCarga = LocalDateTime.parse(fechaCargaField.getText(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            LocalDateTime fechaEntrega = LocalDateTime.parse(fechaEntregaField.getText(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            Estado estado = (Estado) estadoComboBox.getSelectedItem();
            ArrayList<Resultado> resultados = new ArrayList<>();  // Suponiendo que necesitas manejar los resultados de alguna manera

            Peticion peticion = new Peticion(obraSocial, fechaCarga, fechaEntrega, estado, resultados);
            peticionController.modificarPeticion(id, peticion);
            refreshPeticionesList();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar petición: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletePeticion(ActionEvent e) {
        try {
            int id = Integer.parseInt(idField.getText());
            peticionController.bajaPeticion(id);
            refreshPeticionesList();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar petición: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fetchPeticionById(ActionEvent e) {
        try {
            int id = Integer.parseInt(idField.getText());
            Peticion peticion = peticionController.obtenerPeticion(id);
            listModel.clear();
            if (peticion != null) {
                listModel.addElement(peticion);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar petición: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshPeticionesList() {
        listModel.clear();
        for (Peticion peticion : peticionController.obtenerPeticionesPorEstado((Estado) estadoComboBox.getSelectedItem())) {
            listModel.addElement(peticion);
        }
    }
}
