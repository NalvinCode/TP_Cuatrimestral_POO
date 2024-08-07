package com.UADE.view;

import com.UADE.base.Singleton;
import com.UADE.controller.PacienteController;
import com.UADE.controller.PeticionController;
import com.UADE.controller.PracticaController;
import com.UADE.controller.SucursalController;
import com.UADE.dto.*;
import com.UADE.enums.EstadoPeticion;
import com.UADE.enums.RolSistema;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PeticionUI {
    private final PacienteController pacientec;
    private final PeticionController peticionc;
    private final PracticaController practicac;
    private final SucursalController sucursalc;
    private JButton cargarResultadosButton;
    private JPanel panel1;
    private JList<String> listPracticas;
    private JTable tableResultados;
    private JLabel lblPaciente;
    private JLabel lblFechaInicio;
    private JLabel lblFechaEntrega;
    private JLabel lblObraSocial;
    private JLabel lblEstado;
    private JButton enviarResultadosButton;
    private JLabel lblSucursal;
    private JButton finPetButton;
    private List<ResultadoPracticaDTO> resultados;
    private boolean reservado;

    public PeticionUI(Integer codigoPeticion) throws Exception {
        JFrame frame = new JFrame("Detalle de la petición " + codigoPeticion);
        panel1.setBorder(new EmptyBorder(15, 15, 15, 15));
        frame.setContentPane(panel1);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listPracticas.setModel(listModel);
        DefaultTableModel tableModel = new DefaultTableModel(0, 0);
        tableModel.setColumnIdentifiers(new String[]{"Practica", "Estado", "Resultado Numerico", "Resultado Literal"});
        tableResultados.setModel(tableModel);

        pacientec = Singleton.getInstance().pacienteController;
        peticionc = Singleton.getInstance().peticionController;
        practicac = Singleton.getInstance().practicaController;
        sucursalc = Singleton.getInstance().sucursalController;

        PeticionDTO petic = peticionc.obtenerDatosPeticion(codigoPeticion);

        if (Singleton.getInstance().rolSistema == RolSistema.RECEPCION) {
            cargarResultadosButton.setVisible(false);
        }

        enviarResultadosButton.setVisible(false);

        if(petic.getEstadoPeticion() == EstadoPeticion.FINALIZADO){
            finPetButton.setVisible(false);
            enviarResultadosButton.setVisible(true);
        }

        PacienteDTO pac = pacientec.obtenerPaciente(petic.getCodPaciente());
        resultados = peticionc.obtenerResultadosPeticion(codigoPeticion);
        SucursalDTO suc = sucursalc.obtenerDatosSucursal(petic.getCodSucursal());

        lblPaciente.setText(pac.getCodigo() + " " + pac.getNombreCompleto());
        lblObraSocial.setText(petic.getObraSocial());
        lblFechaInicio.setText(petic.getFechaInicio().toString());
        lblFechaEntrega.setText(petic.getFechaEntrega().toString());
        lblEstado.setText(petic.getEstadoPeticion().toString());
        lblSucursal.setText(suc.getCodigo() + " " + suc.getDireccion());

        for (Integer i : petic.getCodPracticas()) {
            PracticaDTO pracdto = practicac.obtenerDatosPractica(i);
            listModel.addElement(i + " " + pracdto.getNombre());
        }

        for (ResultadoPracticaDTO r : resultados) {
            PracticaDTO prac = practicac.obtenerDatosPractica(r.getCodPractica());
            tableModel.addRow(new Object[]{prac.getNombre(), r.getResultadoNumerico(), r.getResultadoLiteral(), r.getTranscription()});
        }

        reservado = practicac.tieneResultadoReservado(resultados);

        cargarResultadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String value = String.valueOf(listPracticas.getSelectedValue());
                Integer codPr = Integer.valueOf(value.split(" ")[0]);

                boolean existeRes = false;

                for(ResultadoPracticaDTO res : resultados){
                    if(res.getCodPractica().intValue() == codPr.intValue()){
                        existeRes = true;
                        break;
                    }
                }

                if(existeRes){
                    JOptionPane.showMessageDialog(null, "Esta practica ya tiene un resultado cargado", "Error", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                try {
                    new ResultadoUI(codigoPeticion, codPr);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                frame.dispose();
            }

        });
        enviarResultadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (petic.getEstadoPeticion() != EstadoPeticion.FINALIZADO) {
                    JOptionPane.showMessageDialog(null, "La petición no está finalizada.", "Error", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    if (!reservado) {
                        JOptionPane.showMessageDialog(null, "Envio de resultados por e-mail", "Envio", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Retiro por sucursal obligatorio", "Retiro", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        finPetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean petFin = true;

                try {
                    petFin = peticionc.finalizarPeticion(petic.getCodigo());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

                if(!petFin){
                    JOptionPane.showMessageDialog(null, "Existen practicas sin resultados cargados", "Error", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(null, "Se finalizó la petición", "Exito", JOptionPane.INFORMATION_MESSAGE);
                }

                frame.dispose();

                try {
                    new PeticionUI(petic.getCodigo());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
    }
}


