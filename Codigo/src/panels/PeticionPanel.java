package panels;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import Controllers.PeticionController;
import enums.Estado;
import enums.ObraSocial;
import normalClasses.Peticion;
import normalClasses.Practica;
import normalClasses.Resultado;
import Controllers.PracticaController;

public class PeticionPanel extends JPanel {
    private JComboBox<ObraSocial> obraSocialComboBox;
    private JTextField fechaEntregaField, idField, dnifield;
    private JComboBox<Estado> estadoComboBox;
    private JButton addButton, updateButton, deleteButton, fetchByIdButton, addResultButton, viewResultsButton, assignPracticaButton;
    private JButton listAllButton;
    private JList<Peticion> peticionesList;
    private DefaultListModel<Peticion> listModel;
    private PeticionController peticionController;
    private PracticaController practicaController;


    public PeticionPanel(PeticionController peticionController,PracticaController practicaController) {
        this.peticionController = peticionController;
        this.practicaController = practicaController;
        initializeUI();
        setupListeners();
    }


    private void initializeUI() {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(0, 2));

        formPanel.add(new JLabel("DNI:"));
        dnifield = new JTextField();
        formPanel.add(dnifield);

        formPanel.add(new JLabel("ID (para actualizar/eliminar):"));
        idField = new JTextField();
        formPanel.add(idField);

        obraSocialComboBox = new JComboBox<>(ObraSocial.values());
        formPanel.add(new JLabel("Obra Social:"));
        formPanel.add(obraSocialComboBox);


        fechaEntregaField = new JTextField();
        formPanel.add(new JLabel("Fecha de Entrega (yyyy-MM-ddTHH:mm):"));
        formPanel.add(fechaEntregaField);

        estadoComboBox = new JComboBox<>(Estado.values());
        formPanel.add(new JLabel("Estado:"));
        formPanel.add(estadoComboBox);

        add(formPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Agregar");
        addResultButton = new JButton("Añadir Resultado");
        assignPracticaButton = new JButton("Asignar Práctica");
        updateButton = new JButton("Actualizar");
        deleteButton = new JButton("Eliminar");
        fetchByIdButton = new JButton("Buscar por ID");
        listAllButton = new JButton("Listar todas las peticiones");
        viewResultsButton = new JButton("Ver Resultados");
        buttonPanel.add(viewResultsButton);
        // crea un boton para listar todas las peticiones


        buttonPanel.add(assignPracticaButton);
        buttonPanel.add(addResultButton);
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(fetchByIdButton);
        buttonPanel.add(listAllButton);
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
        listAllButton.addActionListener(e -> refreshPeticionesList());
        addResultButton.addActionListener(this::openAddResultDialog);
        viewResultsButton.addActionListener(this::viewResults);
        assignPracticaButton.addActionListener(this::assignPractices);
    }

    private void assignPractices(ActionEvent e) {
        Peticion selectedPeticion = peticionesList.getSelectedValue();
        if (selectedPeticion == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una petición para asignar prácticas.", "Ninguna petición seleccionada", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog practicesDialog = new JDialog();
        practicesDialog.setTitle("Asignar Prácticas");
        practicesDialog.setSize(400, 300);
        practicesDialog.setLayout(new BorderLayout());

        DefaultListModel<Practica> allPracticesModel = new DefaultListModel<>();
        practicaController.getPracticas().forEach(allPracticesModel::addElement);
        JList<Practica> practicesList = new JList<>(allPracticesModel);
        practicesDialog.add(new JScrollPane(practicesList), BorderLayout.CENTER);

        JButton saveButton = new JButton("Asignar Seleccionadas");
        saveButton.addActionListener(ev -> {
            List<Practica> selectedPractices = practicesList.getSelectedValuesList();
            selectedPeticion.getPracticas().addAll(selectedPractices);
            practicesDialog.dispose();
            JOptionPane.showMessageDialog(null, "Prácticas asignadas correctamente.");
        });

        practicesDialog.add(saveButton, BorderLayout.SOUTH);
        practicesDialog.setLocationRelativeTo(this);
        practicesDialog.setVisible(true);
    }



    private void openAddResultDialog(ActionEvent e) {
        Peticion selectedPeticion = peticionesList.getSelectedValue();
        if (selectedPeticion == null) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una petición para añadir un resultado.", "Ninguna petición seleccionada", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog resultDialog = new JDialog();
        resultDialog.setTitle("Añadir Resultado");
        resultDialog.setSize(400, 300);
        resultDialog.setLayout(new GridLayout(0, 2));

        JTextField resultadoField = new JTextField();
        JTextField comentariosField = new JTextField();
        JComboBox<Estado> estadoComboBox = new JComboBox<>(Estado.values());

        DefaultListModel<Practica> allPracticesModel = new DefaultListModel<>();
        selectedPeticion.getPracticas().forEach(allPracticesModel::addElement);
        JList<Practica> practicesList = new JList<>(allPracticesModel);
        practicesList.setSize(200, 200);


        resultDialog.add(new JLabel("Resultado:"));
        resultDialog.add(resultadoField);

        resultDialog.add(new JLabel("Comentarios:"));
        resultDialog.add(comentariosField);

        resultDialog.add(new JLabel("Estado:"));
        resultDialog.add(estadoComboBox);

        resultDialog.add(new JLabel("Practica:"));
        resultDialog.add(new JScrollPane(practicesList));

        JButton saveResultButton = new JButton("Guardar Resultado");
        resultDialog.add(saveResultButton);

        saveResultButton.addActionListener(ev -> {
            try {
                String resultadoText = resultadoField.getText();
                String comentarios = comentariosField.getText();
                LocalDateTime fechaCarga = LocalDateTime.now();
                Estado estado = (Estado) estadoComboBox.getSelectedItem();
                Practica practica = practicesList.getSelectedValue();

                Resultado nuevoResultado = new Resultado(comentarios, fechaCarga, estado, resultadoText, practica);
                selectedPeticion.getResultados().add(nuevoResultado);

                listModel.setElementAt(selectedPeticion, peticionesList.getSelectedIndex()); // Refresca la lista
                JOptionPane.showMessageDialog(resultDialog, "Resultado añadido exitosamente.");
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(resultDialog, "Formato de fecha inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(resultDialog, "Error al añadir resultado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            resultDialog.dispose();
        });

        resultDialog.setLocationRelativeTo(this);
        resultDialog.setVisible(true);
    }



    private void viewResults(ActionEvent e) {
        Peticion selectedPeticion = peticionesList.getSelectedValue();
        if (selectedPeticion == null) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una petición para ver los resultados.", "Ninguna petición seleccionada", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog resultsDialog = new JDialog();
        resultsDialog.setTitle("Resultados de la Petición");
        resultsDialog.setSize(400, 300);
        resultsDialog.setLayout(new BorderLayout());

        DefaultListModel<Resultado> resultListModel = new DefaultListModel<>();
        for (Resultado resultado : selectedPeticion.getResultados()) {
            resultListModel.addElement(resultado);
        }

        JList<Resultado> resultsList = new JList<>(resultListModel);
        resultsDialog.add(new JScrollPane(resultsList), BorderLayout.CENTER);

        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(ev -> resultsDialog.dispose());
        resultsDialog.add(closeButton, BorderLayout.SOUTH);

        resultsDialog.setLocationRelativeTo(this);
        resultsDialog.setVisible(true);
    }





    private int currentId = 0;

    // other methods...

    private int generateId() {
        return currentId++;
    }

    private void addPeticion(ActionEvent e) {
        try {
            ObraSocial obraSocial = (ObraSocial) obraSocialComboBox.getSelectedItem();
            LocalDateTime fechaCarga = LocalDateTime.now();
            LocalDateTime fechaEntrega = LocalDateTime.parse(fechaEntregaField.getText(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            Estado estado = (Estado) estadoComboBox.getSelectedItem();
            ArrayList<Resultado> resultados = new ArrayList<>();  // Suponiendo que necesitas manejar los resultados de alguna manera
            List<Practica> practica = new ArrayList<>();
            int id = generateId(); // Necesitas implementar este método para generar un ID único
            Peticion peticion = new Peticion(obraSocial, id, practica, fechaCarga, fechaEntrega, estado, resultados);
            peticionController.altaPeticion(peticion);
            listModel.addElement(peticion);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar petición: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updatePeticion(ActionEvent e) {
        try {
            Peticion selectedPeticion = peticionesList.getSelectedValue();
            int id = Integer.parseInt(idField.getText());
            ObraSocial obraSocial = (ObraSocial) obraSocialComboBox.getSelectedItem();
            LocalDateTime fechaEntrega = LocalDateTime.parse(fechaEntregaField.getText(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            Estado estado = (Estado) estadoComboBox.getSelectedItem();
            ArrayList<Resultado> resultados = new ArrayList<>();
            List<Practica> practica = new ArrayList<>();
            Peticion peticion = new Peticion(obraSocial, id, practica, selectedPeticion.getFechaCarga(), fechaEntrega, estado, resultados);
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