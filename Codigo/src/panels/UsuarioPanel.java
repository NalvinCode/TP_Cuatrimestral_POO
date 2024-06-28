package panels;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import Controllers.UsuarioController;
import enums.ObraSocial;
import enums.RolUsuario;
import normalClasses.Usuario;


public class UsuarioPanel extends JPanel {
    private JTextField nameField, domicilioField, mailField, userField, dniField, bornDateField;
    private JPasswordField passwordField;
    private JComboBox<RolUsuario> rolField;
    private JButton addButton, updateButton, deleteButton, fetchButton, listAllButton;
    private JList<Usuario> usuarioList;
    private DefaultListModel<Usuario> listModel;
    private UsuarioController usuarioController;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");



    public UsuarioPanel(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
        initializeUI();
        setupListeners();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        usuarioList = new JList<>(listModel);
        add(new JScrollPane(usuarioList), BorderLayout.CENTER);

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

        formPanel.add(new JLabel("Contraseña:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        formPanel.add(new JLabel("Usuario:"));
        userField = new JTextField();
        formPanel.add(userField);

        formPanel.add(new JLabel("Fecha de Nacimiento:"));
        bornDateField = new JTextField();
        formPanel.add(bornDateField);

        formPanel.add(new JLabel("Rol:"));
        rolField = new JComboBox<>(RolUsuario.values());
        formPanel.add(rolField);

        add(formPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Agregar");
        updateButton = new JButton("Actualizar");
        deleteButton = new JButton("Eliminar");
        fetchButton = new JButton("Buscar por DNI");
        listAllButton = new JButton("Listar Todos");
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(fetchButton);
        buttonPanel.add(listAllButton);
        add(buttonPanel, BorderLayout.SOUTH);
        usuarioList = new JList<>(listModel);
        usuarioList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        usuarioList.addListSelectionListener(this::onUsuarioSelected);
        add(new JScrollPane(usuarioList), BorderLayout.CENTER);
    }



    private void onUsuarioSelected(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            Usuario selectedUser = usuarioList.getSelectedValue();
            if (selectedUser != null) {
                showUsuarioDetails(selectedUser);
            }
        }
    }

    private void updateUsuarioList() {
        listModel.clear();  // Limpia la lista actual
        List<Usuario> todosLosUsuarios = usuarioController.listarUsuarios();
        for (Usuario usuario : todosLosUsuarios) {
            listModel.addElement(usuario);  // Añade cada usuario a la lista visual
        }
    }


    private void setupListeners() {
        addButton.addActionListener(this::addUsuario);
        updateButton.addActionListener(this::updateUsuario);
        deleteButton.addActionListener(this::deleteUsuario);
        fetchButton.addActionListener(this::fetchUsuario);
        listAllButton.addActionListener(e -> updateUsuarioList());
    }


    private void addUsuario(ActionEvent e) {
        try {
            String name = nameField.getText();
            int dni = Integer.parseInt(dniField.getText());
            String mail = mailField.getText();
            LocalDate fechaNacimiento = LocalDate.parse(bornDateField.getText(), formatter);
            String nombreUsuario = userField.getText();
            char[] password = passwordField.getPassword();
            String domicilio = domicilioField.getText();
            RolUsuario rolUsuario = (RolUsuario) rolField.getSelectedItem();
            Usuario usuario = new Usuario(name, dni, mail, fechaNacimiento, nombreUsuario, password, domicilio, rolUsuario);
            usuarioController.altaUsuario(usuario);
            listModel.addElement(usuario);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateUsuario(ActionEvent e) {
        int selectedIndex = usuarioList.getSelectedIndex();
        if (selectedIndex != -1) {
            try {
                Usuario selectedUsuario = usuarioList.getSelectedValue();
                selectedUsuario.setName(nameField.getText());
                selectedUsuario.setDomicilio(domicilioField.getText());
                selectedUsuario.setMail(mailField.getText());
                selectedUsuario.setDNI(Integer.parseInt(dniField.getText()));
                selectedUsuario.setPassword(passwordField.getPassword());
                selectedUsuario.setNombreUsuario(userField.getText());
                selectedUsuario.setFechaNacimiento(LocalDate.parse(bornDateField.getText(), formatter));
                usuarioController.modificarUsuario(selectedUsuario.getDNI(), selectedUsuario);
                listModel.set(selectedIndex, selectedUsuario);  // Update the JList
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al actualizar usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showUsuarioDetails(Usuario usuario) {
        if (usuario != null) {
            dniField.setText(String.valueOf(usuario.getDNI()));
            nameField.setText(usuario.getName());
            domicilioField.setText(usuario.getDomicilio());
            mailField.setText(usuario.getMail());
            bornDateField.setText(String.valueOf(LocalDate.parse(bornDateField.getText(), formatter)));
            userField.setText(usuario.getNombreUsuario());
            rolField.setSelectedItem(usuario.getRolUsuario());
        }
    }

    private void deleteUsuario(ActionEvent e) {
        int selectedIndex = usuarioList.getSelectedIndex();
        if (selectedIndex != -1) {
            try {
                Usuario selectedUsuario = usuarioList.getSelectedValue();
                usuarioController.bajaUsuario(selectedUsuario.getDNI());
                listModel.removeElementAt(selectedIndex);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void fetchUsuario(ActionEvent e) {
        try {
            int dni = Integer.parseInt(dniField.getText());
            Usuario usuario = usuarioController.obtenerUsuario(dni);
            if (usuario != null) {
                listModel.clear();
                listModel.addElement(usuario);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}