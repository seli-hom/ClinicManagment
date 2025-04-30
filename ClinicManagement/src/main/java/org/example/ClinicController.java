package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClinicController {
    private ClinicManagementGUI view;
    private SystemManager model;

    public ClinicController(ClinicManagementGUI view, SystemManager model) {
        this.view = view;
        this.model = model;

        class AddPatientListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(Messages.getMessage("dialog.enterName"));
                float price = Float.parseFloat(JOptionPane.showInputDialog(Messages.getMessage("dialog.enterPrice")));
                String category = JOptionPane.showInputDialog(Messages.getMessage("dialog.enterCategory"));
                int quantity = Integer.parseInt(JOptionPane.showInputDialog(Messages.getMessage("dialog.enterQuantity")));

//                DBConnection.getInstance(name, price, category, quantity);
                refreshTable();
            }
        }


    }

}
