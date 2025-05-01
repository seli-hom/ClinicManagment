package org.example;

import javax.swing.*;
import java.awt.event.*;

import java.sql.Date;
import java.util.List;

public class ClinicController {
    private ClinicManagementGUI view;
    private SystemManager model;

    public ClinicController(ClinicManagementGUI view, SystemManager model) {
        this.view = view;
        this.model = model;

        class AddPatientListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fname = JOptionPane.showInputDialog(Messages.getMessage("enter first name"));
                String lname = JOptionPane.showInputDialog(Messages.getMessage("enter first name"));
                String address = JOptionPane.showInputDialog(Messages.getMessage("enter first name"));
                String contact = JOptionPane.showInputDialog(Messages.getMessage("enter first name"));
//                Date dob = JComboBox();
                JComboBox<String> sex = new JComboBox<>();
                sex.addItem("Male");
                sex.addItem("Female");
                sex.addItem("Other");
                JComboBox<String> bloodType = new JComboBox<>();
                bloodType.addItem("O+");
                bloodType.addItem("A+");
                bloodType.addItem("B+");
                bloodType.addItem("AB+");
                bloodType.addItem("O-");
                bloodType.addItem("A-");
                bloodType.addItem("B-");
                bloodType.addItem("AB-");

//                Patient newPatient = new Patient(fname, lname, address, contact, dob, sex.getSelectedItem().toString(), bloodType.getSelectedItem().toString());

//                model.registerPatient(newPatient);
            }
        }


    }

}
