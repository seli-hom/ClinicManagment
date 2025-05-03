package org.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            //initialize database
            DBConnection.getInstance().initializeDatabase();

            // Initialize model and view
            SystemManager model = new SystemManager();
            ClinicManagementGUI view = new ClinicManagementGUI();

            // Initialize controller
            new ClinicController(view, model);

            view.setUpPatientTable();
            view.setUpDoctorTable();
            view.setUpAppointmentTable();
            view.setUpRecordTable();

            view.updatePatientTable();
            view.updateDoctorTable();
            view.updateAppointmentTable();
            view.updateRecordTable();

            // Set up and show GUI
            view.setContentPane(view.getContentPane());
            view.setJMenuBar(view.getAppMenuBar());
            view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            view.setSize(900, 600);
            view.setLocationRelativeTo(null);
            view.setVisible(true);
            view.setContentPane(view.getContentPane());



            // Initialize database tables
            DBConnection db = DBConnection.getInstance();
            db.getConnection();
            db.createNewDoctorsTable();
            db.createNewPatientsTable();
            db.createNewAppointmentsTable();

            view.setVisible(true);
        });
    }
}
