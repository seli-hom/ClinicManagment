package org.example;


import javax.swing.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;

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
            JFrame frame = new JFrame("Clinic Management System");
            frame.setContentPane(view.getContentPane());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setContentPane(view.getContentPane());



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