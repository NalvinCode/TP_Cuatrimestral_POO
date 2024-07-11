package com.UADE.view;

import com.UADE.base.Singleton;
import com.UADE.controller.PacienteController;
import com.UADE.controller.PeticionController;
import com.UADE.controller.PracticaController;
import com.UADE.controller.SucursalController;
import com.UADE.dto.PacienteDTO;
import com.UADE.dto.PeticionDTO;
import com.UADE.dto.PracticaDTO;
import com.UADE.dto.SucursalDTO;
import com.UADE.enums.EstadoPeticion;
import com.UADE.enums.ObraSocial;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class NuevaPeticionUI {
    private final PeticionController peticionc;
    private final PracticaController practicac;
    private final PacienteController pacientec;
    private final SucursalController sucursalc;
    private JButton guardarButton;
    private JPanel panel1;
    private JComboBox<String> comboBoxPacientes;
    private JList<String> listPracticas;
    private JComboBox<String> comboBoxPractica;
    private JButton agregarButton;
    private JComboBox comboBoxSucursal;
    private JComboBox comboBoxObraSocial;
    private DefaultListModel<String> listModel;

    public NuevaPeticionUI() throws Exception {
        JFrame frame = new JFrame("Nueva petición");
        panel1.setBorder(new EmptyBorder(15, 15, 15, 15));
        frame.setContentPane(panel1);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        listModel = new DefaultListModel<String>();
        listPracticas.setModel(listModel);

        peticionc = Singleton.getInstance().peticionController;
        practicac = Singleton.getInstance().practicaController;
        pacientec = Singleton.getInstance().pacienteController;
        sucursalc = Singleton.getInstance().sucursalController;

        List<PracticaDTO> listadoPracticas = practicac.obtenerListaPracticas();

        for (PracticaDTO datprac : listadoPracticas) {
            comboBoxPractica.addItem(datprac.getCodigo() + " " + datprac.getNombre());
        }

        List<PacienteDTO> pacientes = pacientec.obtenerListaPacientes();

        for (PacienteDTO pac : pacientes) {
            comboBoxPacientes.addItem(pac.getCodigo() + " " + pac.getNombreCompleto());
        }

        List<SucursalDTO> sucursales = sucursalc.obtenerListaSucursales();

        for(SucursalDTO suc : sucursales){
            comboBoxSucursal.addItem(suc.getCodigo() + " " + suc.getDireccion());
        }

        ObraSocial[] obrasS = ObraSocial.values();

        for(ObraSocial os : obrasS){
            comboBoxObraSocial.addItem(os.toString());
        }

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Iterator<String> it = listModel.elements().asIterator(); it.hasNext(); ) {
                    String i = it.next();

                    if (i.equals(comboBoxPractica.getSelectedItem().toString())) {
                        return;
                    }
                }

                listModel.addElement(comboBoxPractica.getSelectedItem().toString());
            }
        });
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String validFields = validateFields();

                if(validFields != null){
                    JOptionPane.showMessageDialog(null, validFields, "Error", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                String valueP = (String) comboBoxPacientes.getSelectedItem();
                Integer codP = Integer.valueOf(valueP.split(" ")[0]);

                List<Integer> listpract = new ArrayList<>();

                for (Iterator<String> it = listModel.elements().asIterator(); it.hasNext(); ) {
                    String i = it.next();
                    listpract.add(Integer.valueOf(i.split(" ")[0]));
                }

                String valueS = (String) comboBoxSucursal.getSelectedItem();
                Integer codS = Integer.valueOf(valueS.split(" ")[0]);

                LocalDateTime now = LocalDateTime.now();

                Integer demoraTotal = 0;
                for (Integer codigoP : listpract){
                    PracticaDTO p = practicac.obtenerDatosPractica(codigoP);
                    if(p.getTiempoEstimado() > demoraTotal){
                        demoraTotal = p.getTiempoEstimado();
                    }
                }
                LocalDateTime entrega = now.plusHours(demoraTotal);

                ZoneId zoneId = ZoneId.systemDefault();

                Date fechaInicio = Date.from(now.atZone(zoneId).toInstant());
                Date fechaEntrega = Date.from(entrega.atZone(zoneId).toInstant());

                try {
                    peticionc.nuevaPeticion(new PeticionDTO(null, (String) comboBoxObraSocial.getSelectedItem(), fechaInicio, fechaEntrega, EstadoPeticion.INICIO, codP, codS, listpract));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                frame.dispose();

                try {
                    new PeticionesUI();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private String validateFields(){
        String campo = null;

        if(comboBoxObraSocial.getSelectedItem() == null){
            campo = "Obra Social";
        }
        if(comboBoxSucursal.getSelectedItem() == null){
            campo = "Sucursal";
        }
        if(comboBoxPacientes.getSelectedItem() == null){
            campo = "Paciente";
        }

        if(campo != null){
            return "Por favor ingrese el campo " + campo;
        }

        if(!listModel.elements().asIterator().hasNext()){
            return "Debe agregar al menos una practica";
        }

        return null;
    }
}
