package com.UADE.view;

import com.UADE.base.Singleton;
import com.UADE.controller.UsuarioController;
import com.UADE.dto.UsuarioDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UsuariosUI {
    private JList<String> listUsuarios;
    private JPanel panel1;
    private JButton nuevoUsuarioButton;
    private JButton modificarUsuarioButton;
    private JButton borrarUsuarioButton;
    private final UsuarioController usuc;

    public UsuariosUI() throws Exception {
        JFrame frame = new JFrame("Gestión de Usuarios");
        panel1.setBorder(new EmptyBorder(15, 15, 15, 15));
        frame.setContentPane(panel1);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listUsuarios.setModel(listModel);

        usuc = Singleton.getInstance().usuarioController;

        List<UsuarioDTO> lista = usuc.obtenerListaUsuarios();

        for (UsuarioDTO i : lista)
            listModel.addElement(i.getCodigo() + " " + i.getNombreUsuario());

        nuevoUsuarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();

                try {
                    new NuevoUsuarioUI();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        borrarUsuarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String value = listUsuarios.getSelectedValue();
                    Integer cod = Integer.valueOf(value.split(" ")[0]);

                    usuc.borrarUsuario(cod);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                frame.dispose();

                try {
                    new UsuariosUI();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        modificarUsuarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String value = listUsuarios.getSelectedValue();
                    Integer cod = Integer.valueOf(value.split(" ")[0]);

                    new ModificarUsuarioUI(cod);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                frame.dispose();
            }
        });
    }
}
