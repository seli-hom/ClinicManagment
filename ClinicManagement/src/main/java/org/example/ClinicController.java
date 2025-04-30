package org.example;

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
                
            }
        }


    }

}
