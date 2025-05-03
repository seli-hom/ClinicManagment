package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ClinicManagementGUI extends JFrame {
    private final JPanel clinicManagementPanel;
    private final JTabbedPane tabbedPane;
    private JPanel patientTab;
    private JPanel doctorTab;
    private JPanel appointmentTab;
    private JPanel recordTab;

    private JButton addPatientButton;
    private JButton modifyPatientButton;
    private JButton findPatientButton;
    private JButton viewPatientsButton;

    private JButton bookAppointmentButton;
    private JButton rescheduleAppointmentButton;
    private JButton cancelAppointmentButton;
    private JButton findAppointmentButton;
    private JButton viewAppointmentsButton;

    private JButton addDoctorButton;
    private JButton modifyDoctorButton;
    private JButton findDoctorButton;
    private JButton findDoctorBySpecialtyButton;
    private JButton viewDoctorsButton;

    private JButton findPatientRecordButton;
    private JButton viewRecordsButton;
    private JButton assignDoctorButton;

    private JTable patientTable;
    private JTable doctorTable;
    private JTable appointmentTable;
    private JTable recordTable;

    private JMenuBar menuBar;
    private JMenu languageMenu;
    private JMenuItem englishMenuItem;
    private JMenuItem frenchMenuItem;

    private final DoctorDAO doctorDAO = new DoctorDAO();
    private final PatientDAO patientDAO = new PatientDAO();
    private final AppointmentDAO appointmentDAO = new AppointmentDAO();

    public ClinicManagementGUI() {
        setTitle(Messages.getMessage("title.app"));
        setSize(900,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel
        clinicManagementPanel = new JPanel(new BorderLayout());
        languageMenu = new JMenu(Messages.getMessage("menu.language"));
        englishMenuItem = new JMenuItem(Messages.getMessage("menu.language.english"));
        frenchMenuItem = new JMenuItem(Messages.getMessage("menu.language.french"));
        languageMenu.add(englishMenuItem);
        languageMenu.add(frenchMenuItem);

        menuBar = new JMenuBar();
        menuBar.add(languageMenu);
        setJMenuBar(menuBar);
        setContentPane(clinicManagementPanel);

        // Tabs
        tabbedPane = new JTabbedPane();

        setUpPatientTab();
        setUpDoctorTab();
        setUpAppointmentTab();
        setUpRecordTab();

        clinicManagementPanel.add(tabbedPane, BorderLayout.CENTER);
    }

    public void setUpPatientTab() {
        patientTab = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        addPatientButton = new JButton(Messages.getMessage("button.addPatient"));
        modifyPatientButton = new JButton(Messages.getMessage("button.modifyPatient"));
        findPatientButton = new JButton(Messages.getMessage("button.findPatient"));
        viewPatientsButton = new JButton(Messages.getMessage("button.viewPatients"));
        buttonPanel.add(addPatientButton);
        buttonPanel.add(modifyPatientButton);
        buttonPanel.add(findPatientButton);
        buttonPanel.add(viewPatientsButton);

        patientTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(patientTable);

        patientTab.add(buttonPanel, BorderLayout.NORTH);
        patientTab.add(scrollPane, BorderLayout.CENTER);
        tabbedPane.addTab(Messages.getMessage("tab.patients"), patientTab);
    }

    public void setUpDoctorTab() {
        doctorTab = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        addDoctorButton = new JButton(Messages.getMessage("button.addDoctor"));
        modifyDoctorButton = new JButton(Messages.getMessage("button.modifyDoctor"));
        findDoctorButton = new JButton(Messages.getMessage("button.button.findDoctor"));
        findDoctorBySpecialtyButton = new JButton(Messages.getMessage("button.findDoctorBySpecialty"));
        viewDoctorsButton = new JButton(Messages.getMessage("button.viewDoctors"));

        buttonPanel.add(addDoctorButton);
        buttonPanel.add(modifyDoctorButton);
        buttonPanel.add(findDoctorButton);
        buttonPanel.add(findDoctorBySpecialtyButton);
        buttonPanel.add(viewDoctorsButton);

        doctorTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(doctorTable);

        doctorTab.add(buttonPanel, BorderLayout.NORTH);
        doctorTab.add(scrollPane, BorderLayout.CENTER);
        tabbedPane.addTab(Messages.getMessage("tab.doctors"), doctorTab);
    }

    public void setUpAppointmentTab() {
        appointmentTab = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        bookAppointmentButton = new JButton(Messages.getMessage("button.bookAppointment"));
        rescheduleAppointmentButton = new JButton(Messages.getMessage("button.rescheduleAppointment"));
        cancelAppointmentButton = new JButton(Messages.getMessage("button.cancelAppointment"));
        findAppointmentButton = new JButton(Messages.getMessage("button.findAppointment"));
        viewAppointmentsButton = new JButton(Messages.getMessage("button.viewAppointments"));
        buttonPanel.add(bookAppointmentButton);
        buttonPanel.add(rescheduleAppointmentButton);
        buttonPanel.add(cancelAppointmentButton);
        buttonPanel.add(findAppointmentButton);
        buttonPanel.add(viewAppointmentsButton);

        appointmentTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(appointmentTable);

        appointmentTab.add(buttonPanel, BorderLayout.NORTH);
        appointmentTab.add(scrollPane, BorderLayout.CENTER);
        tabbedPane.addTab(Messages.getMessage("tab.appointments"), appointmentTab);
    }

    public void setUpRecordTab() {
        recordTab = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        findPatientRecordButton = new JButton(Messages.getMessage("button.findRecord"));
        viewRecordsButton = new JButton(Messages.getMessage("button.viewRecords"));
        assignDoctorButton = new JButton(Messages.getMessage("button.assignDoctor"));
        buttonPanel.add(findPatientRecordButton);
        buttonPanel.add(assignDoctorButton);
        buttonPanel.add(viewRecordsButton);

        recordTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(recordTable);

        recordTab.add(buttonPanel, BorderLayout.NORTH);
        recordTab.add(scrollPane, BorderLayout.CENTER);
        tabbedPane.addTab(Messages.getMessage("tab.records"), recordTab);
    }

    public void setUpPatientTable() {
        String[] columnNames = {
                "ID",
                Messages.getMessage("dialog.firstName"),
                Messages.getMessage("dialog.lastName"),
                Messages.getMessage("dialog.address"),
                Messages.getMessage("dialog.contact")
        };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        patientTable.setModel(model);
        updatePatientTable();
    }

    public void updatePatientTable() {
        DefaultTableModel model = (DefaultTableModel) patientTable.getModel();
        model.setRowCount(0);

        for (Patient patient : patientDAO.getAllPatients()) {
            model.addRow(new Object[]{
                    patient.getPatientId(),
                    patient.getFirstName(),
                    patient.getLastName(),
                    patient.getAddress(),
                    patient.getContact()
            });
        }
    }

    public void updatePatientTable(List<Patient> filteredList) {
        DefaultTableModel model = (DefaultTableModel) patientTable.getModel();
        model.setRowCount(0);

        for (Patient patient : filteredList) {
            model.addRow(new Object[]{
                    patient.getPatientId(),
                    patient.getFirstName(),
                    patient.getLastName(),
                    patient.getAddress(),
                    patient.getContact()
            });
        }
    }

    public void setUpDoctorTable() {
        String[] columnNames = {
                "ID",
                Messages.getMessage("dialog.firstName"),
                Messages.getMessage("dialog.lastName"),
                Messages.getMessage("dialog.contact"),
                Messages.getMessage("dialog.specialty")
        };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        doctorTable.setModel(model);
        updateDoctorTable();
    }

    public void updateDoctorTable() {
        DefaultTableModel model = (DefaultTableModel) doctorTable.getModel();
        model.setRowCount(0);

        for (Doctor doctor : doctorDAO.getAllDoctors()) {
            model.addRow(new Object[]{
                    doctor.getDoctorId(),
                    doctor.getFirstName(),
                    doctor.getLastName(),
                    doctor.getContact(),
                    doctor.getSpeciality()
            });
        }
    }

    public void updateDoctorTable(List<Doctor> filteredList) {
            DefaultTableModel model = (DefaultTableModel) doctorTable.getModel();
            model.setRowCount(0);

            for (Doctor doctor : filteredList) {
                model.addRow(new Object[]{
                        doctor.getDoctorId(),
                        doctor.getFirstName(),
                        doctor.getLastName(),
                        doctor.getContact(),
                        doctor.getSpeciality()
                });
            }
        }

    public void setUpAppointmentTable() {
        String[] columnNames = {
                "ID",
                Messages.getMessage("dialog.enterPatientFindId"),
                Messages.getMessage("dialog.enterAppointmentDoctorId"),
                Messages.getMessage("dialog.enterDate"),
                Messages.getMessage("dialog.enterTime")
        };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        appointmentTable.setModel(model);
        updateAppointmentTable();
    }

    public void updateAppointmentTable() {
        DefaultTableModel model = (DefaultTableModel) appointmentTable.getModel();
        model.setRowCount(0);

        for (Appointment appointment : appointmentDAO.getAllAppointments()) {
            model.addRow(new Object[]{
                    appointment.getAppointmentId(),
                    appointment.getPatientId(),
                    appointment.getDoctorId(),
                    appointment.getDate(),
                    appointment.getTime()
            });
        }
    }

    public void updateAppointmentTable(List<Appointment> filteredList) {
        DefaultTableModel model = (DefaultTableModel) appointmentTable.getModel();
        model.setRowCount(0);

        for (Appointment appointment : filteredList) {
            model.addRow(new Object[]{
                    appointment.getAppointmentId(),
                    appointment.getPatientId(),
                    appointment.getDoctorId(),
                    appointment.getDate(),
                    appointment.getTime()
            });
        }
    }

    public void setUpRecordTable() {
        String[] columnNames = {
                Messages.getMessage("dialog.enterAppointmentPatientId"),
                Messages.getMessage("dialog.firstName"),
                Messages.getMessage("dialog.lastName"),
                Messages.getMessage("dialog.dateOfBirth"),
                Messages.getMessage("dialog.sex"),
                Messages.getMessage("dialog.familyDoctor"),
                Messages.getMessage("dialog.enterBloodType")
        };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        recordTable.setModel(model);
    }

    public void updateRecordTable() {
        DefaultTableModel model = (DefaultTableModel) recordTable.getModel();
        model.setRowCount(0);
        for (Patient patient : patientDAO.getAllPatients()) {
            model.addRow(new Object[]{
                    patient.getPatientId(),
                    patient.getFirstName(),
                    patient.getLastName(),
                    patient.getDob(),
                    patient.getSex(),
                    patient.getFamilyDoctor(),
                    patient.getBloodType()
            });
        }
    }

    public void updateRecordTable(List<Patient> filteredList) {
        DefaultTableModel model = (DefaultTableModel) recordTable.getModel();
        model.setRowCount(0);
        for (Patient patient : filteredList) {
            model.addRow(new Object[]{
                    patient.getPatientId(),
                    patient.getFirstName(),
                    patient.getLastName(),
                    patient.getDob(),
                    patient.getSex(),
                    patient.getBloodType(),
                    patient.getFamilyDoctor()
            });
        }
    }

    public void refreshTexts() {
        // tabs
        tabbedPane.setTitleAt(0, Messages.getMessage("tab.patients"));
        tabbedPane.setTitleAt(1, Messages.getMessage("tab.doctors"));
        tabbedPane.setTitleAt(2, Messages.getMessage("tab.appointments"));
        tabbedPane.setTitleAt(3, Messages.getMessage("tab.records"));
        // patient buttons
        addPatientButton.setText(Messages.getMessage("button.addPatient"));
        modifyPatientButton.setText(Messages.getMessage("button.modifyPatient"));
        findPatientButton.setText(Messages.getMessage("button.findPatient"));
        viewPatientsButton.setText(Messages.getMessage("button.viewPatients"));
        // doctor buttons
        addDoctorButton.setText(Messages.getMessage("button.addDoctor"));
        modifyDoctorButton.setText(Messages.getMessage("button.modifyDoctor"));
        findDoctorButton.setText(Messages.getMessage("button.findDoctor"));
        findDoctorBySpecialtyButton.setText(Messages.getMessage("button.findDoctorBySpecialty"));
        viewDoctorsButton.setText(Messages.getMessage("button.viewDoctors"));
        // appointment buttons
        bookAppointmentButton.setText(Messages.getMessage("button.bookAppointment"));
        rescheduleAppointmentButton.setText(Messages.getMessage("button.rescheduleAppointment"));
        cancelAppointmentButton.setText(Messages.getMessage("button.cancelAppointment"));
        findAppointmentButton.setText(Messages.getMessage("button.findAppointment"));
        viewAppointmentsButton.setText(Messages.getMessage("button.viewAppointments"));
        // record buttons
        findPatientRecordButton.setText(Messages.getMessage("button.findRecord"));
        viewRecordsButton.setText(Messages.getMessage("button.viewRecords"));
        assignDoctorButton.setText(Messages.getMessage("button.assignDoctor"));
        // menu
        languageMenu.setText(Messages.getMessage("menu.language"));
        englishMenuItem.setText(Messages.getMessage("menu.language.english"));
        frenchMenuItem.setText(Messages.getMessage("menu.language.french"));
    }

    // Getters for all GUI elements
    public JButton getAddPatientButton() {
        return addPatientButton;
    }

    public JButton getModifyPatientButton() {
        return modifyPatientButton;
    }

    public JButton getFindPatientButton() {
        return findPatientButton;
    }

    public JButton getViewPatientsButton() {
        return viewPatientsButton;
    }

    public JButton getBookAppointmentButton() {
        return bookAppointmentButton;
    }

    public JButton getRescheduleAppointmentButton() {
        return rescheduleAppointmentButton;
    }

    public JButton getCancelAppointmentButton() {
        return cancelAppointmentButton;
    }

    public JButton getFindAppointmentButton() {
        return findAppointmentButton;
    }

    public JButton getViewAppointmentsButton() {
        return viewAppointmentsButton;
    }

    public JButton getFindPatientRecordButton() {
        return findPatientRecordButton;
    }

    public JButton getAddDoctorButton() {
        return addDoctorButton;
    }

    public JButton getModifyDoctorButton() {
        return modifyDoctorButton;
    }

    public JButton getFindDoctorButton() {
        return findDoctorButton;
    }

    public JButton getFindDoctorBySpecialtyButton() {
        return findDoctorBySpecialtyButton;
    }

    public JButton getViewDoctorsButton() {
        return viewDoctorsButton;
    }

    public JTable getPatientTable() {
        return patientTable;
    }

    public JTable getDoctorTable() {
        return doctorTable;
    }

    public JTable getAppointmentTable() {
        return appointmentTable;
    }

    public JTable getRecordTable() {
        return recordTable;
    }

    public JPanel getClinicManagementPanel() {
        return clinicManagementPanel;
    }

    public DoctorDAO getDoctorDAO() {
        return doctorDAO;
    }

    public PatientDAO getPatientDAO() {
        return patientDAO;
    }

    public AppointmentDAO getAppointmentDAO() {
        return appointmentDAO;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public JPanel getPatientTab() {
        return patientTab;
    }

    public JPanel getDoctorTab() {
        return doctorTab;
    }

    public JPanel getAppointmentTab() {
        return appointmentTab;
    }

    public JPanel getRecordTab() {
        return recordTab;
    }

    public JButton getViewRecordsButton() {
        return viewRecordsButton;
    }

    public JButton getAssignDoctorButton() {
        return assignDoctorButton;
    }

    public JMenu getLanguageMenu() {
        return languageMenu;
    }

    public JMenuItem getEnglishMenuItem() {
        return englishMenuItem;
    }

    public JMenuItem getFrenchMenuItem() {
        return frenchMenuItem;
    }

    public JMenuBar getAppMenuBar() {
        return menuBar;
    }
}
