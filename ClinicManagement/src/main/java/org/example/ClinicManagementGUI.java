package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClinicManagementGUI {
    private JTabbedPane tabbedPane;
    private JPanel clinicManagementPanel;
    private JButton addPatientButton;
    private JButton modifyPatientButton;
    private JTable patientTable;
    private JTable doctorTable;
    private JButton findPatientButton;
    private JButton bookAppointmentButton;
    private JTable appointmentTable;
    private JButton rescheduleAppointmentButton;
    private JButton cancelAppointmentButton;
    private JButton findAppointmentButton;
    private JButton findPatientRecordButton;
    private JTable recordTable;
    private JPanel patientTab;
    private JPanel doctorTab;
    private JPanel appointmentTab;
    private JPanel recordTab;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Clinic Management System");
        frame.setContentPane(new ClinicManagementGUI().clinicManagementPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }



//    public void addPatient() {
//        addPatientButton.addActionListener(new ActionListener() {
//            JTextField nameField = new JTextField(15);
//            JTextField ageField = new JTextField(5);
//            JTextField contactField = new JTextField(10);
//
//            JPanel panel = new JPanel();
//            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//
//            panel.add(new JLabel("Patient Name:"));
//            panel.add(nameField);
//            panel.add(Box.createVerticalStrut(10)); // Spacer
//
//            panel.add(new JLabel("Age:"));
//            panel.add(ageField);
//            panel.add(Box.createVerticalStrut(10)); // Spacer
//
//            panel.add(new JLabel("Contact Number:"));
//            panel.add(contactField);
//
//            int result = JOptionPane.showConfirmDialog(null, panel,
//                    "Enter Patient Details", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
//
//            if (result == JOptionPane.OK_OPTION) {
//                String name = nameField.getText().trim();
//                String ageText = ageField.getText().trim();
//                String contact = contactField.getText().trim();
//
//                try {
//                    int age = Integer.parseInt(ageText);
//                    // Create the Patient object here
//                    Patient newPatient = new Patient(name, age, contact); // assuming your constructor matches
//                    JOptionPane.showMessageDialog(null, "Patient created:\n" + newPatient);
//                }
//                catch (NumberFormatException e) {
//                    JOptionPane.showMessageDialog(null, "Please enter a valid number for age.");
//                }
//            }
//        }
//    }
}
