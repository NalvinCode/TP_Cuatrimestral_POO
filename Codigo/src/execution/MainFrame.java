package execution;
import javax.swing.*;
import java.awt.*;

import Controllers.*;
import panels.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        super("Sistema de Gestión Hospitalaria");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        initializeUI();
        setVisible(true);
    }

    PacienteController pacienteController = new PacienteController();
    PracticaController practicaController = new PracticaController();
    PeticionController peticionController = new PeticionController();
    SucursalController sucursalController = new SucursalController();
    UsuarioController usuarioController = new UsuarioController();

    private void initializeUI() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Pacientes", new PacientePanel(pacienteController));
        tabbedPane.add("Peticiones", new PeticionPanel(peticionController));
        tabbedPane.add("Prácticas", new PracticaPanel(practicaController));
        tabbedPane.add("Sucursales", new SucursalPanel(sucursalController));
        tabbedPane.add("Usuarios", new UsuarioPanel(usuarioController));
        add(tabbedPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
